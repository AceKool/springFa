package com.example.demo.Services;

import com.example.demo.Models.Document;
import com.example.demo.Models.Student;
import com.example.demo.Repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;
    public void register(Student student) {
        studentRepo.save(student);
    }

    public Student login(String email, String password) {
        Student student = studentRepo.findByEmail(email);
        if (student == null || !student.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return student;
    }

    public List<Document> getDocuments(Student student) {
        return student.getDocuments();
    }

    public Student getById(Long id){
        return studentRepo.findById(id).get();
    }




}
