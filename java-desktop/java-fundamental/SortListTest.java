import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class SortListTest {
  public static void main(String[] args){    
    List<Customer> customers = new ArrayList<Customer>();
    Customer cust1 = new Customer();
    cust1.setId(10l);
    customers.add(cust1);
    Customer cust2 = new Customer();
    cust2.setId(2l);
    customers.add(cust2);
    Customer cust3 = new Customer();
    cust3.setId(5l);
    customers.add(cust3); 
    System.out.println("isi dari list sebelum disorting: ");
    for(int i=0; i< customers.size();i++) {
      System.out.println("index ke-" + i + ":" + customers.get(i) ); 
    }
    Collections.sort(customers);
    System.out.println("isi dari list setelah disorting: ");
    for(int i=0; i< customers.size();i++) {
      System.out.println("index ke-" + i + ":" + customers.get(i) ); 
    }
  }
}