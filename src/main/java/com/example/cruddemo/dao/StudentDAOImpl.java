package com.example.cruddemo.dao;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.cruddemo.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private final EntityManager entityManager;

    
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Student theStudent) {
        this.entityManager.persist(theStudent);
    }

    @Override
    @Transactional
    public Student findById(Integer id) {
        try {
            return this.entityManager.find(Student.class, id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error al buscar estudiante con ID: " + id);
            return null;
        }
    }

    @Override
    @Transactional
    public List<Student> findAll() {
        return entityManager.createQuery("FROM Student", Student.class).getResultList();
    }

    @Override
    @Transactional
    public List<Student> findByLastName(String lastName) {
        String query = "FROM Student WHERE lastName = :lastName";
        return entityManager.createQuery(query, Student.class)
                            .setParameter("lastName", lastName)
                            .getResultList();
    }

    @Override
    @Transactional
    public void update(Student theStudent) {
        entityManager.merge(theStudent);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            entityManager.remove(student);
            System.out.println("Estudiante con ID " + id + " eliminado.");
        } else {
            System.out.println("No se encontró un estudiante con ID " + id + ".");
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        String query = "DELETE FROM Student";
        int result = entityManager.createQuery(query).executeUpdate();
        if (result == 0) {
            System.out.println("No hay estudiantes para eliminar.");
        } else {
            System.out.println("Todos los estudiantes han sido eliminados.");
        }
    }
}
