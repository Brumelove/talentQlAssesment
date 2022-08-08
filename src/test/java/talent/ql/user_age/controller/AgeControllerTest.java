package talent.ql.user_age.controller;

import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import talent.ql.user_age.service.AgeService;

import java.util.Collections;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * @author Brume
 **/
@ActiveProfiles("test")
@WebMvcTest()
@ContextConfiguration(classes = {AgeController.class})
class AgeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AgeService ageService;
    @Mock
    private AgeService service;
    @InjectMocks
    private AgeController controller;

    @Test
    @SneakyThrows
    void calculateAge() {
        String dob = RandomStringUtils.randomAlphanumeric(5);
        when(service.calculateAge(dob)).thenReturn(4L);
        var response = controller.calculateAge(dob);

        assertNotNull(response);
        assertEquals(4L, response.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/howold/" + dob)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}