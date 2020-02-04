package com.znamenacek.jakub.springBootSecurity.student.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor @Setter @Getter
@Table(name = "students")
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 100)
    private String name;
}
