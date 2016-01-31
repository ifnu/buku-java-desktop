import java.util.Date;
public class GarbageCollector{
   public static void main(String[] args){
      Date d = getDate();
      //lakukan sesuatu di sini
      System.out.println(d);
   }
   public static Date getDate() {
      StringBuffer buffer = new StringBuffer("garbage collectable");
      Date date = new Date();
      return date;
   }
}