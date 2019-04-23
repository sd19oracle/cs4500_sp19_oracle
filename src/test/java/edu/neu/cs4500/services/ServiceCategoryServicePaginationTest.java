package edu.neu.cs4500.services;

import edu.neu.cs4500.models.ServiceCategory;
import edu.neu.cs4500.repositories.PagedServiceCategoryRepository;
import edu.neu.cs4500.repositories.ServiceCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ServiceCategoryService.class)
public class ServiceCategoryServicePaginationTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private ServiceCategoryRepository serviceCategoryRepository;

    @MockBean
    private PagedServiceCategoryRepository pagedServiceCategoryRepository;

    ServiceCategory c1 = new ServiceCategory();
    ServiceCategory c2 = new ServiceCategory();
    ServiceCategory c3 = new ServiceCategory();
    ServiceCategory c4 = new ServiceCategory();
    ServiceCategory c5 = new ServiceCategory();
    ServiceCategory c6 = new ServiceCategory();

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
        c4.setId(4);
        c4.setServiceCategoryName("Animal Services");
        c4.setPopularity(105);
        c4.setIcon("4");
        c5.setId(5);
        c5.setServiceCategoryName("Tutoring");
        c5.setPopularity(50);
        c5.setIcon("5");
        c6.setServiceCategoryName("Vehicular");
        c6.setId(6);
        c6.setPopularity(60);
        c6.setIcon("6");
    }

    @Test
    public void testFirstPage() throws Exception {
        List<ServiceCategory> categories = Arrays.asList(c1, c2, c3, c4, c5, c6);
        Pageable pageable = PageRequest.of(0, 5);
        Page<ServiceCategory> pagesContent = new PageImpl<>(categories, pageable, categories.size());
        when(pagedServiceCategoryRepository.findAll(pageable)).thenReturn(pagesContent);

        this.mockmvc.perform(get("/api/categories/paged").param("pageNum", "0").param("ipp", "5"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(6))).andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[5].id", is(6)))
                .andExpect(jsonPath("$.content.[*].id", containsInAnyOrder(1, 2, 3, 4, 5, 6)))
                .andExpect(jsonPath("$.totalElements", is(6))).andExpect(jsonPath("$.totalPages", is(2)));
        verify(pagedServiceCategoryRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(pagedServiceCategoryRepository);
    }

    @Test
    public void emptyPage() throws Exception {
        List<ServiceCategory> categories = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 5);
        Page<ServiceCategory> pagesContent = new PageImpl<>(categories, pageable, categories.size());
        when(pagedServiceCategoryRepository.findAll(pageable)).thenReturn((pagesContent));

        this.mockmvc.perform(get("/api/categories/paged").param("pageNum", "0").param("ipp", "5"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(0))).andExpect(jsonPath("$.totalElements", is(0)))
                .andExpect(jsonPath("$.totalPages", is(0)));
        verify(pagedServiceCategoryRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(pagedServiceCategoryRepository);
    }

    @Test
    public void getSecondPage() throws Exception {
        List<ServiceCategory> categories = Arrays.asList(c1, c2, c3, c4);
        Pageable pageable = PageRequest.of(1, 2);
        Page<ServiceCategory> pagesContent = new PageImpl<>(categories, pageable, categories.size());
        when(pagedServiceCategoryRepository.findAll(pageable)).thenReturn(pagesContent);

        this.mockmvc.perform(get("/api/categories/paged").param("pageNum", "1").param("ipp", "2"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(4))).andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(1, 2, 3, 4)))
                .andExpect(jsonPath("$.totalElements", is(4))).andExpect(jsonPath("$.totalPages", is(2)));

        verify(pagedServiceCategoryRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(pagedServiceCategoryRepository);
    }

    @Test
    public void changePage() throws Exception {
        List<ServiceCategory> categories = Arrays.asList(c1, c2, c3, c4, c5);
        Pageable pageable0 = PageRequest.of(0, 2);
        Pageable pageable1 = PageRequest.of(0, 4);
        Page<ServiceCategory> page0 = new PageImpl<>(categories, pageable0, categories.size());
        Page<ServiceCategory> page1 = new PageImpl<>(categories, pageable1, categories.size());
        when(pagedServiceCategoryRepository.findAll(pageable0)).thenReturn(page0);
        when(pagedServiceCategoryRepository.findAll(pageable1)).thenReturn(page1);

        this.mockmvc.perform(get("/api/categories/paged").param("paneNum", "0").param("ipp", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(1, 2, 3, 4, 5)))
                .andExpect(jsonPath("$.totalElements", is(5)))
                .andExpect(jsonPath("$.totalPages", is(3)));


        this.mockmvc.perform(get("/api/categories/paged").param("paneNum", "0").param("ipp", "4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(1, 2, 3, 4, 5)))
                .andExpect(jsonPath("$.totalElements", is(5)))
                .andExpect(jsonPath("$.totalPages", is(2)));

        verify(pagedServiceCategoryRepository, times(1)).findAll(pageable0);
        verify(pagedServiceCategoryRepository, times(1)).findAll(pageable1);
        verifyNoMoreInteractions(pagedServiceCategoryRepository);

    }

}
