package com.example.projectcalculator.service;

import com.example.projectcalculator.model.Project;
import com.example.projectcalculator.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    ///  functionally replaced by getAllProjectsWithTotalHours for now, but keeping it here in case we want to return projects without the total hours in the future
    public List<Project> getAllProjects() {
        return projectRepository.listAllProjects();
    }

    public Project getProjectById(long id) {
        return projectRepository.findProjectById(id);
    }

    public boolean createProject(Project project) {
        return projectRepository.createProject(project);
    }

    public boolean updateProject(Project project) {
        return projectRepository.updateProject(project);
    }

    public boolean deleteProject(long id) {
        return projectRepository.deleteProject(id);
    }

    public List<Project> getAllProjectsWithTotalHours() {
        List<Project> projects = projectRepository.listAllProjects();

        for (Project p : projects) {
            double total = projectRepository.getTotalEstimatedHoursForProject(p.getId());
            p.setTotalEstimatedHours(total);
        }

        return projects;
    }
}
