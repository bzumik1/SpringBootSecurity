package com.znamenacek.jakub.springBootSecurity.student.service;

import com.znamenacek.jakub.springBootSecurity.student.model.Student;
import com.znamenacek.jakub.springBootSecurity.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Integer id){
        return studentRepository.findById(id);
    }
}
