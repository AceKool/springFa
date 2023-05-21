package com.example.demo.Controllers;

import com.example.demo.Models.Document;
import com.example.demo.Models.Student;
import com.example.demo.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/*
{
	"name": "user2",
    "email":"dsf",
    "password": "123"
}
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public void register(@RequestBody Student student) {
        studentService.register(student);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam (name="email") String email, @RequestParam (name="password") String password) {
        return new ResponseEntity<>(studentService.login(email, password), HttpStatus.OK);
    }

    @GetMapping("/{id}/documents")
    public List<Document> getDocuments(@PathVariable Long id) {
        Student student = studentService.getById(id);
        return studentService.getDocuments(student);
    }
}
