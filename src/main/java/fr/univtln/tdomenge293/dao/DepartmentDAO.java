package fr.univtln.tdomenge293.dao;

import fr.univtln.tdomenge293.model.Department;
import jakarta.persistence.EntityManager;
public class DepartmentDAO extends GenericDAO<Department>{

    public DepartmentDAO ( EntityManager entityManager ) {
        super(Department.class, entityManager);
    }
}
