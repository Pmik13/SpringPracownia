package program.Spring.example;
import javax.persistence.*;

@Entity
@Table(name = "Druzyny")
public class Druzyna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "nazwa", nullable = false)
    public String nazwa;

    @Column(name = "miasto")
    public String miasto;

    public Druzyna(){

    }
    public Druzyna(Integer id, String nazwa, String miasto){
        this.nazwa = nazwa;
        this.id = id;
        this.miasto = miasto;
    }
}