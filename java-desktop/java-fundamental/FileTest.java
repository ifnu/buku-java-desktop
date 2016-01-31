import java.io.File;
import java.io.IOException;
public class FileTest {
  public static void main(String[] args) {
    File f = new File("newfile.txt");
    if(!f.exists()){
      try{    	
        f.createNewFile();
        System.out.println(f.getAbsolutePath());
      } catch(IOException e){
      	e.printStackTrace();
      }
      
    }
    File folder = new File("newfolder");
    if(!f.exists()) {
       f.mkdir();
       //f.mkdirs(); //membuat folder di dalam folder
    }
    File currentFolder = new File(System.getProperty("user.dir"));
    File[] siblings = currentFolder.listFiles();
    for(int i=0;i<siblings.length;i++){
       File sibling = siblings[i];
       if(sibling.isFile()) {
         System.out.println("f " + sibling.getName());
       } else {
         System.out.println("d " + sibling.getName());
       }
    }
  }
}