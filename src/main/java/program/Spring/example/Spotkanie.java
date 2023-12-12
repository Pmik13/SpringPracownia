package program.Spring.example;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "Spotkania")
public class Spotkanie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "gospodarz", nullable = false)
    public String gospodarz;

    @Column(name = "gosc", nullable = false)
    public String gosc;

    @Column(name = "data")
    @Type(type = "org.hibernate.type.ZonedDateTimeType")
    public ZonedDateTime data;
    public Spotkanie(){

    }
    public Spotkanie(Integer id, String gospodarz, String gosc){
        this.id = id;
        this.gospodarz = gospodarz;
        this.gosc = gosc;
        this.data = ZonedDateTime.now();
    }
}