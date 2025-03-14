package com.example.cruddemo.dao;

import java.util.List;

import com.example.cruddemo.entity.Student;

public interface StudentDAO {

    void save(Student theStudent);

    Student findById(Integer id);

    List<Student> findAll();

    void update(Student theStudent);

    void delete(int id);

    void deleteAll();

    List<Student> findByLastName(String lastName);

}