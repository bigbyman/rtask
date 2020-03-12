package com.bigbyman.rtask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Patient extends BaseEntity {
    @NotNull
    @Column(unique = true)
    private String pesel;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    @OrderBy("localDate DESC")
    @Nullable
    private List<Visit> visits;

    public Patient() {
    }

    public Patient(String name, String lastName, String pesel) {
        this.name = name;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Nullable
    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(@Nullable List<Visit> visits) {
        this.visits = visits;
    }
}
