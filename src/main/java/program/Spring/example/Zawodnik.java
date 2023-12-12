package program.Spring.example;
import javax.persistence.*;

@Entity
@Table(name = "Zawodnicy")
public class Zawodnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "imie")
    public String imie;

    @Column(name = "nazwisko")
    public String nazwisko;

    // Relacja one-to-many z Druzyny
    @ManyToOne
    @JoinColumn(name = "id_druzyny") // Kolumna w tabeli Zawodnicy, która przechowuje klucz obcy do tabeli Druzyny
    private Druzyna druzyna;


    public Zawodnik() {

    }
    public Zawodnik(String imie, String nazwisko, Druzyna druzyna) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.druzyna = druzyna;
    }

}