public class DuckedException {
	public static void main(String[] args){
		int i = 0;
		while(true){
			i++;
			if(i == 100){
				String abc = null;
				abc.toString();
			}
		}
	}
}