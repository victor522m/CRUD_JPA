package com.example.cruddemo;

import org.springframework.context.annotation.Bean;

import com.example.cruddemo.dao.StudentDAO;
import com.example.cruddemo.dao.StudentDAOImpl;
import com.example.cruddemo.entity.Student;

import org.springframework.boot.CommandLineRunner;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CruddemoApplication {
    private static final String[] NOMBRES = {
            "Luis", "Pepe", "María", "Ana", "Carlos", "Elena", "Juan", "Lucía", "Pedro", "Sofía"
    };

    private static final String[] APELLIDOS = {
            "Pérez", "García", "López", "Martínez", "Hernández", "Rodríguez", "González", "Sánchez", "Torres", "Ramírez"
    };

   
    public static String generaNombreAleatorio() {
        long currentTime = System.nanoTime();
        int indice = (int) ((currentTime / 3) %  NOMBRES.length);
        return NOMBRES[indice];
    }
    public static int generaNumeroAleatorio() {
        long currentTime = System.nanoTime();
        int numero = (int) (currentTime % 199) + 1;
        return Math.abs(numero); 
    }

    
    public static String generaApellidoAleatorio() {
        long currentTime = System.nanoTime();
        int indice = (int) ((currentTime / 3) % APELLIDOS.length); 
        return APELLIDOS[indice];
    }

    public static void main(String[] args) {
        SpringApplication.run(CruddemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
        return runner -> {

            System.out.println("Guardando estudiantes...");
            for (int i = 0; i < 200; i++) {
                Student student = new Student(generaNombreAleatorio(), generaApellidoAleatorio());
                studentDAO.save(student);
            }
            int i = generaNumeroAleatorio();
            System.out.println("\nBuscando estudiante con ID: " + i);
            Student foundStudent = studentDAO.findById(i);
            if (foundStudent != null) {
                System.out.println(
                        "Estudiante encontrado: " + foundStudent.getFirstName() + " " + foundStudent.getLastName());
            } else {
                System.out.println("No hemos encontrado ningun estudiante con la id : " + i);
            }

           
            System.out.println("\nObteniendo todos los estudiantes...");
            List<Student> allStudents = studentDAO.findAll();
            allStudents.forEach(student -> System.out
                    .println(student.getId() + " " + student.getFirstName() + " " + student.getLastName()));

            
            System.out.println("\nBuscando estudiantes con apellido 'Pérez'...");
            List<Student> studentsByLastName = studentDAO.findByLastName("Pérez");
            studentsByLastName
                    .forEach(student -> System.out.println(student.getFirstName() + " " + student.getLastName()));

            
            System.out.println("\nActualizando estudiante con Id" + foundStudent.getId());
            if (foundStudent != null) {
                foundStudent.setLastName("Muñoz");
                studentDAO.update(foundStudent);
                System.out.println(
                        "Estudiante actualizado: " + foundStudent.getFirstName() + " " + foundStudent.getLastName());
            }

            i = generaNumeroAleatorio();
            System.out.println("\nEliminando estudiante con ID " + i);
            studentDAO.delete(i);

            
            System.out.println("\nEliminando todos los estudiantes...");
            studentDAO.deleteAll();
        };
    }

}
