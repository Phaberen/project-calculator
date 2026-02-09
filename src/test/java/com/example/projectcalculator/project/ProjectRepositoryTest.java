package com.example.projectcalculator.project;

import com.example.projectcalculator.model.Project;
import com.example.projectcalculator.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(ProjectRepository.class)
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository repo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createAndListAllProjects_worksAgainstH2() {
        Project p = new Project(null, "P1", "Desc", LocalDate.now().plusDays(1));
        boolean created = repo.createProject(p);
        assertTrue(created);

        List<Project> all = repo.listAllProjects();
        assertEquals(1, all.size());
        assertEquals("P1", all.get(0).getName());
    }

    @Test
    void findProjectById_returnsNullWhenMissing() {
        Project found = repo.findProjectById(999);
        assertNull(found);
    }
}
