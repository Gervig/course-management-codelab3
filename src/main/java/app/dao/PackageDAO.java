package app.dao;

import app.entities.Package;
import app.enums.PackageDeliveryStatus;
import app.exceptions.ApiException;
import jakarta.persistence.*;

import java.util.List;

public class PackageDAO
{
    private static EntityManagerFactory emf;
    private static PackageDAO instance;

    private PackageDAO()
    {
    }

    public static PackageDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new PackageDAO();
        }
        return instance;
    }

    public Package create(Package pack)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                // sets 'updated' to LocalDateTime.now()
                pack.setUpdatedToNow();
                em.persist(pack);
                em.getTransaction().commit();
                return pack;
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error creating package", e);
            }
        }
    }

    public Package getByTrackingNumber(String trackingNumber)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                TypedQuery<Package> query = em.createQuery("SELECT p FROM Package p WHERE p.trackingNumber = :trackingNumber", Package.class);
                query.setParameter("trackingNumber", trackingNumber);
                Package pack = query.getSingleResult();
                return pack;
            } catch (Exception e)
            {
                throw new ApiException(401, "Error finding package by tracking number: " + trackingNumber, e);
            }
        }
    }

    public List<Package> getAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                TypedQuery<Package> query = em.createQuery("SELECT p FROM Package p", Package.class);
                List<Package> pack = query.getResultList();
                if (pack.isEmpty())
                {
                    throw new NullPointerException();
                }
                return pack;
            } catch (Exception e)
            {
                throw new ApiException(401, "Error finding packages or database is empty", e);
            }
        }
    }

    public Package updateDeliveryStatusById(PackageDeliveryStatus status, Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Package pack = em.find(Package.class, id);
            em.getTransaction().begin();
            // sets the 'updated' to LocalDateTime.now()
            pack.setUpdatedToNow();
            pack.setDeliveryStatus(status);
            em.getTransaction().commit();
            return pack;
        } catch (IllegalArgumentException iae)
        {
            throw new ApiException(401, "Package not found");
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating package delivery status", e);
        }
    }

    public void remove(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                Package pack = em.find(Package.class, id);
                em.getTransaction().begin();
                em.remove(pack);
                em.getTransaction().commit();
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error removing package", e);
            }
        }
    }
}
