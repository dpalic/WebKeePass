package installers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import components.GridFlowLayout;
import components.GridFlowLayoutParameter;

public class InstallerMain implements ActionListener, MouseListener{

	JTextField path_T = new JTextField(20);
	JTextField jh_T = new JTextField(20);
	JTextField db_T = new JTextField(20);
	JTextField usr_DB = new JTextField(20);
	JTextField pw_DB = new JTextField(20);

	JTextField usr_ADM = new JTextField(20);
	JTextField pw_ADM = new JTextField(20);

	JTextField usr_USR = new JTextField(20);
	JTextField pw_USR = new JTextField(20);
	
	JTextField port_A = new JTextField(20);
	JTextField dmName_A = new JTextField(20);
	
	JTextField key_A = new JTextField(40);
	JTextField key_B = new JTextField(40);
	
	ButtonGroup bgroup = new ButtonGroup();
	
	String key1; 
	
	JFrame main_F; 
 
	String fs, os; 

	public JProgressBar bar = new JProgressBar();


	DataBaseInstaller data = new DataBaseInstaller();
	ZipInstaller zip = new ZipInstaller();
	FileInstaller fi = new FileInstaller();
	StringBuffer msgs = new StringBuffer();
	
	
	public JPanel buildTitlePanel() {
		JPanel tl_P = new JPanel();
		JLabel l = new JLabel(new ImageIcon("Install/banner.jpg"));
		l.setFont(new java.awt.Font("Times", 1, 20));
		tl_P.add(l);
		tl_P.setBackground(Color.WHITE);
		tl_P.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED), BorderFactory
				.createLineBorder(new Color(0, 150, 150), 1)));
		return tl_P;
	}
	

	public JPanel buildfooterPanel(String next, String action) {
		JPanel ftr_P = new JPanel();
		JButton in_B = new JButton(next);
		in_B.setActionCommand(action);
		in_B.addActionListener(this);
		JButton cn_B = new JButton("Cancel");
		cn_B.setActionCommand("Cancel.Action");
		cn_B.addActionListener(this);
		ftr_P.add(cn_B);
		ftr_P.add(in_B);
		return ftr_P;
	}

	
	
//-------
	
	public void showLICENSE() {
		os = System.getProperty("os.name");
		fs = System.getProperty("file.separator");
		main_F = new JFrame();
		main_F.getContentPane().setLayout(new BorderLayout());
		main_F.getContentPane().setBackground(Color.WHITE);
		main_F.add(buildTitlePanel(), BorderLayout.NORTH);
		main_F.add(buildLncePanel(), BorderLayout.CENTER);
		main_F.pack();
		main_F.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		main_F.setLocation((d.width - main_F.getSize().width) / 2, (d.height - main_F.getSize().height) / 2);
		main_F.setVisible(true);
	}

	
	public JPanel buildLncePanel() {
		JPanel l = new JPanel(new BorderLayout());
		JTextArea t = new JTextArea();
	    JScrollPane main_P = new JScrollPane(t);
	    main_P.setPreferredSize(new Dimension(480,300));	  
	    
	    try {
	    	Reader file = new FileReader("Install/LICENSE.txt");	
	    	StringBuffer buf = new StringBuffer();
	    	char[] b = new char[128];
	    	int n;
	    	while ((n = file.read(b)) > 0)
	    		buf.append(b,0,n);
	    	file.close();
	    	t.setText(buf.toString());
		} catch (Exception er) {
			t.setText(null);
		}
				
		l.add(main_P, BorderLayout.CENTER);
		l.add(buildfooterPanel("I Agree", "Next1.Action"), BorderLayout.SOUTH);
		
		return l;
	}
	
	
	
	
//------- 
	
	public void showScreen1() {
		main_F.dispose();
		main_F = new JFrame();
		main_F.getContentPane().setLayout(new BorderLayout());
		main_F.getContentPane().setBackground(Color.WHITE);
		main_F.add(buildTitlePanel(), BorderLayout.NORTH);			
		main_F.add(buildPanel1(), BorderLayout.CENTER);
		main_F.add(buildfooterPanel("Next", "Next2.Action" ), BorderLayout.SOUTH);
		main_F.pack();
		main_F.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		main_F.setLocation((d.width - main_F.getSize().width) / 2, (d.height - main_F
				.getSize().height) / 2);
		main_F.setVisible(true);
	}

	
	
	public JPanel buildPanel1() {
		
		if(fs.equals("/"))
			path_T.setText("/usr/local/WebKeePass");
		else
			path_T.setText("C:\\WebKeePass");
		
		db_T.setText(null);
		db_T.setEditable(false);
		usr_DB.setText(null);
		usr_DB.setEditable(false);
		pw_DB.setText(null);
		pw_DB.setEditable(false);
		
		JPanel main_P = new JPanel(new GridFlowLayout(10, 10));

		JLabel l1 = new JLabel("<html><font color=\"blue\"><u>Installation Path:</u></font></html>");
		l1.addMouseListener(this);
		l1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		l1.setName("Path.Action");
		main_P.add(l1, new GridFlowLayoutParameter(true, 1));
		main_P.add(path_T, new GridFlowLayoutParameter(false, 2));
		
		JLabel l2 = new JLabel("<html><font color=\"blue\"><u>'JAVA_HOME' Path:</u></font></html>");
		l2.addMouseListener(this);
		l2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		l2.setName("JAVA_HOME.Action");
		main_P.add(l2, new GridFlowLayoutParameter(true, 1));
		main_P.add(jh_T, new GridFlowLayoutParameter(false, 2));
		
		JRadioButton rb1 = new JRadioButton("Use an embedded WebKeePass data source");
		rb1.setName("Embedded.Action");		
		rb1.setActionCommand("Embedded.Action");
		rb1.addActionListener(this);
		
		bgroup.add(rb1);

		JRadioButton rb2 = new JRadioButton("Use a new MySQL database data source");
		rb2.setName("MySQL.Action");
		rb2.setActionCommand("MySQL.Action");
		rb2.addActionListener(this);
		bgroup.add(rb2);

		
		main_P.add(rb1, new GridFlowLayoutParameter(true, 2));
		main_P.add(rb2, new GridFlowLayoutParameter(true, 2));
		bgroup.setSelected(rb1.getModel(), true);
		
		main_P.add(new JLabel("MySQL Database/Schema:"), new GridFlowLayoutParameter(true, 1));
		main_P.add(db_T, new GridFlowLayoutParameter(false, 2));

		main_P.add(new JLabel("MySQL Admin User Name:"), new GridFlowLayoutParameter(true, 1));
		main_P.add(usr_DB, new GridFlowLayoutParameter(false, 2));

		main_P.add(new JLabel("MySQL Admin Password:"), new GridFlowLayoutParameter(true, 1));
		main_P.add(pw_DB, new GridFlowLayoutParameter(false, 2));
		
		return main_P;
	
	}

	private boolean editScreen1() {
		
		if (path_T.getText() == null || path_T.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Invalid Install Path",
					"Path Error", JOptionPane.ERROR_MESSAGE);
			return false;
		
		
		 } else if( !(new File(jh_T.getText().trim() + "/bin/java").isFile())  &&
		 		   !(new File(jh_T.getText().trim() + "/bin/java.exe").isFile())  ) {
		 	JOptionPane.showMessageDialog(null, "Invalid JAVA_HOME Path",
		 		"Path Error", JOptionPane.ERROR_MESSAGE);
		    return false;		 
		
		    
		} 
		 
		 
		if(db_T.isEditable()) { 
			if (db_T.getText() == null || db_T.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Invalid Database Name",
					"Input Error", JOptionPane.ERROR_MESSAGE);
				return false;
			
			} else if (usr_DB.getText() == null || usr_DB.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Invalid Database User",
					"Input Error", JOptionPane.ERROR_MESSAGE);
				return false;
			
			} else if (pw_DB.getText() == null || pw_DB.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Invalid Database Password",
					"Input Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		
		return true;
	}

	


	
	
//--------- 
	
	public void showScreen2() {
		main_F.dispose();
		main_F = new JFrame();
		main_F.getContentPane().setLayout(new BorderLayout());
		main_F.getContentPane().setBackground(Color.WHITE);
		main_F.add(buildTitlePanel(), BorderLayout.NORTH);			
		main_F.add(buildPanel2(), BorderLayout.CENTER);
		main_F.add(buildfooterPanel("Next", "Next3.Action" ), BorderLayout.SOUTH);
		
		main_F.pack();
		main_F.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		main_F.setLocation((d.width - main_F.getSize().width) / 2, (d.height - main_F.getSize().height) / 2);
		main_F.setVisible(true);
	}
		

	public JPanel buildPanel2() {

		JPanel main_P = new JPanel(new GridFlowLayout(10, 10));
		usr_ADM.setText("root");
		usr_USR.setText("user");
		
		main_P.add(new JLabel("Web KeePass Admin/Root UserID:"), new GridFlowLayoutParameter(true, 1));
		main_P.add(usr_ADM, new GridFlowLayoutParameter(false, 2));

		main_P.add(new JLabel("Admin/Root Password:"), new GridFlowLayoutParameter(true, 1));
		main_P.add(pw_ADM, new GridFlowLayoutParameter(false, 2));

		main_P.add(new JLabel("Web KeePass Standard UserID:"), new GridFlowLayoutParameter(true, 1));
		main_P.add(usr_USR, new GridFlowLayoutParameter(false, 2));
		
		main_P.add(new JLabel("Standard Password:"), new GridFlowLayoutParameter(true, 1));
		main_P.add(pw_USR, new GridFlowLayoutParameter(false, 2));

		return main_P;	
	}
	
	
	
	private boolean editScreen2() {	
		if (usr_ADM.getText() == null || usr_ADM.getText().length() < 4 ||
				usr_USR.getText() == null || usr_USR.getText().length() < 4) {
			JOptionPane.showMessageDialog(null, "Invalid - User ID < 4 long",
					"UserID Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}	
		
		if (pw_ADM.getText() == null || pw_ADM.getText().length() < 8 ||
					pw_USR.getText() == null || pw_USR.getText().length() < 8) {
				JOptionPane.showMessageDialog(null, "Invalid - Password < 8 long",
						"UserID Error", JOptionPane.ERROR_MESSAGE);
				return false;
		}
			
		return true;
	}
	
	
//----------- 
	public void showScreen3() {
		main_F.dispose();
		main_F = new JFrame();
		main_F.getContentPane().setLayout(new BorderLayout());
		main_F.getContentPane().setBackground(Color.WHITE);
		bar.setMaximum(0);
		bar.setMaximum(4);
		bar.setStringPainted(true);
		bar.setPreferredSize(path_T.getPreferredSize());
		bar.setVisible(false);
		
		main_F.getContentPane().setLayout(new BorderLayout());
		main_F.getContentPane().setBackground(Color.WHITE);
		main_F.add(buildTitlePanel(), BorderLayout.NORTH);			
		main_F.add(buildPanel3(), BorderLayout.CENTER);
		main_F.add(buildfooterPanel("Install", "Install.Action" ), BorderLayout.SOUTH);
		
		main_F.pack();
		main_F.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		main_F.setLocation((d.width - main_F.getSize().width) / 2, (d.height - main_F.getSize().height) / 2);
		main_F.setVisible(true);
	}
	
	
	public JPanel buildPanel3() {
		JPanel main_P = new JPanel(new GridFlowLayout(10, 10));
		
		port_A.setText("8443");
		dmName_A.setText("localhost");
		main_P.add(new JLabel("Tomcat HTTPS/SSL Port:"), new GridFlowLayoutParameter(true, 1));
		main_P.add(port_A, new GridFlowLayoutParameter(false, 2));

		main_P.add(new JLabel("Your Host Name:"), new GridFlowLayoutParameter(true, 1));
		main_P.add(dmName_A, new GridFlowLayoutParameter(false, 2));

		main_P.add(new JLabel("Enter Encryption Key Value:"), new GridFlowLayoutParameter(true, 1));
		main_P.add(key_A, new GridFlowLayoutParameter(false, 2));

		//main_P.add(new JLabel("Progress"), new GridFlowLayoutParameter(true, 1));
		main_P.add(bar, new GridFlowLayoutParameter(true, 2));

		return main_P;
	
	}
	
	
	private boolean editScreen3() {
		int port = 0;
		try {
			port = Integer.parseInt(port_A.getText());
			
			} catch (Exception e) {port = 0;}
		
		if (port < 200 || port > 65000) {
			JOptionPane.showMessageDialog(null, "Invalid HTTPS/SSL Port",
					"Tomcat Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}	
		
		if (dmName_A.getText() == null || dmName_A.getText().length() < 2) {
			JOptionPane.showMessageDialog(null, "Invalid Host Name",
					"Tomcat Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}	
			
		if (key_A.getText() == null || key_A.getText().length() < 20) {
				JOptionPane.showMessageDialog(null, "Invalid Encryption Key Value - Key value must be > 20 long",
						"Encryption Error", JOptionPane.ERROR_MESSAGE);
				return false;	
		}
		
		return true;
	}
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {
	   
		
		
		if (e.getActionCommand().equals("Next1.Action") ) {
			showScreen1();
				
		} else if (e.getActionCommand().equals("Next2.Action") && editScreen1()  ) {
			showScreen2();
	    
		} else if (e.getActionCommand().equals("Next3.Action") && editScreen2()  ) {
			showScreen3();
	    
	    } else if (e.getActionCommand().equals("Install.Action") && editScreen3()) {
			installAction();

		} else if (e.getActionCommand().equals("Cancel.Action")) {
			main_F.dispose();
	    
		} else if(e.getActionCommand().equals("Embedded.Action")) {
			db_T.setText(null);
			db_T.setEditable(false);
			usr_DB.setText(null);
			usr_DB.setEditable(false);
			pw_DB.setText(null);
			pw_DB.setEditable(false);
		 
	    } else if(e.getActionCommand().equals("MySQL.Action")) {
			db_T.setText("WebKeePass");
			db_T.setEditable(true);
			usr_DB.setText("root");
			usr_DB.setEditable(true);
			pw_DB.setText(null);
			pw_DB.setEditable(true);					 
			 
		 }	    
	    
	}



	public void mouseClicked(MouseEvent e) {
		 if(e.getComponent().getName().equals("Path.Action")) {
		 		pathAction();
		
		 } else if(e.getComponent().getName().equals("JAVA_HOME.Action")) {
			 java_homeAction();
		 	 
		 }
		 
		 
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	private void pathAction() {
		JFileChooser chr = new JFileChooser();
		chr.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chr.showDialog(main_F, "select ") != JFileChooser.APPROVE_OPTION)
			return;
		path_T.setText(chr.getSelectedFile().getAbsolutePath());
	}

	private void java_homeAction() {
		JFileChooser chr = new JFileChooser();
		chr.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chr.showDialog(main_F, "select ") != JFileChooser.APPROVE_OPTION)
			return;
		jh_T.setText(chr.getSelectedFile().getAbsolutePath());
	}
	
	
	
	private void installAction() {
		if (editScreen1() && editScreen2() && editScreen3() ) {
			RunInstall r = new RunInstall();
		  	r.start();
		  	r = null;
		}
	}

	
	

	
	public boolean installDB() {	
		if(db_T.isEditable()) {
			if(!data.installDB(
				db_T.getText().trim(), 
				usr_DB.getText().trim(), pw_DB.getText().trim(),"Install/CreateTablesMySQL.sql", 
				usr_ADM.getText().trim(), sha1(pw_ADM.getText().trim()),
				usr_USR.getText().trim(), sha1(pw_USR.getText().trim()))
				)
				return false;
			
		} else {
			if(!data.installEmbeded(
					path_T.getText(), key1,
					"webkeepass", "passkeeweb" ,"Install/CreateTablesEmbeded.sql", 
					usr_ADM.getText().trim(), sha1(pw_ADM.getText().trim()),
					usr_USR.getText().trim(), sha1(pw_USR.getText().trim()))
					)
				return false;			
			
		}
		
		return true;
	}
	
	
	
	public boolean createConfig() {	
	
		String javaHome = jh_T.getText().trim();
		String path = path_T.getText().trim();
		String user = usr_DB.getText().trim();
		String pwrd = pw_DB.getText().trim();    
		String port = port_A.getText().trim();
		key1 = sha1(key_A.getText().trim());      
		
		
		if(!fi.readFile("Install/ConfigFile1.xml"))
			return false;
		
		
		if(db_T.isEditable()) {
		    fi.mergeTag("{dvr}", "com.mysql.jdbc.Driver");
		    fi.mergeTag("{db}", "jdbc:mysql://localhost/"+db_T.getText().trim());
			fi.mergeTag("{user}", user);
			fi.mergeTag("{password}", pwrd);

		} else {
			fi.mergeTag("{dvr}", "org.apache.derby.jdbc.EmbeddedDriver");
			fi.mergeTag("{db}", "jdbc:derby:" + path + 
					"datasrc;dataEncryption=true;encryptionAlgorithm=DES/CBC/NoPadding;encryptionKey=" + key1.trim() );
			fi.mergeTag("{user}", "webkeepass");
			fi.mergeTag("{password}", "passkeeweb");

		}
		

		fi.mergeTag("{eKey}", key1);
		fi.mergeTag( "{scripts}", 
				path + "jakarta-tomcat-5.5.7"+ fs + "webapps" + fs +"ROOT" +  fs + "XML" + fs);
		fi.mergeTag("{images}", 
				path +"jakarta-tomcat-5.5.7" + fs + "webapps" + fs +"ROOT" +  fs + "XML" + fs + "images" + fs);
		fi.mergeTag( "{schm}",
				path + "jakarta-tomcat-5.5.7"+ fs + "webapps" + fs +"ROOT" + fs + "WEB-INF" + fs +"Schema1.xml");
		fi.mergeTag( "{lits}",
				path + "jakarta-tomcat-5.5.7"+ fs + "webapps" + fs +"ROOT" + fs + "WEB-INF" + fs +"Literal1.xml");
		fi.mergeTag("{logs}", 
				path +"jakarta-tomcat-5.5.7" + fs +"logs" + fs);
		if(!fi.writeFile(path +"jakarta-tomcat-5.5.7"+ fs + "webapps" + fs +"ROOT" + fs + "WEB-INF" + fs +"ConfigFile1.xml"))
			return false;	
		
		
		
		
		
		if(!fi.readFile("Install/web.xml"))
			return false;
		fi.mergeTag( "{home}",  path + "jakarta-tomcat-5.5.7"+ fs + "webapps" + fs +"ROOT" + fs + "WEB-INF" + fs );
		if(!fi.writeFile( path +"jakarta-tomcat-5.5.7"+ fs + "webapps" + fs +"ROOT" + fs + "WEB-INF" + fs +"web.xml"))
			return false;	
		
		
		
		if(!fi.readFile("Install/server.xml"))
			return false;
		fi.mergeTag( "{port}", port);
		fi.mergeTag( "{certFile}",  path + "webKeePass.key");
		if(!fi.writeFile( path +"jakarta-tomcat-5.5.7"+ fs + "conf" + fs +"server.xml"))
			return false;	
		

		
		StringBuffer startL = new StringBuffer("export JAVA_HOME=" + javaHome.trim() +"\n"+
										       "cd ./jakarta-tomcat-5.5.7/bin\n" +
		                      				   "./startup.sh \n");

		StringBuffer startW = new StringBuffer("set JAVA_HOME=" + javaHome.trim() +"\r\n"+ 
											  "cd jakarta-tomcat-5.5.7\\bin\r\n" +
											  "startup.bat \r\n");
		
		Writer sourceFile;
		try {
			sourceFile = new FileWriter(path + "startup.sh" );
			sourceFile.write (startL.toString());
			sourceFile.close();
		
			sourceFile = new FileWriter(path + "startup.bat" );
			sourceFile.write (startW.toString());
			sourceFile.close();	
			
		} catch (IOException e) {
			return false;
		}
		
		
		
		return true;
	}
	
	
	
	class RunInstall extends Thread {		
		
	public RunInstall () {} 
	
	public void run () {
		bar.setVisible(true);
		
		String error = "\n \n - before re-installing please remove: " + path_T.getText() +
		                  "\n   and drop database: " +db_T.getText();
		
		String start =   "Installation is Complete! \n " +
				       "\n   1 - Start Tomcat: " + path_T.getText() +
        			   "\n   2 - Open: https://" +dmName_A.getText().trim() + ":" + port_A.getText() + " \n ";
		try {
		main_F.setCursor(new Cursor(Cursor.WAIT_CURSOR));	 
		if(!path_T.getText().endsWith("/") && !path_T.getText().endsWith("\\")) 
				 path_T.setText(path_T.getText() + fs);
		
		bar.setValue(1);
		if(!zip.unZip("Install/Install.zip", path_T.getText())) {
			JOptionPane.showMessageDialog(null, "Error Expanding Files: \n " + zip.getMessages() + error,
					"Zip Error", JOptionPane.ERROR_MESSAGE);
			throw new InterruptedException();	
		}	
		
		bar.setValue(2);
	    
		if(!createConfig()) {
			JOptionPane.showMessageDialog(null, "Config Write Error \n " + fi.getMessages() + error,
					"Config-Error", JOptionPane.ERROR_MESSAGE);
			throw new InterruptedException();
		}

		
		if(!createCert()) {
			JOptionPane.showMessageDialog(null, "Could not create Certified Key  " + error,
					"Cert Error", JOptionPane.ERROR_MESSAGE);
			throw new InterruptedException();
		}

		bar.setValue(3);
		if(!installDB()) {
			JOptionPane.showMessageDialog(null, "Database Write Error: \n" + data.getMessages() + error,
					"Data Error", JOptionPane.ERROR_MESSAGE);
			throw new InterruptedException();
		}
		
		
		
		bar.setValue(5);
		
		JOptionPane.showMessageDialog(null, start, "Done", JOptionPane.INFORMATION_MESSAGE);
		main_F.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));			
		
		} catch (InterruptedException i) {
			bar.setValue(0);
				
		}
		main_F.dispose();
	 }
	
	}
	
	
	
	
	public boolean createCert() {	
		/*	keytool -genkey 
    	-keystore ~/tomcat/conf/keystore 
    	-alias tomcat 
    	-keyalg RSA 
    	-keysize 2048 
    	-dname CN=localhost 
    	-storepass changeit 
    	-keypass changeit   */
		
		try { 
			String path = path_T.getText().trim();
			String hostname = dmName_A.getText().trim();
			sun.security.tools.KeyTool.main(new String []
				  { "-genkey", 
					"-alias", "tomcat",
					"-keystore", path + "webKeePass.key",
					"-keyalg", "RSA",
					"-keysize", "2048", 
					"-dname", "CN=" + hostname,
					"-storepass", "changeit",
					"-keypass", "changeit" });
			
			} catch (Exception e) {
				e.printStackTrace();
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
	    for (int i=0; i<digest.length; i++) {
	      String h = Integer.toHexString(digest[i]);
	      if (h.length() == 1) h = "0" + h;
	      h = h.substring(h.length()-2);
	      hex += h;
	    }
	    return hex;
	 } catch (NoSuchAlgorithmException e) {
		return data;
	 }
   }
	
	
	public static void main(String[] args) {
		InstallerMain installer = new InstallerMain();
		installer.showLICENSE();
	
		
	}
}
