package program.Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import program.Spring.example.Baza;
import program.Spring.example.Druzyna;
import program.Spring.example.Zawodnik;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		Baza baza = new Baza();
		Druzyna druzyna = new Druzyna(2, "Pogon", "Szczecin");
		Zawodnik zawodnik = new Zawodnik("Kamil", "Grosicki", druzyna);

		baza.pobierzSpotkania();
		System.out.println("działa?");
	}
}
