package com.example.projectcalculator.project;

import com.example.projectcalculator.service.ProjectService;
import com.example.projectcalculator.model.Project;
import com.example.projectcalculator.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    private final ProjectRepository repo = Mockito.mock(ProjectRepository.class);
    private final ProjectService service = new ProjectService(repo);

    @Test
    void getAllProjects_delegatesToRepository() {
        when(repo.listAllProjects()).thenReturn(List.of());

        service.getAllProjects();

        verify(repo).listAllProjects();
    }

    @Test
    void getAllProjectsWithTotalHours_setsTotalHoursOnEachProject() {
        Project p1 = new Project(1L, "P1", "D", LocalDate.now().plusDays(1));
        Project p2 = new Project(2L, "P2", "D", LocalDate.now().plusDays(1));

        when(repo.listAllProjects()).thenReturn(List.of(p1, p2));
        when(repo.getTotalEstimatedHoursForProject(1L)).thenReturn(10.0);
        when(repo.getTotalEstimatedHoursForProject(2L)).thenReturn(5.5);

        List<Project> result = service.getAllProjectsWithTotalHours();

        assertEquals(2, result.size());
        assertEquals(10.0, result.get(0).getTotalEstimatedHours());
        assertEquals(5.5, result.get(1).getTotalEstimatedHours());
    }
}
