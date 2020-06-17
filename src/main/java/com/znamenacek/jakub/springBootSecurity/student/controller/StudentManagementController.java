package com.znamenacek.jakub.springBootSecurity.student.controller;

import com.znamenacek.jakub.springBootSecurity.student.exceptions.NotFoundException;
import com.znamenacek.jakub.springBootSecurity.student.model.Student;
import com.znamenacek.jakub.springBootSecurity.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping(path = "management/api/students")
public class StudentManagementController {
    private final StudentService studentService;

    @Autowired
    public StudentManagementController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public ResponseEntity<List<Student>>  getAllStudents(){
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/{id}") @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id){
        return ResponseEntity.of(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentService.createStudent(student),HttpStatus.ACCEPTED);
    }

    @PutMapping(path ="/{id}")
    public ResponseEntity<Student> updateStudentById(@PathVariable Integer id, @RequestBody Student student){
        return studentService.updateStudentById(id,student)
                .map(student1 -> new ResponseEntity<>(student1, HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
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
