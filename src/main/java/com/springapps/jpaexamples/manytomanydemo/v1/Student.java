package com.springapps.jpaexamples.manytomanydemo.v1;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name="course_id")
    )
    private Set<Course> courses;

    public Student() {
    }
}
