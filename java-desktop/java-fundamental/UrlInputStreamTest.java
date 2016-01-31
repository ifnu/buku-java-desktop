import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLConnection;
public class UrlInputStreamTest {
  public static void main(String[] args) {
    try{
      URL url = new URL("http://www.yahoo.com");
      URLConnection urlconn = url.openConnection();
      InputStream is = urlconn.getInputStream();
      int byteRead;
      do{
        byteRead = is.read();
        System.out.print((char)byteRead);
      } while(byteRead != -1);
    } catch(MalformedURLException ex) {
      ex.printStackTrace();
    } catch(IOException ex) {
      ex.printStackTrace();
    }
  }
}