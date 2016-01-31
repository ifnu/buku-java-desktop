import java.util.Date;
public class EksperimenGarbageCollector{
  public static void main(String[] args){
    Runtime rt = Runtime.getRuntime();
    System.out.println("jumlah memory awal : " + rt.totalMemory());
    for(int i=0;i < 10000000;i++) {
       Date d = new Date();
    }
    System.out.println("jumlah memory sebelum  gc : " + rt.freeMemory());
    System.gc();
    System.out.println("jumlah memory setelah  gc : " + rt.freeMemory());
  }
}