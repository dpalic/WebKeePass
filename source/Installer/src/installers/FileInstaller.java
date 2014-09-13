package installers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

public class FileInstaller {
	
	StringBuffer buffer; 
	StringBuffer msg ;
	
	public FileInstaller(){}
		
	public boolean readFile(String fileNmae) {
		buffer = new StringBuffer();
		msg  = new StringBuffer();
	    try {
	    	Reader file = new FileReader(fileNmae);	
	    	char[] b = new char[128];
	    	int n;
	    	while ((n = file.read(b)) > 0)
	    		buffer.append(b,0,n);
	    	file.close();
	    	
	    	
	    	
		} catch (Exception er) {
			er.printStackTrace();
			msg.append("Error Reading File - " + er.getMessage());
			return false;
		}
		return true;
	}
	
	
	public void mergeTag(String tag, String value) {
		boolean merging = true;
		while(merging){
			int a = buffer.indexOf(tag);
			if( a != -1)
				buffer.replace(a, a+tag.length(), value);
			else
				merging = false;
		}
	}


	
	public boolean writeFile (String fileNmae) {			
		try {			 
			Writer sourceFile = new FileWriter(fileNmae);
			sourceFile.write (buffer.toString());
			sourceFile.close();
			
		} catch (Exception er) {
			er.printStackTrace();
			msg.append("Error Writing File - " + er.getMessage());
			return false;	
		}
	return true;	
	}
	
	
	public String getMessages(){
		return msg.toString();
	}	
}
