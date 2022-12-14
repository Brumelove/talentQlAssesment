package talent.ql.user_age.util;

import org.junit.jupiter.api.Test;
import talent.ql.user_age.MockitoJunitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Brume
 **/
class DateUtilsTest extends MockitoJunitRunner {

    @Test
    void parseToLocalDateTime() {
        String timeStamp = "2020-08-09 12:00:09";

        assertNotNull(DateUtils.parseStringToLocalDateTime(timeStamp));
    }

    @Test
    void getCurrentDate() {
        assertNotNull(DateUtils.getCurrentDate());
    }


    @Test
    void differenceInYears() {
        var secondDate = DateUtils.parseStringToLocalDateTime("2020-08-09 12:00:09");

        var response = DateUtils.differenceInYears(secondDate.toLocalDate());

        assertNotNull(response);
        assertEquals(2, response);

    }

    @Test
    void parseUnixToLocalDateTime() {
        var response = DateUtils.parseUnixToLocalDateTime("22");

        assertNotNull(response);
    }
}