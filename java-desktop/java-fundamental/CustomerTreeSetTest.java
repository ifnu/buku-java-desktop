import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;
public class CustomerTreeSetTest{
  public static void main(String[] args) {
    Set<Customer> customers = new TreeSet<Customer>();
    Customer id1 = new Customer();
    id1.setId(1l);
    customers.add(id1);
    Customer id2 = new Customer();
    id2.setId(2l);
    customers.add(id2);
    Customer c = new Customer();
    c.setId(1l);
    customers.add(c); //mereplace id1 karena mempunyai id yang sama
    Iterator<Customer> i = customers.iterator();
    while(i.hasNext()){
       Customer current = i.next();
       System.out.println("keranjang no-" + current.hashCode() 
         + " idnya:" + current.getId());
    }
  }
}