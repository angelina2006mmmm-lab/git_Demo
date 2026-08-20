package com.example.studentservice.controller;
import com.example.studentservice.security.JwtService; import org.springframework.http.ResponseEntity; import org.springframework.security.authentication.*; import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/auth") public class AuthController {
 private final JwtService jwt; private final AuthenticationManager auth;
 public AuthController(JwtService j,AuthenticationConfiguration c)throws Exception{jwt=j;auth=c.getAuthenticationManager();}
 @PostMapping("/login") public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest r){auth.authenticate(new UsernamePasswordAuthenticationToken(r.username(),r.password()));return ResponseEntity.ok(Map.of("token",jwt.generate(r.username())));} public record LoginRequest(String username,String password){}
}
