package com.example.studentservice.service;
import com.example.studentservice.event.StudentCreatedEvent; import com.example.studentservice.exception.StudentNotFoundException; import com.example.studentservice.model.Student; import com.example.studentservice.repository.StudentRepository;
import org.springframework.kafka.core.KafkaTemplate; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service public class StudentService {
 private final StudentRepository repo; private final KafkaTemplate<String,StudentCreatedEvent> kafka;
 public StudentService(StudentRepository r,KafkaTemplate<String,StudentCreatedEvent> k){repo=r;kafka=k;}
 public List<Student> findAll(){return repo.findAll();}
 public Student findById(Long id){return repo.findById(id).orElseThrow(()->new StudentNotFoundException(id));}
 @Transactional public Student create(Student s){ Student saved=repo.save(s); kafka.send("student.created",saved.getId().toString(),new StudentCreatedEvent(saved.getId(),saved.getEmail(),saved.getFirstName()+" "+saved.getLastName())); return saved; }
 @Transactional public Student update(Long id,Student s){ Student x=findById(id); x.setFirstName(s.getFirstName());x.setLastName(s.getLastName());x.setEmail(s.getEmail());x.setAge(s.getAge()); return repo.save(x); }
 @Transactional public void delete(Long id){repo.delete(findById(id));}
}
