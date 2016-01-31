public class StackTraceRootCause {
	public void methodPertama() {
		System.out.println("method pertama dipanggil");
		methodKedua();
	}
	public void methodKedua() {
		System.out.println("method kedua dipanggil");
		methodKetiga();
	}
	public void methodKetiga() {
				System.out.println("method ketiga dipanggil");
		methodKeempat();
	}
	public void methodKeempat() {
		System.out.println("method keempat dipanggil");
		methodKelima();
	}
	public void methodKelima() {
		System.out.println("method kelima dipanggil");
		String abc  = null;
		abc.toString(); //kode ini akan menyebabkan NullPointerException
	}
	public static void main(String[] args) {
		StackTraceRootCause strc = new StackTraceRootCause();
		strc.methodPertama();
		System.out.println("kode ini tidak akan dieksekusi " +
		"karena program sudah keluar " +
		"ketika exception di methodKelima tidak ditangkap");
	}

}