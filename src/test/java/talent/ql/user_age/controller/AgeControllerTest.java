package talent.ql.user_age.controller;

import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;
import talent.ql.user_age.service.AgeService;
import talent.ql.user_age.util.TranslatorUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Brume
 **/
@ActiveProfiles("test")
@WebMvcTest()
@ContextConfiguration(classes = {AgeController.class})
class AgeControllerTest {
    @MockBean
    AgeService ageService;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AgeService service;
    @InjectMocks
    private AgeController controller;

    @BeforeEach
    public void init() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");

        new TranslatorUtils(rs);
    }

    @Test
    @SneakyThrows
    void calculateAge() {
        String dob = RandomStringUtils.randomAlphanumeric(5);
        when(service.calculateAge(dob)).thenReturn(4L);
        var response = controller.calculateAge(dob);

        assertNotNull(response);
        assertEquals(4L, response.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/howold?dob=" + dob)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @SneakyThrows
    void calculateAgeWithRateLimit() {
        String dob = RandomStringUtils.randomAlphanumeric(5);

        for (int i = 0; i < 4; i++) {
            if (i > 3) {

                mockMvc.perform(MockMvcRequestBuilders.get("/howold?dob=" + dob)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("You have exhausted your API Request Quota"))
                        .andExpect(status().isTooManyRequests());
            }
        }
    }

    @Test
    @SneakyThrows
    void calculateAgeWithRateLimit2() {
        String dob = RandomStringUtils.randomAlphanumeric(5);

        for (int i = 0; i < 4; i++) {
            if (i > 3) {

                mockMvc.perform(MockMvcRequestBuilders.get("/howold?dob=" + dob)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("You have exhausted your API Request Quota"))
                        .andExpect(status().isTooManyRequests());
            }
        }
    }

    @Test
    void calculateAgeWithException() {
        String timeStamp = "2024-02-09 12:22:09";
        for (int i = 0; i < 4; i++) {
            if (i > 3) {
                var exception = Assertions.assertThrows(ResponseStatusException.class, () -> controller.calculateAge(timeStamp));
                assertEquals(HttpStatus.TOO_MANY_REQUESTS, exception.getStatus());
                assertEquals("Year can not be in the future", exception.getReason());
            }
        }
    }

}