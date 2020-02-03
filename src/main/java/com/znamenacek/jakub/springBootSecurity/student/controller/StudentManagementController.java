package com.znamenacek.jakub.springBootSecurity.student.controller;

import com.znamenacek.jakub.springBootSecurity.student.exceptions.NotFoundException;
import com.znamenacek.jakub.springBootSecurity.student.model.Student;
import com.znamenacek.jakub.springBootSecurity.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "management/api/students")
public class StudentManagementController {
    private final StudentService studentService;

    @Autowired
    public StudentManagementController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>>  getAllStudents(){
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentService.createStudent(student),HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Student> deleteStudentById(@PathVariable Integer id){
        return studentService.deleteStudentById(id)
                .map(student -> new ResponseEntity(student,HttpStatus.ACCEPTED))
                .orElseThrow(()->new NotFoundException(id));
    }

//    @PutMapping(path = "{id}")
//    public ResponseEntity<Student> updateStudentById(@PathVariable Integer id,@RequestBody Student student){
//
//    }
}
