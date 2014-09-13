package installers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import components.CipherString; 

public class TermInstallerMain {

	String path_T = new String();
	String jh_T = new String();
	String db_T = new String();
	String usr_DB = new String();
	String pw_DB = new String();

	String usr_ADM = new String();
	String pw_ADM = new String();

	String usr_USR = new String();
	String pw_USR = new String();

	String portStart_A = new String();
	String portStop_A = new String();
	String dmName_A = new String();
	String cipher  = new String();
	String fs, os;

	BufferedReader in;
	PrintStream out;
	String my_string;

	DataBaseInstaller data = new DataBaseInstaller();
	ZipInstaller zip = new ZipInstaller();
	FileInstaller fi = new FileInstaller();
	StringBuffer msgs = new StringBuffer();

	SecretKey secretKey;
	
	public void doInstall() throws Exception {

		do {
			out.println("\n 1 > File System Details \n");
			if (fs.equals("/"))
				out.print("Installation Path: \t");
			else
				out.print("Installation Path: \t");
			path_T = in.readLine();
		} while (!editpath());	
		
			 
		
		do {		 
			out.print("JAVA_HOME' Path: \t");
			jh_T = in.readLine();
		} while (!editJvm());

		

		
		do {
			out.println("\n 2 > User ID's and Passwords \n");
			out.print("Web KeePass Admin/Root UserID: [root] ");
			usr_ADM = in.readLine();
			if(usr_ADM == null || usr_ADM.equals("")) usr_ADM = "root";
			
			out.print("Admin/Root Password: ");
			pw_ADM = in.readLine();
			
			out.print("Web KeePass Standard UserID: [user] ");
			usr_USR = in.readLine();
			if(usr_USR == null || usr_USR.equals("")) usr_USR = "user";
			
			out.print("Standard User Password: ");
			pw_USR = in.readLine();

		} while (!editPasswords());

		
		
		
		do {
			out.println("\n 3  > Tomcat Config \n");
			out.print("Tomcat HTTPS/SSL Port: [8443] ");
			portStart_A = in.readLine();
			if(portStart_A == null || portStart_A.equals(""))  portStart_A = "8443";
			
			out.print("Tomcat Shutdown Port: [9444] ");
			portStop_A = in.readLine();
			if(portStop_A == null || portStop_A.equals("")) portStop_A = "9444";
			
			out.print("Your Host Name: [localhost] ");
			dmName_A = in.readLine();
			if (dmName_A == null || dmName_A.equals("")) dmName_A = "localhost";
			
		} while (!editTomcat());
		
		
		
		
		do {
			out.println("\n 4 > Cipher Config \n");
			out.println("Select Cipher: [1] ");
			out.println("  1 = Blowfish");
			out.println("  2 = DESede");
			out.println("  3 = TripleDES");
			out.println("  4 = AES");
			out.println("  5 = DES");
			
			cipher = in.readLine();
			if(cipher == null || cipher.equals("")) cipher = "1";
	 		
		} while (!editCipher());
		
		
		
		RunInstall r = new RunInstall();
	  	r.start();
	  	r = null;

	}


   private boolean editpath() {
	
	if (path_T == null || path_T.equals(""))  {	
		if(fs.equals("/"))
		   out.println("\n ***  Invalid Install Folder  ");
		else
		   out.println("\n ***  Invalid Install Folder ");
		return false;
	}
	return true;
   }

	
   private boolean editJvm() {
		if (!(new File(jh_T.trim() + "/bin/java").isFile())
				&& !(new File(jh_T.trim() + "/bin/java.exe").isFile())) {
			out.println("\n ***  Invalid JAVA_HOME Path");
			return false;
		}
		return true;
	}

	

   private boolean editPasswords() {
		if (usr_ADM == null || usr_ADM.length() < 4 || usr_USR == null
				|| usr_USR.length() < 4) {
			out.println("\n ***  Invalid - User ID < 4 long");
			return false;
		}

		if (pw_ADM == null || pw_ADM.length() < 8 || pw_USR == null
				|| pw_USR.length() < 8) {
			out.println("\n ***  Invalid - Password < 8 long");
			return false;
		}

		return true;
	}

	private boolean editTomcat() {
		int portStart = 0, portStop = 0;
		try {
			portStart = Integer.parseInt(portStart_A);
		} catch (Exception e) {
			portStart = 0;
		}
		if (portStart < 200 || portStart > 65000) {
			out.println("\n ***  Invalid HTTPS/SSL Port");
			return false;
		}

		try {
			portStop = Integer.parseInt(portStop_A);
		} catch (Exception e) {
			portStop = 0;
		}
		if (portStop < 200 || portStop > 65000) {
			out.println("\n ***  Invalid Shutdown Port");
			return false;
		}

		if (dmName_A == null || dmName_A.length() < 2) {
			out.println("\n ***  Invalid Host Name");
			return false;
		}

	return true;
	}
	
	
	private boolean editCipher() {
		if ((cipher == null) || 
				(!cipher.equals("1") && !cipher.equals("2") && !cipher.equals("3") && !cipher.equals("4") && !cipher.equals("5") ) ) {
			out.println("\n *** Cipher not valid");
			out.println("  1 = Blowfish");
			out.println("  2 = DESede");
			out.println("  3 = TripleDES");
			out.println("  4 = AES");
			out.println("  5 = DES");
		    return false;
		}
			
		return true;
	
	}

	public boolean installDB() {
		try {
			if (!data.installEmbeded(new CipherString(secretKey),
					path_T,  "webkeepass", "passkeeweb",
					"Install/CreateTablesEmbeded.sql", usr_ADM.trim(),
					sha1(pw_ADM.trim()), usr_USR.trim(), sha1(pw_USR.trim()), "ENG", "ENG" ))
				return false;

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean createConfig() {

		String javaHome = jh_T.trim();
		String path = path_T.trim();
		String user = usr_DB.trim();
		String pwrd = pw_DB.trim();
		String portStart = portStart_A.trim();
		String portStop = portStop_A.trim();

		//key1 = sha1(key_A.trim());

		if (!fi.readFile("Install/ConfigFile1.xml"))
			return false;

		fi.mergeTag("{dvr}", "org.apache.derby.jdbc.EmbeddedDriver");
		fi.mergeTag("{db}", "jdbc:derby:" + path + "datasrc");
		fi.mergeTag("{user}", "webkeepass");
		fi.mergeTag("{password}", "passkeeweb");
		

		fi.mergeTag("{scripts}", path + "jakarta-tomcat-5.5.7"+ fs + "webapps" + fs +"ROOT" +  fs + "XML" + fs);
		fi.mergeTag("{docs}",    path + "jakarta-tomcat-5.5.7"+ fs + "document" + fs );
		fi.mergeTag("{images}",  path +"jakarta-tomcat-5.5.7" + fs + "webapps" + fs +"ROOT" +  fs + "XML" + fs + "images" + fs);
		fi.mergeTag("{schm}",    path + "conf" + fs +"Schema1.xml");
		fi.mergeTag("{logs}",    path +"jakarta-tomcat-5.5.7" + fs +"logs" + fs);
		
		if(!fi.writeFile(path + "conf"+ fs + "WebKeePassConf.xml"))
			return false;

		if (!fi.readFile("Install/web.xml"))
			return false;
		
		fi.mergeTag( "{home}",  path + "conf" + fs );
		if(!fi.writeFile( path +"jakarta-tomcat-5.5.7"+ fs + "webapps" + fs +"ROOT" + fs + "WEB-INF" + fs +"web.xml"))
			return false;
		
		if (!fi.readFile("Install/server.xml"))
			return false;
		fi.mergeTag("{portStartUp}", portStart);
		fi.mergeTag("{portShutDown}", portStop);
		fi.mergeTag( "{certFile}",  path + "conf" + fs +"webKeePass.key");
		if (!fi.writeFile(path + "jakarta-tomcat-5.5.7" + fs + "conf" + fs + "server.xml"))
			return false;

		StringBuffer startL = new StringBuffer("export JAVA_HOME="
				+ javaHome.trim() + "\n" + "cd ./jakarta-tomcat-5.5.7/bin\n"
				+ "./startup.sh \n");

		StringBuffer startW = new StringBuffer("set JAVA_HOME="
				+ javaHome.trim() + "\r\n" + "cd jakarta-tomcat-5.5.7\\bin\r\n"
				+ "startup.bat \r\n");

		Writer sourceFile;
		try {
			sourceFile = new FileWriter(path + "startup.sh");
			sourceFile.write(startL.toString());
			sourceFile.close();

			sourceFile = new FileWriter(path + "startup.bat");
			sourceFile.write(startW.toString());
			sourceFile.close();

		} catch (IOException e) {
			return false;
		}

		return true;
	}

	class RunInstall extends Thread {

		public RunInstall() {
		}

		public void run() {

			if ( ! path_T.endsWith(fs))  path_T += fs;


			String error = "\n \n - before re-installing please remove: "
					+ path_T + "\n   and drop database: " + db_T;

			out.println("\n********************************************************");
			out.println("* Installation Detail");
			out.println("*\t Installation Path: " + path_T);
			out.println("*\t JAVA_HOME: " + jh_T);			
			out.println("*\t Web KeePass Admin/Root UserID: " + usr_ADM);			
			out.println("*\t Password:  *********** ");
			out.println("*\t Web KeePass Standard UserID: \t "+ usr_USR);
			out.println("*\t Password:  *********** ");
			out.println("*\t Tomcat HTTPS/SSL Port: " + portStart_A);
			out.println("*\t Tomcat Shutdown Port: "+ portStop_A );
			out.println("*\t Host Name: " + dmName_A);
			out.println("*\t Cipher: " + cipher);
			out.println("\n********************************************************");
			out.print(" \n \n ****");
			
			try {
				
				if(cipher.equals("2"))
			  	     secretKey = KeyGenerator.getInstance("DESede").generateKey();
				else if(cipher.equals("3"))
			  	     secretKey = KeyGenerator.getInstance("TripleDES").generateKey();
				else if(cipher.equals("4"))
					 secretKey = KeyGenerator.getInstance("AES").generateKey();
				else if(cipher.equals("5"))	
					secretKey = KeyGenerator.getInstance("DES").generateKey();
				else
					secretKey = KeyGenerator.getInstance("Blowfish").generateKey();
				
				if (!zip.unZip("Install/Install.zip", path_T)) {
					out.println("Error Expanding Files: \n");
					throw new InterruptedException();
				}

				out.print("****");
				if (!createConfig()) {
					out.println("\n \n   *** ERROR ****      Config Write Error \n " + fi.getMessages() + error);
					throw new InterruptedException();
				}

				out.print("****");
				if (!createCert()) {
					out.println("\n \n   *** ERROR ****      Could not create Certified Key  "
									+ error);
					throw new InterruptedException();
				}

				out.print("****");
				if (!installDB()) {
					out.println("\n \n   *** ERROR ****      Database Write Error");
					throw new InterruptedException();
				}

				out.println("**** \n \n");
				out.print(" **** Complete! \n "
								+ "\n   1 - Start Tomcat: "
								+ path_T + "\n   2 - point your web browser to : https://"
								+ dmName_A.trim() + ":"
								+ portStart_A
								+ "\n   3 - point your mobile device to https://"
								+ dmName_A.trim() + ":" + portStart_A
								+ "/iphone \n \n "
								+ "\n(*note: If reinstallation is required, you must first \n delete the target installation folder) \n \n ");

			} catch (Exception i) {
				out.println("\n *** ERROR **** \n");
				i.printStackTrace(out);
				out.println("\n *** ERROR **** \n");
				
			}
		}

	}

	public boolean createCert() {
		/*
		 * keytool -genkey -keystore ~/tomcat/conf/keystore -alias tomcat
		 * -keyalg RSA -keysize 2048 -dname CN=localhost -storepass changeit
		 * -keypass changeit
		 */

		try {
			
			//Create ssl cert
			String path = path_T.trim();
			String hostname = dmName_A.trim();

			sun.security.tools.KeyTool.main(new String []
				  { "-genkey", 
					"-alias", "tomcat",
					"-keystore", 
					 path +  "conf" + fs + "webKeePass.key",
					"-keyalg", "RSA",
					"-keysize", "2048", 
					"-dname", "CN=" + hostname,
					"-storepass", "changeit",
					"-keypass", "changeit" });
			
		    //Create SecretKey
		    File file = new File(path + "conf" + fs + "dataKeePass.key");
		    ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file));
		    o.writeObject( secretKey );
		    o.flush();
		    o.close();

		} catch (Exception e) {
			out.println(e.getMessage());
			return false;
		}
		return true;
	}

	
	
	public static String sha1(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			String hex = "";
			md.update(data.getBytes());
			byte[] digest = md.digest();
			for (int i = 0; i < digest.length; i++) {
				String h = Integer.toHexString(digest[i]);
				if (h.length() == 1)
					h = "0" + h;
				h = h.substring(h.length() - 2);
				hex += h;
			}
			return hex;
		} catch (NoSuchAlgorithmException e) {
			return data;
		}
	}

	public void showLic() {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = System.out;
			os = System.getProperty("os.name");
			fs = System.getProperty("file.separator");
			
			out.print(" ** Falling back to terminal install ** ");

			Reader file = new FileReader("Install/LICENSE.txt");
			StringBuffer buf = new StringBuffer();
			char[] b = new char[128];
			int n;
			while ((n = file.read(b)) > 0)
				buf.append(b, 0, n);
			file.close();
			out.println(buf.toString());
			out.print("Do You Agree?  [Y or N] :");
			if (in.readLine().toUpperCase().equals("Y")) {
				doInstall();
			}

		} catch (Exception e) {
			out.println("\n *** ERROR ****" + e.getMessage());
			out.println(e.getMessage());
		}

	}

	public static void main(String[] args) throws Exception {
		TermInstallerMain installer = new TermInstallerMain();
		installer.showLic();
	}
}
