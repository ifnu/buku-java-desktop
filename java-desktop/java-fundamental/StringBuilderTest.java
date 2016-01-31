public class StringBuilderTest {
  public static void main(String[] args){
    StringBuilder s = new StringBuilder("ini StringBuilder tidak immutable ");
    System.out.println("sebelum operasi concat nilai s : " + s);
    //append tidak merubah variabel s, tetapi dibuat object baru dan hasilnya direturn
    s.append("concat");
    System.out.println("setelah operasi concat nilai s : " + s);
    s.append(s.append("concat")).append(" object baru");
  }
}