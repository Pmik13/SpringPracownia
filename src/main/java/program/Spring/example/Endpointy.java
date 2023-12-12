package program.Spring.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class Endpointy {

    @Autowired
    public Baza baza;
    @GetMapping("/druzyny")
    public List<Druzyna> getDruzyny(){
        return Baza.pobierzDruzyny();
    }

    @GetMapping("/spotkanie")
    public Spotkanie kolejnespotkanie(){
        return Baza.znajdzNajblizszeSpotkanie();
    }

}
