package fr.univtln.tdomenge293.dao;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.UUID;

public abstract class GenericDAO<T>  implements AutoCloseable{

    private final Class<T> entityClass;
    final EntityManager entityManager;

    public GenericDAO(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    public T findById( UUID id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll(int pageNumber, int pageSize) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> rootEntry = cq.from(entityClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        allQuery.setFirstResult((pageNumber - 1) * pageSize);
        allQuery.setMaxResults(pageSize);
        return allQuery.getResultList();
    }

    public Long getCount() {
        CriteriaQuery<Long> cq = entityManager.getCriteriaBuilder().createQuery(Long.class);
        cq.select(entityManager.getCriteriaBuilder().count(cq.from(entityClass)));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public T create(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    public T update(T entity) {
        entityManager.getTransaction().begin();
        T mergedEntity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return mergedEntity;
    }

    public void delete(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        entityManager.getTransaction().commit();
    }

    @Override
    public void close () {
        entityManager.close();
    }
}
