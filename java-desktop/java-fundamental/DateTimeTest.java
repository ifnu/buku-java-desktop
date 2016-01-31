import org.joda.time.DateTime;
import java.util.Date;
import java.util.Calendar;
public class DateTimeTest {
  public static void main(String[] args) {
    DateTime dateTime = new DateTime(); //waktu sekarang
    int date = dateTime.getDayOfMonth();
    int month = dateTime.getMonthOfYear(); //dimulai dari 1 hingga 12 
    int year = dateTime.getYear();
    DateTime plusMonth = dateTime.plusMonths(2);
    DateTime plusMinutes = dateTime.plusMinutes(2);
    Date d = plusMonth.toDate(); //mengubah DateTime ke Date
    Calendar c = plusMinutes.toGregorianCalendar(); //mengubah DateTime ke Calendar
    System.out.println(dateTime);
  }
}