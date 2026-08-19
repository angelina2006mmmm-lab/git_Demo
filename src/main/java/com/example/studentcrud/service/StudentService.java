package com.example.studentcrud.service;

import com.example.studentcrud.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(Long id);
    Student create(Student student);
    Student update(Long id, Student student);
    void delete(Long id);
}
