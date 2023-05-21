package com.example.demo.Repos;

import com.example.demo.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Student findByEmail(String email);

}
