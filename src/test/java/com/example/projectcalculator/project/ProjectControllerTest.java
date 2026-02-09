package com.example.projectcalculator.project;

import com.example.projectcalculator.controller.ProjectController;
import com.example.projectcalculator.model.Project;
import com.example.projectcalculator.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    void showProjects_returnsListView_andProjectsInModel() throws Exception {
        Project p1 = new Project(1L, "P1", "Desc", LocalDate.now().plusDays(1));
        p1.setTotalEstimatedHours(12.5);

        when(projectService.getAllProjectsWithTotalHours()).thenReturn(List.of(p1));

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/list"))
                .andExpect(model().attributeExists("projects"));

        verify(projectService).getAllProjectsWithTotalHours();
    }

    @Test
    void showCreateForm_returnsCreateView_andEmptyProjectInModel() throws Exception {
        mockMvc.perform(get("/projects/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/create"))
                .andExpect(model().attributeExists("project"));
    }

    @Test
    void createProject_withValidationErrors_returnsCreateView() throws Exception {
        // name er @NotBlank -> tom => fejl
        mockMvc.perform(post("/projects/create")
                        .param("name", "")
                        .param("description", "x")
                        .param("deadline", LocalDate.now().plusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("project/create"));

        verify(projectService, never()).createProject(any(Project.class));
    }

    @Test
    void createProject_success_redirectsWithSuccessParam() throws Exception {
        when(projectService.createProject(any(Project.class))).thenReturn(true);

        mockMvc.perform(post("/projects/create")
                        .param("name", "New Project")
                        .param("description", "Desc")
                        .param("deadline", LocalDate.now().plusDays(1).toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects?success=Project created successfully"));

        verify(projectService).createProject(any(Project.class));
    }

    @Test
    void createProject_serviceFails_redirectsWithErrorParam() throws Exception {
        when(projectService.createProject(any(Project.class))).thenReturn(false);

        mockMvc.perform(post("/projects/create")
                        .param("name", "New Project")
                        .param("description", "Desc")
                        .param("deadline", LocalDate.now().plusDays(1).toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects?error=Could not create project"));
    }

    @Test
    void showUpdateForm_projectNotFound_redirectsWithError() throws Exception {
        when(projectService.getProjectById(99L)).thenReturn(null);

        mockMvc.perform(get("/projects/99/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects?error=Project not found"));
    }

    @Test
    void showUpdateForm_projectFound_returnsEditView() throws Exception {
        Project p = new Project(1L, "P1", "Desc", LocalDate.now().plusDays(1));
        when(projectService.getProjectById(1L)).thenReturn(p);

        mockMvc.perform(get("/projects/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/edit"))
                .andExpect(model().attributeExists("project"));
    }

    @Test
    void updateProject_withValidationErrors_returnsEditView() throws Exception {
        mockMvc.perform(post("/projects/1/edit")
                        .param("name", "") // invalid
                        .param("description", "Desc")
                        .param("deadline", LocalDate.now().plusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("project/edit"));

        verify(projectService, never()).updateProject(any(Project.class));
    }

    @Test
    void updateProject_success_redirectsWithSuccess() throws Exception {
        when(projectService.updateProject(any(Project.class))).thenReturn(true);

        mockMvc.perform(post("/projects/1/edit")
                        .param("name", "Updated")
                        .param("description", "Desc")
                        .param("deadline", LocalDate.now().plusDays(1).toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects?success=Project updated successfully"));

        verify(projectService).updateProject(any(Project.class));
    }

    @Test
    void deleteProject_success_redirectsWithSuccess() throws Exception {
        when(projectService.deleteProject(1L)).thenReturn(true);

        mockMvc.perform(post("/projects/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects?success=Project deleted successfully"));
    }
}
