package talent.ql.user_age.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.context.support.ResourceBundleMessageSource;
import talent.ql.user_age.MockitoJunitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Brume
 **/
class TranslatorUtilsTest extends MockitoJunitRunner {

    @InjectMocks
    private TranslatorUtils translatorUtils;


    @BeforeEach
    public void init() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");

        new TranslatorUtils(rs);

    }


    @Test
    void toLocale() {
        var response = TranslatorUtils.toLocale("date.time.bad.format");
        assertEquals("timestamp format is wrong please use YYYY-MM-DD HH:mm:ss or unix epoch format", response);
    }

}