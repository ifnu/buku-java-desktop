import org.joda.time.Period;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
public class PeriodTest {
  public static void main(String[] args) {
    Period p = new Period(1000); //1 detik
    System.out.println("period : " + p);
    p = new Period(2,3,9,125); //2 jam 3 menit 9 detik dan 125 mili detik
    System.out.println("period : " + p);
    DateTime startTime = new DateTime(2000,1,1,9,0,0,0); //1 januari 2000 jam 9
    //menambahkan period ke instant untuk mendapatkan instant baru
    DateTime endTime = startTime.plus(p); 
    System.out.println("end time : " + endTime);
    //Periode nilainya tidak menentu, satu hari belum tentu 24 jam, 
    //tergantung apakah hari itu ada daylight savings atau tidak. 
    //Begitupula satu tahun belum tentu 365 hari, 
    //tergantung kabisat atau tidak. 
    //Mengubah Period ke durasi harus ada waktu awal, 
    //kemudian ditambah dengan period dapat waktu akhir dan dihitung durasinya
    Hours hours = Hours.hoursBetween(startTime,endTime);
    //mendapatkan durasi dalam jam dengan tipe data int
    int hoursBetween = hours.getHours();
    System.out.println("hours duration : " + hours);
    Minutes  minutes = Minutes.minutesBetween(startTime, endTime);
    System.out.println("minutes duration : " + minutes);
  }
}