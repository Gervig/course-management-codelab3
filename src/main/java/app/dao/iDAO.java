package app.dao;

import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public interface iDAO<T,I>
{
    T create(T t);

    T getById(I id);

    void remove(I id);

    List<T> getAll();

    //--------------Generic CRUD methods-------------
   // T create(T t);
   // T read(I i);
   // List<T> readAll();
   // T update(T t);
   // void delete(T t);

}