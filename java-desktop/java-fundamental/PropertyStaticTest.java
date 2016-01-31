public class PropertyStaticTest {
  public static final String nilaiStatic; 
  public static void main(String[] args) {
    PropertyStaticTest.nilaiStatic =  "nilai dari main";
    System.out.println(nilaiStatic);
    methodUbahPropertyStatic();
    System.out.println(nilaiStatic);
  }
  public static void methodUbahPropertyStatic() {
      PropertyStaticTest.nilaiStatic = "nilai dari methodUbahPropertyStatic";
  }
}