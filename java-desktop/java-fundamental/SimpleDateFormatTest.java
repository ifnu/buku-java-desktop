import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
public class SimpleDateFormatTest {
  public static void main(String[] args) {
    Date now = new Date();
    System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(now));
    String tanggal = "21/12/1990 08:09:10";
    try {
      Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(tanggal);
      System.out.println(new SimpleDateFormat("dd MMMM yyyy").format(date));
    } catch(ParseException ex) {
      ex.printStackTrace();
    } 
  }
}