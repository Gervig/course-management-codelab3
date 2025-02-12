package app.dao;

import app.entities.Course;
import app.entities.Student;
import app.entities.Teacher;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TeacherDAO implements iDAO<Teacher, Integer>
{
    private static EntityManagerFactory emf;
    private static TeacherDAO instance;


    public static TeacherDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new TeacherDAO();
        }
        return instance;
    }

    @Override
    public Teacher create(Teacher teacher)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                em.getTransaction().begin();
                em.persist(teacher);
                em.getTransaction().commit();
                return teacher;
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error creating teacher", e);
            }
        }
    }

    @Override
    public Teacher getById(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                Teacher teacher = em.find(Teacher.class, id);
                if (teacher == null)
                {
                    throw new NullPointerException();
                }
                return teacher;
            } catch (Exception e)
            {
                throw new ApiException(401, "Teacher not found", e);
            }
        }
    }

    @Override
    public Teacher update(Teacher teacher)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Teacher updatedTeacher = em.find(Teacher.class,teacher.getId());
            if(updatedTeacher == null){
                throw new NullPointerException();
            }
            em.getTransaction().begin();
            updatedTeacher = em.merge(teacher);
            em.getTransaction().commit();
            return updatedTeacher;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating teacher", e);
        }
    }

    @Override
    public void remove(Integer id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            try
            {
                Teacher teacher = em.find(Teacher.class, id);
                if (teacher == null)
                {
                    throw new NullPointerException();
                }
                em.getTransaction().begin();
                em.remove(teacher);
                em.getTransaction().commit();
            } catch (Exception e)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error removing teacher", e);
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
                TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Teacher t", Teacher.class);
                List<Teacher> teacherList = query.getResultList();
                //This method is allowed to show an empty list
                return teacherList;
            } catch (Exception e)
            {
                throw new ApiException(401, "Error finding teachers", e);
            }
        }
    }

    public List<Course> getCoursesForTeacher(Integer teacherId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Teacher foundTeacher = em.find(Teacher.class, teacherId);

            return foundTeacher.getCourses().stream().toList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding courses for teacher with id " + teacherId, e);
        }
    }

}
