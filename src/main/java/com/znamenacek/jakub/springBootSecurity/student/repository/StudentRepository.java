package com.znamenacek.jakub.springBootSecurity.student.repository;


import com.znamenacek.jakub.springBootSecurity.student.model.Student;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
}
