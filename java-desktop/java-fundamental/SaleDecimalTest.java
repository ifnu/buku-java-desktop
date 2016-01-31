import java.text.NumberFormat;
import java.util.Locale;
public class SaleDecimalTest { 
  public static void main(String args[]) { 
    double amount = 1000000.05; 
    double discount = amount * 0.10; 
    double total = amount - discount; 
    double tax = total * 0.05; 
    double taxedTotal = tax + total; 
    NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("in","ID")); 
    System.out.println("Subtotal : "+ money.format(amount)); 
    System.out.println("Discount : " + money.format(discount)); 
    System.out.println("Total : " + money.format(total)); 
    System.out.println("Tax : " + money.format(tax)); 
    System.out.println("Tax+Total: " + money.format(taxedTotal)); 
  } 
}