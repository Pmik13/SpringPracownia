package program.Spring.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class Endpointy {

    @Autowired
    public Baza baza;
    @GetMapping("/druzyny/get")
    public List<Druzyna> getDruzyny(){
        return Baza.pobierzEncje(Druzyna.class);
    }

    @GetMapping("/druzyny/najwiecejgoli")
    public List<Object[]> NajwiecejGoli(){
        return Baza.najwiecejGoli();
    }

    @GetMapping("/spotkania/get")
    public List<Spotkanie> getSpotkania(){
        return Baza.pobierzEncje(Spotkanie.class);
    }

    @GetMapping("/trenerzy/get")
    public List<Trener> getTrenerzy(){
        return Baza.pobierzEncje(Trener.class);
    }

    @DeleteMapping("/trenerzy/usun/{id}")
    public ResponseEntity<String> usunTrenera(@PathVariable int id){
        try {
            if(baza.usunTrenera(id))
                return ResponseEntity.ok("Trener został usunięty.");
            else
                return ResponseEntity.status(500).body("Brak Trenera o podanym id");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Wystąpił błąd podczas usuwania trenera.");
        }
    }

    @PostMapping("/trenerzy/dodaj")
    public ResponseEntity<String> dodajTrenera(@RequestBody Trener trener) {
        try {
            System.out.println("Weszlo");
            System.out.println(trener.imie);
            if(!baza.sprawdzenieDanychTrenera(trener)){
                return ResponseEntity.status(500).body("Złe dane");
            }
            baza.dodajObiekt(trener);
            return ResponseEntity.ok("Trener został dodany.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Wystąpił błąd podczas dodawania trenera.");
        }
    }

    @GetMapping("/spotkania/nastepne")
    public Spotkanie kolejnespotkanie(){
        return Baza.znajdzNajblizszeSpotkanie();
    }

    @PutMapping("/trenerzy/edytuj/{id}")
    public ResponseEntity<String> edytujTrenera(@PathVariable int id, @RequestBody Trener trener) {
        try {
            if(!baza.sprawdzenieDanychTrenera(trener)){
                return ResponseEntity.status(500).body("Złe dane");
            }
            if(baza.edytujTrenera(id, trener)){
                return ResponseEntity.ok("Trener został zaktualizowany.");
            }
            System.out.println(trener.druzyna);
            return ResponseEntity.status(500).body("Brak Trenera w bazie o podanym id");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Wystąpił błąd podczas aktualizacji trenera.");
        }
    }

}
