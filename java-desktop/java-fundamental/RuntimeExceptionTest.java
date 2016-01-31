public class RuntimeExceptionTest {
  public static void main(String[] args){
  	 int i = 0;
  	 try{
       i = Integer.parseInt("abc");
  	 } catch(NumberFormatException ex) {
  	   ex.printStackTrace();
  	 }
     System.out.println("kode setelah exception");
  }
}