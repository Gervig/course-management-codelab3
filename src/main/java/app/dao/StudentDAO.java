package app.dao;

import app.entities.Course;
import app.entities.Student;
import app.entities.Teacher;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StudentDAO implements iDAO<Student, Integer>
{
    private static EntityManagerFactory emf;
    private static StudentDAO instance;

    public static StudentDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new StudentDAO();
        }
        return instance;
    }

    @Override
    public Student create(Student student)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                em.persist(student);
                em.getTransaction().commit();
                return student;
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error creating student", e);
            }
        }
    }

    @Override
    public Student getById(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                Student student = em.find(Student.class, id);
                if (student == null)
                {
                    throw new NullPointerException();
                }
                return student;
            } catch (Exception e)
            {
                throw new ApiException(401, "Student not found", e);
            }
        }
    }

    @Override
    public Student update(Student student)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Student updatedStudent = em.find(Student.class,student.getId());
            if(updatedStudent == null){
                throw new NullPointerException();
            }
            em.getTransaction().begin();
            updatedStudent = em.merge(student);
            em.getTransaction().commit();
            return updatedStudent;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating student", e);
        }
    }

    @Override
    public void remove(Integer id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            try
            {
                Student student = em.find(Student.class, id);
                if (student == null)
                {
                    throw new NullPointerException();
                }
                em.getTransaction().begin();
                em.remove(student);
                em.getTransaction().commit();
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error removing student", e);
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
                TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
                List<Student> studentList = query.getResultList();
                //This method is allowed to show an empty list
                return studentList;
            } catch (Exception e)
            {
                throw new ApiException(401, "Error finding students", e);
            }
        }
    }
}
