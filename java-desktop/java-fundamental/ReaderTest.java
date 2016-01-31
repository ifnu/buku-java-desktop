import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class ReaderTest {
  public static void main(String[] args) {
  	try{
      BufferedReader reader = new BufferedReader(new FileReader("ReaderTest.java"));
      String line;
      while((line = reader.readLine())!=null) {
        System.out.println(line + "\n");
      }
      reader.close(); //jangan lupa untuk selalu memanggil close setelah reader tidak digunakan 
  	} catch(IOException e) {
  	  e.printStackTrace();
  	}
  }
}