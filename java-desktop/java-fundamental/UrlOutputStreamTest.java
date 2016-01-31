import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLConnection;
public class UrlOutputStreamTest {
  public static void main(String[] args) {
    try{
      URL url = new URL("http://www.yahoo.com");
      URLConnection urlconn = url.openConnection();
      InputStream is = urlconn.getInputStream();
      OutputStream os = new FileOutputStream("yahoo.txt");
      int byteRead;
      do{
        byteRead = is.read();
        os.write(byteRead);
        System.out.print((char)byteRead);
      } while(byteRead != -1);
      os.flush();
      
    } catch(MalformedURLException ex) {
      ex.printStackTrace();
    } catch(IOException ex) {
      ex.printStackTrace();
    }
  }
}