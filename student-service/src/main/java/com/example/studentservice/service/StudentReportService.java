package com.example.studentservice.service;
import org.springframework.scheduling.annotation.Async; import org.springframework.stereotype.Service; import java.util.concurrent.CompletableFuture;
@Service public class StudentReportService {
 private final StudentService service; public StudentReportService(StudentService s){service=s;}
 @Async public CompletableFuture<String> buildReport(){ return CompletableFuture.completedFuture("Students count: "+service.findAll().size()); }
}
