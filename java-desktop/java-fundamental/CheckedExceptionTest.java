import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class CheckedExceptionTest {
  public static void main(String[] args) {
  	try{
      FileInputStream inputStream = new FileInputStream("buka-file.txt");
  	} catch(FileNotFoundException ex){
  	  ex.printStackTrace();
  	} catch(IOException ex) {
  	  ex.printStackTrace();
  	}
    System.out.println("kode setelah buka file");
  }
}