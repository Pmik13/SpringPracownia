package program.Spring.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class Endpointy {

    @Autowired
    public Baza baza;
    @GetMapping("/druzyny")
    public List<Druzyna> getDruzyny(){
        return Baza.pobierzDruzyny();
    }

    @GetMapping("/spotkania/get")
    public List<Spotkanie> getSpotkania(){
        return Baza.pobierzSpotkania();
    }

    @GetMapping("/trenerzy/get")
    public List<Trener> getTrenerzy(){
        return Baza.pobierzTrener();
    }

    @DeleteMapping("/trenerzy/{id}")
    public ResponseEntity<String> usunTrenera(@PathVariable int id){
        try {
            // Wywołaj metodę usuwającą trenera z bazy
            System.out.println("cos dizła" + id);
            baza.usunTrenera(id);
            return ResponseEntity.ok("Trener został usunięty.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Wystąpił błąd podczas usuwania trenera.");
        }
    }

    @PostMapping("/trenerzy/dodaj")
    public ResponseEntity<String> dodajTrenera(@RequestBody Trener trener) {
        try {
            System.out.println("Weszlo");
            System.out.println(trener.imie);
            baza.dodajTrenera(trener);
            return ResponseEntity.ok("Trener został dodany.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Wystąpił błąd podczas dodawania trenera.");
        }
    }

    @GetMapping("/spotkania/nastepne")
    public Spotkanie kolejnespotkanie(){
        return Baza.znajdzNajblizszeSpotkanie();
    }

    @PutMapping("/edytuj/{id}")
    public ResponseEntity<String> edytujTrenera(@PathVariable int id, @RequestBody Trener trener) {
        try {
            baza.usunTrenera(id);
            trener.id = id;
            baza.dodajTrenera(trener);
            return ResponseEntity.ok("Trener został zaktualizowany.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Wystąpił błąd podczas aktualizacji trenera.");
        }
    }

}
