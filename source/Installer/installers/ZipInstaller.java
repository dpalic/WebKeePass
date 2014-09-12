package installers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JProgressBar;

public class ZipInstaller  {

	protected String targetPath, filename;

	protected ZipFile zipper;

	protected byte[] b;

	protected StringBuffer msg;


	ZipInstaller() {
		b = new byte[8092];
	}

	public boolean unZip(String file, String target) {
		msg = new StringBuffer();
		targetPath = target;
		File d = new File(targetPath);
		try {
			
			if (!d.exists() ) {	
				msg.append(" Creating Directory: " + targetPath + " \n ");
				if (!d.mkdirs()) {
					msg.append("Warning: unable to mkdir " + targetPath + " \n ");
					return false;
				}
			}
		
		filename = file;
		
			zipper = new ZipFile(filename);
			Enumeration all = zipper.entries();
			while (all.hasMoreElements()) {
				getFile((ZipEntry) all.nextElement());
			}
		} catch (IOException err) {
			msg.append("IO Error: " + err + " \n ");
			return false;
		}
		return true;
	}


	
	
	protected void getFile(ZipEntry e) throws IOException {
		String zipName = targetPath + e.getName();
		
		if (zipName.endsWith("/")) {
			return;
		}


		int ix = zipName.lastIndexOf('/');
		if (ix > 0) {
			String dirName = zipName.substring(0, ix);

			File d = new File(dirName);
			
			if (!(d.exists() && d.isDirectory())) {
				
				msg.append(" Creating Directory: " + dirName + " \n ");
				if (!d.mkdirs()) {
					msg.append("Warning: unable to mkdir " + dirName + " \n ");
				}
			}

		}

		msg.append(" -Creating " + zipName + " \n ");
		FileOutputStream os = new FileOutputStream(zipName);
		InputStream is = zipper.getInputStream(e);
		int n = 0;
		while ((n = is.read(b)) > 0)
			os.write(b, 0, n);
		is.close();
		os.close();
	}

	public String getMessages() {
		return msg.toString();
	}

}