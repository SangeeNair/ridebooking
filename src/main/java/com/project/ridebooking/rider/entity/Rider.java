package com.project.ridebooking.rider.entity;

import jakarta.persistence.*;


@Entity
@Table(name="riders")
public class Rider {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;

    protected Rider(){}

    public Rider(String name, String phone){
        this.name = name;
        this.phone = phone;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
