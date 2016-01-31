import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.imageio.*;
public class ImageTest {
  public static void main(String[] args) {
    try {
      URL url = 
        new URL("http://ifnubima.org/wp-content/uploads/2010/07/2010-07-25_0334.png");
      URLConnection urlconn = url.openConnection();
      InputStream is = urlconn.getInputStream();
      OutputStream os = new FileOutputStream("image.png");
      int byteRead;
      do {
        byteRead = is.read();
        os.write(byteRead);
      } while (byteRead != -1);
      os.flush();
      os.close();
       //menampilkan image
      Icon icon = new ImageIcon(
        ImageIO.read(new File("image.png")));
      JOptionPane.showMessageDialog(null, "ini gambar","menampilkan gambar",
        JOptionPane.INFORMATION_MESSAGE,
        icon);
      } catch (IOException ex) {
         ex.printStackTrace();
      }
  }
}