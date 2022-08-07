package talent.ql.user_age.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import talent.ql.user_age.MockitoJunitRunner;
import talent.ql.user_age.util.TranslatorUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Brume
 **/

class AgeServiceImplTest extends MockitoJunitRunner {

    @InjectMocks
    private AgeServiceImpl ageService;

    @BeforeEach
    public void init() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");

        new TranslatorUtils(rs);
    }

    @Test
    void calculateAge() {
        String timeStamp = "2020-02-09 12:22:09";
        var response = ageService.calculateAge(timeStamp);
        assertEquals(2, response);
    }

    @Test
    void calculateAgeWithException() {
        String timeStamp = "2024-02-09 12:22:09";

        var exception = Assertions.assertThrows(ResponseStatusException.class, () -> ageService.calculateAge(timeStamp));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Year can not be in the future", exception.getReason());
    }

    @Test
    void calculateAgeWithException2() {
        String timeStamp = "2024-02-09";

        var exception = Assertions.assertThrows(ResponseStatusException.class, () -> ageService.calculateAge(timeStamp));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("timestamp format is wrong please use YYYY-MM-DD HH:mm:ss", exception.getReason());
    }
}