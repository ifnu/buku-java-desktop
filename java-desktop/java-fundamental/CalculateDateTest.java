import java.util.Calendar;
import java.text.SimpleDateFormat; 
public class CalculateDateTest {
  public static void main(String[] args) {
    Calendar c = Calendar.getInstance();
    c.set(2000,Calendar.JANUARY,1,0,0,0);
    c.add(Calendar.DATE, 20);
    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    System.out.println("date : " + format.format(c.getTime()));
  }
}