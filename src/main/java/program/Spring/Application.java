package program.Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import program.Spring.example.Baza;
import program.Spring.example.Druzyna;
import program.Spring.example.Trener;
import program.Spring.example.Zawodnik;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		Baza baza = new Baza();
		Druzyna druzyna = new Druzyna(2, "Pogon", "Szczecin");
		Zawodnik zawodnik = new Zawodnik("Kamil", "Grosicki", druzyna);
		Trener trener = new Trener("Jens", "Gustavson", druzyna);
//		baza.usunTrenera(2);
//		baza.znajdzDruzyneZNajwiecejStrzelonychGoli();
//		baza.pobierzTrenerowStronicowaniem(2);
		System.out.println("działa?");
	}
}
