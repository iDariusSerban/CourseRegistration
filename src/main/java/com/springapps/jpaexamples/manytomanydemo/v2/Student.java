package com.springapps.jpaexamples.manytomanydemo.v2;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @OneToMany(mappedBy ="student")
    private Set<CurseRegistration> courseRegistrations;

    public Student() {
    }
}
