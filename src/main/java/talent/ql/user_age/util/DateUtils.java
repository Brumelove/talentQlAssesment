package talent.ql.user_age.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
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
    public static final String UTC = "UTC";

    /**
     * Parses LocalDateTime in String to LocalDateTime dataType
     * @param dateTime
     * @return LocalDateTime
     */
    public static LocalDateTime parseStringToLocalDateTime(String dateTime) {
        dateTimeFormatter.withZone(ZoneId.of(UTC));
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    /**
     * This method concerts unix epoch timestamp format into LocalDateTime in the UTC timeZOne
     * @param dob
     * @return LocalDateTime
     */
    public static LocalDateTime parseUnixToLocalDateTime(String dob) {
           return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(dob)), ZoneId.of(UTC));
    }

    /**
     * Returns today's date in the UTC timeZone
     * @return LocalDate
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now(ZoneId.of(UTC));
    }

    /** returns the difference in years in long
     * @param request
     * @return Long
     */
    public static Long differenceInYears(LocalDate request) {
        return ChronoUnit.YEARS.between(request, getCurrentDate());
    }
}
