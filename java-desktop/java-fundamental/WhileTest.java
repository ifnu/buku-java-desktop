public class WhileTest {
  public static void main(String[] args) {
   while(System.currentTimeMillis() % 3 != 0) {
     System.out.println("waktu sekarang dibagi 3 masih ada sisanya");
   }
  }
}