package com.example.demo.Repos;

import com.example.demo.Models.Document;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocRepo  extends JpaRepository<Document,Long> {

}
