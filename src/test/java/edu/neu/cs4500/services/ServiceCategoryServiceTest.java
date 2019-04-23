package edu.neu.cs4500.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceCategory;
import edu.neu.cs4500.repositories.PagedServiceCategoryRepository;
import edu.neu.cs4500.repositories.ServiceCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ServiceCategoryService.class)
public class ServiceCategoryServiceTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private ServiceCategoryRepository serviceCategoryRepository;

    @MockBean
    private PagedServiceCategoryRepository pagedServiceCategoryRepository;

    ServiceCategory c1 = new ServiceCategory();
    ServiceCategory c2 = new ServiceCategory();
    ServiceCategory c3 = new ServiceCategory();

    ServiceCategory c4 = new ServiceCategory(5, "Animal Services");

    public static String asJson(final Object obj) {
        try {
            final ObjectMapper m = new ObjectMapper();
            final String json = m.writeValueAsString(obj);
            return json;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setupTestCases() {
        c1.setId(1);
        c1.setServiceCategoryName("Plumbing");
        c1.setPopularity(100);
        c1.setIcon("1");
        c2.setId(2);
        c2.setServiceCategoryName("Electrician");
        c2.setPopularity(99);
        c2.setIcon("2");
        c3.setId(3);
        c3.setServiceCategoryName("Cleaning");
        c3.setPopularity(90);
        c3.setIcon("3");
    }

    @Test
    public void testCreateNewServiceCategory() throws Exception {
        ServiceCategory c5 = new ServiceCategory();
        c5.setId(5);
        mockmvc.perform(
                post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(c5)))
                        .andExpect(status().isOk());
    }

    @Test
    public void testFindAllServiceCategory() throws Exception {
        List<ServiceCategory> categories = Arrays.asList(c1, c2, c3);
        when(serviceCategoryRepository.findAllServiceCategories())
                .thenReturn(categories);
        this.mockmvc.perform(get("/api/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id",
                        containsInAnyOrder(1,2,3)))
                .andExpect(jsonPath("$.[*].serviceCategoryName",
                        containsInAnyOrder("Plumbing", "Electrician", "Cleaning")));
    }

    @Test
    public void testFindServiceCategoryById() throws Exception {
        List<ServiceCategory> categories = Arrays.asList(c1, c2, c3);
        when(serviceCategoryRepository.findServiceCategoryById(1))
                .thenReturn(c1);

        this.mockmvc.perform(get("/api/categories/1"))
                .andDo(print())
                .andExpect((status().isOk()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.serviceCategoryName", is("Plumbing")))
                .andExpect(jsonPath("$.icon", is("1")));
    }

    @Test
    public void testUpdateServiceCategory() throws Exception {
        ServiceCategory c1_update = new ServiceCategory();
        c1_update.setId(1);
        c1_update.setServiceCategoryName("Fancy Plumbing");
        c1_update.setPopularity(200);
        c1_update.setIcon("1a");
        when(serviceCategoryRepository.save(c1_update)).thenReturn(c1_update);
        when(serviceCategoryRepository.findServiceCategoryById(1)).thenReturn(c1);

        this.mockmvc.perform(
                put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"serviceCategoryName\": \"Fancy Plumbing\", \"popularity\": 200, \"icon\": \"1a\"}"))
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteServiceCategory() throws Exception {
        doNothing().when(serviceCategoryRepository).deleteById(1);
        this.mockmvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk());
    }

}
