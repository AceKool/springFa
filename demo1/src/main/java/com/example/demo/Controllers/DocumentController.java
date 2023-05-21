package com.example.demo.Controllers;

import com.example.demo.Models.Document;
import com.example.demo.Models.Student;
import com.example.demo.Services.DocumentService;
import com.example.demo.Services.StudentService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @Value("${upload.path}")
    private String path;
    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/uploadDocument", consumes = {MediaType.ALL_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.ALL_VALUE, })
    public ResponseEntity<?> upload(@RequestPart("studentId") String id, @RequestPart("file") MultipartFile file, @RequestPart("docName") String docName) throws IOException {
        try {
            Student student = studentService.getById((long) Integer.parseInt(id));
            if(student == null){
                return new ResponseEntity<>("Нет такого юзера", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String fullName = "";
            String fileName = UUID.randomUUID().toString().substring(0,9) + file.getOriginalFilename();
            if(!file.getOriginalFilename().equals("")){
                fullName = path + "/" + fileName;
            }
            file.transferTo(new File(fullName));
            Document document = new Document();
            document.setFileName(docName);
            document.setFilepath(fileName);
            student.getDocuments().add(document);
            documentService.save(document);
            studentService.register(student);
            return new ResponseEntity<>("Документ создан", HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Нет такого юзера", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadDocument(@PathVariable Long id) throws FileNotFoundException {
        Document document = documentService.getById(id);
        File file = documentService.getFile(document);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFilepath() + "\"")
                    .body(resource);
        }
    }
