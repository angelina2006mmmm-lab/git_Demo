package com.example.studentservice.exception;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler {
 @ExceptionHandler(StudentNotFoundException.class) public ResponseEntity<Map<String,Object>> notFound(StudentNotFoundException e){ return ResponseEntity.status(404).body(Map.of("status",404,"message",e.getMessage())); }
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<Map<String,String>> validation(MethodArgumentNotValidException e){ Map<String,String> m=new LinkedHashMap<>(); e.getBindingResult().getFieldErrors().forEach(x->m.put(x.getField(),x.getDefaultMessage())); return ResponseEntity.badRequest().body(m); }
}
