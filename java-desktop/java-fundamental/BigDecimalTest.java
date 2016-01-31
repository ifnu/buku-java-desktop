import java.math.BigDecimal; 
import java.math.RoundingMode;
public class BigDecimalTest { 
  public static void main(String args[]) {
    BigDecimal amount = new BigDecimal("1000000.05"); 
    BigDecimal discountPercent = new BigDecimal("0.10"); 
    BigDecimal discount = amount.multiply(discountPercent); 
    discount = discount.setScale(2, RoundingMode.HALF_UP); 
    BigDecimal total = amount.subtract(discount);
    total = total.setScale(2, RoundingMode.HALF_UP); 
    BigDecimal taxPercent = new BigDecimal("0.05"); 
    BigDecimal tax = total.multiply(taxPercent); 
    tax = tax.setScale(2, RoundingMode.HALF_UP); 
    BigDecimal taxedTotal = total.add(tax);
    taxedTotal = taxedTotal.setScale(2, RoundingMode.HALF_UP);
    System.out.println("Subtotal : " + amount);
    System.out.println("Discount : " + discount);
    System.out.println("Total : " + total); 
    System.out.println("Tax : " + tax); 
    System.out.println("Tax+Total: " + taxedTotal); 
  } 
}