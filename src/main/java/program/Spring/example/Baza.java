package program.Spring.example;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        // Tworzenie fabryki EntityManagerFactory
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        List<Spotkanie> spotkania = null;
        try {
            // FACTORY NAME HAS TO MATCH THE ONE FROM PERSISTENCE.XML !!!
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");

            // Otwieranie EntityManager
            entityManager = entityManagerFactory.createEntityManager();

            // Otwieranie transakcji
            entityManager.getTransaction().begin();

            // Tworzenie kryteriów zapytania
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Spotkanie> criteriaQuery = builder.createQuery(Spotkanie.class);
            Root<Spotkanie> root = criteriaQuery.from(Spotkanie.class);
            criteriaQuery.select(root);

            // Wykonanie zapytania
            spotkania = entityManager.createQuery(criteriaQuery).getResultList();
            for (Spotkanie spotkanie : spotkania) {
                int id = spotkanie.id;
                String gospodarz = spotkanie.gospodarz;
                String gosc = spotkanie.gosc;
                ZonedDateTime data = spotkanie.data;
                System.out.println("ID: " + id + ", gospodarz " + gospodarz + ",  data spotkania" + data);
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
        return spotkania;
    }
    public static Spotkanie znajdzNajblizszeSpotkanie() {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");

            // Otwieranie EntityManager
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
            // Upewnij się, że zamykasz EntityManager i EntityManagerFactory
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }

}