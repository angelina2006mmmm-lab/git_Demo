package com.example.studentservice.controller;
import com.example.studentservice.model.Student; import com.example.studentservice.service.*; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*; import java.util.concurrent.CompletableFuture;
@RestController @RequestMapping("/api/students") public class StudentController {
 private final StudentService service; private final StudentReportService reports;
 public StudentController(StudentService s,StudentReportService r){service=s;reports=r;}
 @GetMapping public List<Student> all(){return service.findAll();}
 @GetMapping("/{id}") public Student one(@PathVariable Long id){return service.findById(id);}
 @PostMapping public ResponseEntity<Student> create(@Valid @RequestBody Student s){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(s));}
 @PutMapping("/{id}") public Student update(@PathVariable Long id,@Valid @RequestBody Student s){return service.update(id,s);}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
 @GetMapping("/report") public CompletableFuture<String> report(){return reports.buildReport();}
}
