public class StringImmutableTest {
  public static void main(String[] args){
    String s = "ini string immutable ";
    System.out.println("sebelum operasi concat nilai s : " + s);
    //append tidak merubah variabel s, tetapi dibuat object baru dan hasilnya direturn
    s.concat("concat");
    System.out.println("setelah operasi concat nilai s : " + s);
    String concat = s + s.concat("concat") + " object baru";
  }
}