package com.example.studentservice.event;
public record StudentCreatedEvent(Long studentId,String email,String fullName){}
