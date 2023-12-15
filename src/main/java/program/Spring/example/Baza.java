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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Baza {

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

    public static List<Trener> pobierzTrenerowStronicowaniem(int pagenr) {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        List<Trener> trenerzy = null;

        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");

        entityManager = entityManagerFactory.createEntityManager();

        try {
            Query queryTotal = entityManager.createQuery("SELECT COUNT(t) FROM Trener t");
            long countResult = (long) queryTotal.getSingleResult();
            int pageSize = 1;
            int pageNumber = (int) ((countResult / pageSize) + 1);

            if (pagenr > pageNumber) pagenr = pageNumber;

            Query query = entityManager.createQuery("SELECT t FROM Trener t");

            query.setFirstResult((pagenr - 1) * pageSize);
            query.setMaxResults(pageSize);

            trenerzy = query.getResultList();
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

    public boolean usunTrenera(int id) {
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
                return true;
            } else {
                return false;
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


    public static List<Object[]> najwiecejGoli() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Object[]> result = null;
        try {
            TypedQuery<Object[]> query = entityManager.createQuery(
                    "SELECT s.gospodarz, COALESCE(SUM(s.golegospodarza), 0) + COALESCE(SUM(s.golegoscia), 0) " +
                            "FROM Spotkanie s " +
                            "GROUP BY s.gospodarz " +
                            "ORDER BY 2 DESC", Object[].class);

            query.setMaxResults(1);

            result = query.getResultList();

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

        return result;
    }

    @Transactional
    public void dodajObiekt(Object obiekt) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        if (entityManager.contains(obiekt)) {

            System.out.println(obiekt.getClass().getSimpleName() + " znajduje się w bazie");
        } else {
            entityManager.merge(obiekt);
        }

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    public static <T> List<T> pobierzEncje(Class<T> entityType) {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        List<T> encje = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = builder.createQuery(entityType);
            Root<T> root = criteriaQuery.from(entityType);
            criteriaQuery.select(root);

            encje = entityManager.createQuery(criteriaQuery).getResultList();

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

        return encje;
    }
    public boolean edytujTrenera(int id, Trener trener) {
        EntityManager entityManager = null;
        EntityManagerFactory entityManagerFactory = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Trener istniejacyTrener = entityManager.find(Trener.class, id);
            System.out.println(trener.druzyna);

            if (istniejacyTrener != null) {
                istniejacyTrener.imie = trener.imie;
                istniejacyTrener.nazwisko = trener.nazwisko;
                istniejacyTrener.druzyna = trener.druzyna;
                entityManager.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public boolean sprawdzenieDanychTrenera(Trener trener) {
        String imieNazwisko = "^[A-Za-z]+$";
        if ((trener.imie == null || trener.imie.matches(imieNazwisko)) &&
                (trener.nazwisko == null || trener.nazwisko.matches(imieNazwisko))) {
            return true;
        }
        return false;
    }
}