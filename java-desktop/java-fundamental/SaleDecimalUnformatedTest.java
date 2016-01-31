import java.util.Locale;
public class SaleDecimalUnformatedTest { 
  public static void main(String args[]) { 
    double amount = 1000000.05; 
    double discount = amount * 0.10; 
    double total = amount - discount; 
    double tax = total * 0.05; 
    double taxedTotal = tax + total; 
    System.out.println("Subtotal : "+ amount); 
    System.out.println("Discount : " + discount); 
    System.out.println("Total : " + total); 
    System.out.println("Tax : " + tax); 
    System.out.println("Tax+Total: " + taxedTotal); 
  } 
}