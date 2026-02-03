package com.example.projectcalculator.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class Project {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be max 50 characters")
    private String name;

    @Size(max = 200, message = "Description must be max 200 characters")
    private String description;

    @FutureOrPresent(message = "Deadline cannot be in the past")
    private LocalDate deadline;

    private List<SubProject> subProjects;

    public Project() {
    }

    public Project(Long id, String name, String description, LocalDate deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public List<SubProject> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(List<SubProject> subProjects) {
        this.subProjects = subProjects;
    }
}
