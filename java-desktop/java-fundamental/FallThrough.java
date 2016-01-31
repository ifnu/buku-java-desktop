public class FallThrough{
	public static void main(String[] args){
		int x = 1;
		switch(x){
			case 1 : 
				System.out.println("satu");
				continue;
			case 2 : 
				System.out.println("dua fall-through");
			case 3 : 
				System.out.println("tiga fall-through");
			case 10 : 
				System.out.println("satu yang kedua fall-through");
			default :
				System.out.println("default fall-through");
		}
	}
}