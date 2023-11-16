package com.springapps.jpaexamples.manytomanydemo.v1;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @ManyToMany(mappedBy ="courses")
    private Set<Student> students;

    public Course() {
    }
}
