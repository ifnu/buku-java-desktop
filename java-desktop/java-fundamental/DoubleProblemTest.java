import java.math.BigDecimal;
import java.math.RoundingMode;
public class DoubleProblemTest {
  public static void main(String[] args) {
    double dd = .35; 
    BigDecimal d = new BigDecimal(dd);
    System.out.println(".35 = " + d); //hasilnya berantakan karena berasal dari double
    d = d.setScale(2, RoundingMode.HALF_UP);
    System.out.println(".35 = " + d); //hasilnya bagus setelah pembulatan
    BigDecimal dec = new BigDecimal("0.35");
    System.out.println(".35 = " + dec); //hasilnya bagus
  }
} 