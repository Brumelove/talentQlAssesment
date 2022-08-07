package talent.ql.user_age.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author Brume
 **/
@Component
@NoArgsConstructor
public final class DateUtils {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parseToLocalDateTime(String dateTime) {
        dateTimeFormatter.withZone(ZoneId.of("UTC"));
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now(ZoneId.of("UTC"));
    }

    public static Long differenceInYears(LocalDate request) {
        return ChronoUnit.YEARS.between(request, getCurrentDate());
    }
}
