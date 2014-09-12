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
 

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
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
	JTextField usr_T = new JTextField(20);
	JTextField pw_T = new JTextField(20);
	JFrame main_F; 
 
	String fs, os; 

	public JProgressBar bar = new JProgressBar();


	DataBaseInstaller data = new DataBaseInstaller();
	ZipInstaller zip = new ZipInstaller();
	FileInstaller fi = new FileInstaller();

	StringBuffer msgs = new StringBuffer();

	
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
		
		JPanel ftr_P = new JPanel();
		JButton inst = new JButton("I Agree");
		inst.setActionCommand("Next.Action");
		inst.addActionListener(this);
		ftr_P.add(inst);

		JButton can = new JButton("Cancel");
		can.setActionCommand("Cancel.Action");
		can.addActionListener(this);
		ftr_P.add(can);
		
		l.add(main_P, BorderLayout.CENTER);
		l.add(ftr_P, BorderLayout.SOUTH);
		return l;
	}
	
	
	
	
	public void MainLICENSE() {
		 
		main_F = new JFrame();
		main_F.getContentPane().setLayout(new BorderLayout());
		main_F.getContentPane().setBackground(Color.WHITE);
		main_F.add(buildTitlePanel(), BorderLayout.NORTH);
		main_F.add(buildLncePanel(), BorderLayout.CENTER);
		main_F.pack();
		main_F.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		main_F.setLocation((d.width - main_F.getSize().width) / 2, (d.height - main_F
				.getSize().height) / 2);
		main_F.setVisible(true);
	}

	
	
	public void MainInstaller() {
		main_F.dispose();
		main_F = new JFrame();

		os = System.getProperty("os.name");
		fs = System.getProperty("file.separator");
			
		if(fs.equals("/"))
			path_T.setText("/usr/local/WebKeePass");
		else
			path_T.setText("C:\\WebKeePass");
		
		db_T.setText("WebKeePass");
		usr_T.setText("root");
		
		
		bar.setMaximum(0);
		bar.setMaximum(4);
		bar.setStringPainted(true);
		bar.setPreferredSize(path_T.getPreferredSize());
		
		main_F.getContentPane().setLayout(new BorderLayout());
		main_F.getContentPane().setBackground(Color.WHITE);
		main_F.add(buildTitlePanel(), BorderLayout.NORTH);			
		
		main_F.add(buildInstallingPanel(), BorderLayout.CENTER);
		
		main_F.add(buildfooterPanel(), BorderLayout.SOUTH);
		
		main_F.pack();
		main_F.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		main_F.setLocation((d.width - main_F.getSize().width) / 2, (d.height - main_F
				.getSize().height) / 2);
		main_F.setVisible(true);
	}
	
	
	
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

	public JPanel buildfooterPanel() {
		JPanel ftr_P = new JPanel();
		JButton in_B = new JButton("Install");
		in_B.setActionCommand("Install.Action");
		in_B.addActionListener(this);
		ftr_P.add(in_B);

		JButton cn_B = new JButton("Cancel");
		cn_B.setActionCommand("Cancel.Action");
		cn_B.addActionListener(this);
		ftr_P.add(cn_B);

		return ftr_P;
	}

	public JPanel buildInstallingPanel() {
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
		
		main_P.add(new JLabel("Create MySQL Database/Schema:"), new GridFlowLayoutParameter(
				true, 1));
		main_P.add(db_T, new GridFlowLayoutParameter(false, 2));

		main_P.add(new JLabel("MySQL Admin User Name:"), new GridFlowLayoutParameter(
				true, 1));
		main_P.add(usr_T, new GridFlowLayoutParameter(false, 2));

		main_P.add(new JLabel("MySQL Admin Password:"), new GridFlowLayoutParameter(
				true, 1));
		main_P.add(pw_T, new GridFlowLayoutParameter(false, 2));
		
		main_P.add(new JLabel("Progress:"), new GridFlowLayoutParameter(true, 1));

		
		main_P.add(bar, new GridFlowLayoutParameter(false, 2));
		return main_P;
	
	}

	
	
	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("Next.Action"))
			MainInstaller();
				
		else if (e.getActionCommand().equals("Install.Action"))
			installAction();

		else if (e.getActionCommand().equals("Cancel.Action"))
			main_F.dispose();
	}



	public void mouseClicked(MouseEvent e) {
		 if(e.getComponent().getName().equals("Path.Action"))
		 	pathAction();
		
		 else if(e.getComponent().getName().equals("JAVA_HOME.Action"))
			 java_homeAction();
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
		if (editInstall()) {
			RunInstall r = new RunInstall();
		  	r.start();
		  	r = null;
		}
	}

	
	
	private boolean editInstall() {
		
		if (path_T.getText() == null || path_T.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Invalid Install Path",
					"Path Error", JOptionPane.ERROR_MESSAGE);
			return false;
		
		
		 } else if( !(new File(jh_T.getText().trim() + "/bin/java").isFile())  &&
		 		   !(new File(jh_T.getText().trim() + "/bin/java.exe").isFile())  ) {
		 	JOptionPane.showMessageDialog(null, "Invalid JAVA_HOME Path",
		 		"Path Error", JOptionPane.ERROR_MESSAGE);
		    return false;		 
		 	
		} else if (db_T.getText() == null || db_T.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Invalid Database Name",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return false;
			
		} else if (usr_T.getText() == null || usr_T.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Invalid Database User",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return false;
			
		} else if (pw_T.getText() == null || pw_T.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Invalid Database Password",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		
		return true;
	}

	public boolean installDB(String database, String user, String password) {
		if(!data.installDB(database, user, password, "Install/CreateTables.sql"))
			return false;
		
		return true;
	}
	
	
	public boolean createConfig(String javaHome, String path, String url, String user, String pwrd) {		
		if(!fi.readFile("Install/ConfigFile1.xml"))
			return false;
		fi.mergeTag("{db}", url);
		fi.mergeTag("{user}", user);
		fi.mergeTag("{password}", pwrd);
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
		
		try {
		main_F.setCursor(new Cursor(Cursor.WAIT_CURSOR));	 
		if(!path_T.getText().endsWith("/") && !path_T.getText().endsWith("\\")) 
				 path_T.setText(path_T.getText() + fs);
		
		bar.setValue(1);
		if(!zip.unZip("Install/Install.zip", path_T.getText())) {
			JOptionPane.showMessageDialog(null, "Error Expanding Files: \n " + zip.getMessages(),
					"Zip Error", JOptionPane.ERROR_MESSAGE);
			throw new InterruptedException();	
		}	
		
		bar.setValue(2);
	    
		if(!createConfig(jh_T.getText().trim(), path_T.getText().trim(), 
			 	                        db_T.getText().trim(), usr_T.getText().trim(), pw_T.getText().trim())) {
			JOptionPane.showMessageDialog(null, "Config Write Error \n " + fi.getMessages() ,
					"Config-Error", JOptionPane.ERROR_MESSAGE);
			
			throw new InterruptedException();
		}
			
		bar.setValue(3);
		if(!installDB(db_T.getText(), usr_T.getText(), pw_T.getText())) {
			JOptionPane.showMessageDialog(null, "Database Write Error: \n" + data.getMessages(),
					"Data Error", JOptionPane.ERROR_MESSAGE);
			
			throw new InterruptedException();
		}
		
			
		bar.setValue(5);
		JOptionPane.showMessageDialog(null, "Installation Complete",
				"Done", JOptionPane.INFORMATION_MESSAGE);
		main_F.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));			
		main_F.dispose();
		
		} catch (InterruptedException i) {
			bar.setValue(0);
			main_F.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));		
		}
		
	 }
	}
	
	
	public static void main(String[] args) {
		InstallerMain installer = new InstallerMain();
		installer.MainLICENSE();
	}
}
