package com.example.studentservice.model;
import jakarta.persistence.*; import jakarta.validation.constraints.*;
@Entity @Table(name="students")
public class Student {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank private String firstName; @NotBlank private String lastName;
 @Email @NotBlank @Column(unique=true) private String email;
 @NotNull @Min(16) private Integer age;
 public Student(){} public Long getId(){return id;} public void setId(Long id){this.id=id;}
 public String getFirstName(){return firstName;} public void setFirstName(String v){firstName=v;}
 public String getLastName(){return lastName;} public void setLastName(String v){lastName=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public Integer getAge(){return age;} public void setAge(Integer v){age=v;}
}
