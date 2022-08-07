package talent.ql.user_age.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Brume
 **/
@Configuration
public class CustomLocaleResolverConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(new Locale("en"),
            new Locale("fr"));
    private static final String RESOURCE_BUNDLE_NAME = "messages";
    private static final String DEFAULT_ENCODING = "UTF-8";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), SUPPORTED_LOCALES);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename(RESOURCE_BUNDLE_NAME);
        rs.setDefaultEncoding(DEFAULT_ENCODING);
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
}

