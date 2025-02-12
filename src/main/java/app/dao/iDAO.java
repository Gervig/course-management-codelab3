package app.dao;

import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public interface iDAO<T>
{
    T getInstance(EntityManagerFactory _emf);

    T create(T item);

    T getById(int id);

    T remove(int id);

    List<T> getAll();
}
