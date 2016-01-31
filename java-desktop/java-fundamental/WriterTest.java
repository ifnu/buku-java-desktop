import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class WriterTest {
  public static void main(String[] args) {
  	try{
        BufferedWriter writer = new BufferedWriter(new     
          FileWriter("file.txt"));
        writer.write("HelloWorld ini file text saya\n");
        //menulis text ke dalam file

        writer.write("ini text di baris dua");
        //jangan lupa untuk selalu memanggil close setelah reader tidak digunakan 
        //close juga sekaligus memanggil flush dan menulis data ke dalam file
        writer.close(); 
  	} catch(IOException e) {
  	  e.printStackTrace();
  	}
  }
}