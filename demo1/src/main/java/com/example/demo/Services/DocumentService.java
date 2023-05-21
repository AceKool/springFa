package com.example.demo.Services;

import com.example.demo.Models.Document;
import com.example.demo.Repos.DocRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DocumentService {
    @Autowired
    private DocRepo documentRepo;
    @Value("${upload.path}")
    private String path;
    public void save(Document document) {
        documentRepo.save(document);
    }

    public Document getById(Long id) {
        return documentRepo.findById(id).get();
    }

    public File getFile(Document document) {
        return new File(path + "/" + document.getFilepath());
    }
}
