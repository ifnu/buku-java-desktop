import java.lang.Comparable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class Customer implements Comparable<Customer>{
  private Long id;
  private List<String> emails;
  public void setId(Long aId){
    this.id = aId;
  }
  public Long getId() {
    return this.id;
  }
  public void setEmails(List<String> aEmails) {
    this.emails = aEmails;
  }
  public List<String> getEmails() {
    return Collections.unmodifiableList(emails);
  }
  public void addEmail(String email){
    if(this.emails == null) {
      this.emails = new ArrayList<String>();
    }
    emails.add(email);
  }
  public int compareTo(Customer c) {
    return getId().compareTo(c.getId());
  }
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
     }
     if (getClass() != obj.getClass()) {
      return false;
    }
    final Customer other = (Customer) obj;
    if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }
  public int hashCode() {
    int hash = 7;
    hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }
}