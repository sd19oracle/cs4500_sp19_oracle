package edu.neu.cs4500.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.ServiceCategory;
import edu.neu.cs4500.repositories.ServiceCategoryRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceCategoryService.class)
public class ServiceCategoryServiceTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private ServiceCategoryRepository serviceCategoryRepository;

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


}
