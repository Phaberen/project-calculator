package com.example.projectcalculator.model;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class SubTask {

    private Long id;

    @NotNull(message = "Task id is required")
    private Long taskId;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be max 50 characters")
    private String name;

    @Size(max = 200, message = "Description must be max 200 characters")
    private String description;

    @FutureOrPresent(message = "Deadline cannot be in the past")
    private LocalDate deadline;

    @DecimalMin(value = "0.1", message = "Estimated hours must be at least 0.1")
    private Double estimatedHours;

    public SubTask() {
    }

    public SubTask(Long taskId, Long id, String name, String description, LocalDate deadline, Double estimatedHours) {
        this.taskId = taskId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.estimatedHours = estimatedHours;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public void setEstimatedHours(Double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Double getEstimatedHours() {
        return estimatedHours;
    }
}
