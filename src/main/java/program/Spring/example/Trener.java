package program.Spring.example;

import javax.persistence.*;
@Entity
@Table(name = "Trenerzy")
public class Trener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "imie")
    public String imie;

    @Column(name = "nazwisko")
    public String nazwisko;

    @OneToOne
    @JoinColumn(name = "id_klubu")
    public Druzyna druzyna;


    public Trener() {
    }
    public Trener(String imie, String nazwisko, Druzyna druzyna) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.druzyna = druzyna;
    }
}
