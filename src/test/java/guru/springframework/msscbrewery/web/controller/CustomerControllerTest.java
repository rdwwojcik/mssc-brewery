package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest{

    @MockBean
    CustomerService customerService;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerDto validCustomer;

    @Before
    public void setUp() throws Exception {
        validCustomer = CustomerDto.builder()
                .name("Some name")
                .build();
    }

    @Test
    public void testGetCustomer() throws Exception {
        when(customerService.getCustomerById(any())).thenReturn(validCustomer);

        mockMvc.perform(get("/api/v1/customer/" + UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(validCustomer.getName())));
    }

    @Test
    public void testHandlePost() throws Exception {
        CustomerDto savedCustomer = CustomerDto.builder().id(UUID.randomUUID()).name("Some name").build();
        when(customerService.saveNewCustomer(validCustomer)).thenReturn(savedCustomer);

        String jsonData = objectMapper.writeValueAsString(validCustomer);
        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isCreated());
    }

    public void testHandleUpdate() {
    }

    public void testDeleteById() {
    }
}