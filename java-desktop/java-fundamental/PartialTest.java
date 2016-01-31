import org.joda.time.LocalDate;
public class PartialTest {
  public static void main(String[] args) {
    LocalDate birthDay = new LocalDate(1965,1,23);
    long millis = birthDay.toDateTimeAtCurrentTime().getMillis();
    System.out.println(millis); //bernilai negatif karena sebelum 1970
    birthDay = birthDay.plusYears(27); //ultah ke 27
    int year = birthDay.getYear(); //tahun ultah ke 27
  }
}