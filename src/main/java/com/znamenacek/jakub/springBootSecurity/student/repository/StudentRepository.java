package com.znamenacek.jakub.springBootSecurity.student.repository;


import com.znamenacek.jakub.springBootSecurity.student.model.Student;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
}
