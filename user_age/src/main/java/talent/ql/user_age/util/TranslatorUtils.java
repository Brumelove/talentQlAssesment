package talent.ql.user_age.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author Brume
 **/
@Component
public class TranslatorUtils {
    private static ResourceBundleMessageSource messageSource;

    @Autowired
    public TranslatorUtils(ResourceBundleMessageSource messageSource) {
        TranslatorUtils.messageSource = messageSource;
    }

    public static String toLocale(String msgCode) {
        return messageSource.getMessage(msgCode, null, LocaleContextHolder.getLocale());
    }

    public static String toLocale(String msgCode, @Nullable Object[] args) {
        return messageSource.getMessage(msgCode, args, LocaleContextHolder.getLocale());
    }
}
