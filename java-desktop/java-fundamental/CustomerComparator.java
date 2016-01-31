import java.util.Comparator;
public class CustomerComparator implements Comparator<Customer>{
   public int compare(Customer c1, Customer c2) {
     return c1.getId().compareTo(c2.getId());
   }
}
