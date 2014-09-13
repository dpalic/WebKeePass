import sun.servlet.http.HttpServer;

public class ServletServer {
 
  public static void main(String[] args) {
    boolean portSet = false;
    boolean servletDirSet = false;
    for (int i = 0; i < args.length; i++) {
      if(args[i].equals("-p")) 
        portSet = true;
      if(args[i].equals("-d")) 
        servletDirSet = true;
    }
    
    int i = 0;
     String[] arguments = new String[args.length + (servletDirSet ? 0 : 2) + (portSet ? 0 : 2)];
    for (; i < args.length; i++) {
      arguments[i] = args[i];
    }
    if(!portSet) {
      arguments[i++] = "-p";
      arguments[i++] = "8100";
    }
    
    if(!servletDirSet) {
      arguments[i++] = "-d";
      String servletDir = System.getProperty("java.class.path");
      servletDir = servletDir.substring(0,servletDir.indexOf(java.io.File.pathSeparator));
      arguments[i++] = servletDir;
    }
    
    HttpServer.main(arguments);
  }
}
 
