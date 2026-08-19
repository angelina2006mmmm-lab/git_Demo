package com.example.studentcrud.service;

import com.example.studentcrud.exception.StudentNotFoundException;
import com.example.studentcrud.model.Student;
import com.example.studentcrud.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        log.info("Получение списка студентов");
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        log.info("Поиск студента по id={}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    @Transactional
    public Student create(Student student) {
        log.info("Создание студента с email={}", student.getEmail());
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student update(Long id, Student student) {
        Student existing = findById(id);

        existing.setFirstName(student.getFirstName());
        existing.setLastName(student.getLastName());
        existing.setEmail(student.getEmail());
        existing.setAge(student.getAge());

        log.info("Обновление студента id={}", id);
        return studentRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Student student = findById(id);
        log.info("Удаление студента id={}", id);
        studentRepository.delete(student);
    }
}
