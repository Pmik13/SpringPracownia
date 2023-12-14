package program.Spring.example;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.util.List;

@Component
public class Baza {

    public static List<Druzyna> pobierzDruzyny() {
        // Tworzenie fabryki EntityManagerFactory
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        List<Druzyna> druzyny = null;
        try {
            // FACTORY NAME HAS TO MATCH THE ONE FROM PERSISTENCE.XML !!!
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");

            // Otwieranie EntityManager
            entityManager = entityManagerFactory.createEntityManager();

            // Otwieranie transakcji
            entityManager.getTransaction().begin();

            // Tworzenie kryteriów zapytania
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Druzyna> criteriaQuery = builder.createQuery(Druzyna.class);
            Root<Druzyna> root = criteriaQuery.from(Druzyna.class);
            criteriaQuery.select(root);

            // Wykonanie zapytania
            druzyny = entityManager.createQuery(criteriaQuery).getResultList();
            for (Druzyna druzyna : druzyny) {
                int id = druzyna.id;
                String nazwa = druzyna.nazwa;
                System.out.println("ID: " + id + ", Nazwa drużyny: " + nazwa);
            }
            // Zatwierdzanie transakcji
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Anulowanie transakcji w przypadku błędu
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            // Zamykanie EntityManager i EntityManagerFactory
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
        return druzyny;
    }
    @Transactional
    public void DodajDruzyne(Druzyna druzyna) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        if (entityManager.contains(druzyna)) {
            // Obiekt jest już w kontekście trwałym, więc użyj merge
            System.out.println("Drużyna znajduje się w bazie");
        } else {

            entityManager.merge(druzyna);
        }

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
    public void DodajZawodnika(Zawodnik zawodnik) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        if (entityManager.contains(zawodnik)) {
            // Obiekt jest już w kontekście trwałym, więc użyj merge
            System.out.println("Zawodnik znajduje się w bazie");
        } else {

            entityManager.merge(zawodnik);
        }

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
    public void PobieranieZawodnika() {
        // Tworzenie fabryki EntityManagerFactory
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            // FACTORY NAME HAS TO MATCH THE ONE FROM PERSISTENCE.XML !!!
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");

            // Otwieranie EntityManager
            entityManager = entityManagerFactory.createEntityManager();

            // Otwieranie transakcji
            entityManager.getTransaction().begin();

            // Tworzenie kryteriów zapytania
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Zawodnik> criteriaQuery = builder.createQuery(Zawodnik.class);
            Root<Zawodnik> root = criteriaQuery.from(Zawodnik.class);
            criteriaQuery.select(root);

            // Wykonanie zapytania
            List<Zawodnik> zawodnicy = entityManager.createQuery(criteriaQuery).getResultList();

            for (Zawodnik zawodnik : zawodnicy) {
                int id = zawodnik.id;
                String imie = zawodnik.imie;
                String nazwisko = zawodnik.nazwisko;
                System.out.println("ID: " + id + ", imie: " + imie + ", nazwisko" + nazwisko);
            }

            // Zatwierdzanie transakcji
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Anulowanie transakcji w przypadku błędu
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            // Zamykanie EntityManager i EntityManagerFactory
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }
    public static List<Spotkanie> pobierzSpotkania() {

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        List<Spotkanie> spotkania = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");

            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Spotkanie> criteriaQuery = builder.createQuery(Spotkanie.class);
            Root<Spotkanie> root = criteriaQuery.from(Spotkanie.class);
            criteriaQuery.select(root);

            spotkania = entityManager.createQuery(criteriaQuery).getResultList();
            for (Spotkanie spotkanie : spotkania) {
                int id = spotkanie.id;
                String gospodarz = spotkanie.gospodarz;
                String gosc = spotkanie.gosc;
                ZonedDateTime data = spotkanie.data;
                System.out.println("ID: " + id + ", gospodarz " + gospodarz + ",  data spotkania" + data);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
        return spotkania;
    }
    public static Spotkanie znajdzNajblizszeSpotkanie() {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");


            entityManager = entityManagerFactory.createEntityManager();

            List<Spotkanie> spotkania = entityManager.createQuery(
                            "SELECT s FROM Spotkanie s WHERE s.data >= :today ORDER BY s.data ASC", Spotkanie.class)
                    .setParameter("today", ZonedDateTime.now())
                    .getResultList();

            if (!spotkania.isEmpty()) {
                return spotkania.get(0);
            } else {
                return null;
            }
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }
    public static List<Trener> pobierzTrener() {

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        List<Trener> trenerzy = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");

            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Trener> criteriaQuery = builder.createQuery(Trener.class);
            Root<Trener> root = criteriaQuery.from(Trener.class);
            criteriaQuery.select(root);

            trenerzy = entityManager.createQuery(criteriaQuery).getResultList();
            for (Trener trener : trenerzy) {
                int id = trener.id;
                String imie = trener.imie;
                String nazwisko = trener.nazwisko;
                System.out.println("ID: " + id + ", imie" + imie + ",  nazwisko" + nazwisko);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
        return trenerzy;
    }

    public static List<Trener> pobierzTrenerowStronicowaniem(int pagenr) {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        List<Trener> trenerzy = null;

        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");

        entityManager = entityManagerFactory.createEntityManager();

        try {
            // Liczenie wszystkich rekordów w tabeli
            Query queryTotal = entityManager.createQuery("SELECT COUNT(t) FROM Trener t");
            long countResult = (long) queryTotal.getSingleResult();

            // Ustawianie pageSize
            int pageSize = 1;

            // Obliczanie liczby stron
            int pageNumber = (int) ((countResult / pageSize) + 1);

            // Sprawdzanie, czy pagenr nie przekracza liczby stron
            if (pagenr > pageNumber) pagenr = pageNumber;

            // Tworzenie zapytania
            Query query = entityManager.createQuery("SELECT t FROM Trener t");

            // Ustawianie numeru strony i ilości wyników na stronie
            query.setFirstResult((pagenr - 1) * pageSize);
            query.setMaxResults(pageSize);

            // Pobieranie wyników
            trenerzy = query.getResultList();
        } finally {
            // Zamykanie EntityManager i EntityManagerFactory
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
        for (Trener trener : trenerzy) {
            int id = trener.id;
            String imie = trener.imie;
            String nazwisko = trener.nazwisko;
            System.out.println("ID: " + id + ", imie" + imie + ",  nazwisko" + nazwisko);
        }
        return trenerzy;
    }

    public void usunTrenera(int id) {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
            entityManager = entityManagerFactory.createEntityManager();

            Trener trener = entityManager.find(Trener.class, id);

            if (trener != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(trener);
                entityManager.getTransaction().commit();
                System.out.println("Trener został usunięty.");
            } else {
                System.out.println("Nie znaleziono trenera o podanym ID.");
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }

    public void dodajTrenera(Trener trener) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        if (entityManager.contains(trener)) {
            // Obiekt jest już w kontekście trwałym, więc użyj merge
            System.out.println("trener znajduje się w bazie");
        } else {

            entityManager.merge(trener);
        }

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }



    public Druzyna znajdzDruzyneZNajwiecejStrzelonychGoli() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Object[]> query = entityManager.createQuery(
                    "SELECT s.gospodarz, COALESCE(SUM(s.golegospodarza), 0) + COALESCE(SUM(s.golegoscia), 0) " +
                            "FROM Spotkanie s " +
                            "GROUP BY s.gospodarz " +
                            "ORDER BY 2 DESC", Object[].class);

            query.setMaxResults(1);

            List<Object[]> result = query.getResultList();

            if (!result.isEmpty()) {
                Object[] row = result.get(0);
                String nazwaDruzyny = (String) row[0];
                Long liczbaStrzelonychGoli = (Long) row[1];

                System.out.println("Najwięcej goli strzelonych przez drużynę '" + nazwaDruzyny + "': " + liczbaStrzelonychGoli);

                // Tutaj możesz zwrócić obiekt Druzyna lub zrobić coś innego z wynikiem
                // Na razie wypisuję informacje na konsolę.
            } else {
                System.out.println("Brak danych.");
            }

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return null; // Tutaj możesz zwrócić obiekt Druzyna lub zrobić coś innego z wynikiem
    }

    @Transactional
    public void dodajObiekt(Object obiekt) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        if (entityManager.contains(obiekt)) {
            // Obiekt jest już w kontekście trwałym, więc użyj merge
            System.out.println(obiekt.getClass().getSimpleName() + " znajduje się w bazie");
        } else {
            entityManager.merge(obiekt);
        }

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}