import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
public class MapTest {
  public static void main(String[] args) {
    Map<String,List<Customer>> customerByCityMap =
       new HashMap<String,List<Customer>>();
     List<Customer> jakartaCust = new ArrayList<Customer>();
     Customer a = new Customer();
     a.setId(1l);
     jakartaCust.add(a);
     Customer b  = new Customer();
     b.setId(2l);
     jakartaCust.add(b);
     customerByCityMap.put("jakarta",jakartaCust);
     List<Customer> surabayaCust = new ArrayList<Customer>();
     Customer c = new Customer();
     c.setId(3l);
     surabayaCust.add(c);
     customerByCityMap.put("surabaya",surabayaCust);
     Set<String> keys =  customerByCityMap.keySet();
     Iterator<String> iterator = keys.iterator();
     while(iterator.hasNext()) {
       String key = iterator.next();
       List<Customer> customers =  customerByCityMap.get(key);
       for(int i = 0;i < customers.size(); i++) {
         Customer cust = customers.get(i);
         System.out.println("kota : " + key + ", Customer id : " + cust.getId());
       }
     }
  }
}