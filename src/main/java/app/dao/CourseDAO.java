package app.dao;

import app.entities.Course;
import app.entities.Student;
import app.entities.Teacher;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CourseDAO implements iDAO<Course, Integer>
{
    private static EntityManagerFactory emf;
    private static CourseDAO instance;

    public static CourseDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new CourseDAO();
        }
        return instance;
    }

    @Override
    public Course create(Course course)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                em.persist(course);
                em.getTransaction().commit();
                return course;
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error creating course", e);
            }
        }
    }

    @Override
    public Course getById(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                Course course = em.find(Course.class, id);
                if (course == null)
                {
                    throw new NullPointerException();
                }
                return course;
            } catch (Exception e)
            {
                throw new ApiException(401, "Course not found", e);
            }
        }
    }

    @Override
    public void remove(Integer id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            try
            {
                Course course = em.find(Course.class, id);
                if (course == null)
                {
                    throw new NullPointerException();
                }
                em.getTransaction().begin();
                em.remove(course);
                em.getTransaction().commit();
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error removing course", e);
            }
        }
    }

    @Override
    public List getAll()
    {
        try(EntityManager em = emf.createEntityManager())
        {
            try
            {
                TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c", Course.class);
                List<Course> courseList = query.getResultList();
                //This method is allowed to show an empty list
                return courseList;
            } catch (Exception e)
            {
                throw new ApiException(401, "Error finding courses", e);
            }
        }
    }
}
