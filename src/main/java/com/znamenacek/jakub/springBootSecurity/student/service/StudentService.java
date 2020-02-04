package com.znamenacek.jakub.springBootSecurity.student.service;

import com.znamenacek.jakub.springBootSecurity.student.model.Student;
import com.znamenacek.jakub.springBootSecurity.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Integer id){
        return studentRepository.findById(id);
    }

    public Optional<Student> updateStudentById(Integer id, Student student){
        var studentInDb = getStudentById(id);
        if(studentInDb.isPresent()){
            student.setId(studentInDb.get().getId());
            studentRepository.save(student);
            return Optional.of(student);
        }
        return Optional.empty();
    }

    public Optional<Student> deleteStudentById(Integer id){
        var studentInDb = getStudentById(id);
        getStudentById(id).ifPresent(studentRepository::delete);
        return studentInDb;
    }

//    public Optional<Student> updateStudentById(Integer id, Student student){
//        var studentInDb = getStudentById(id);
//        studentInDb.ifPresent(studentRepository.sa);
//    }
}
