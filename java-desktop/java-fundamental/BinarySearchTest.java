import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class BinarySearchTest {
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
    Collections.sort(customers);
    int index = Collections.binarySearch(customers, cust3);
    System.out.println("Customer dengan id:" + cust3.getId() + 
      " ditemukan di index : " + index);
  }
}