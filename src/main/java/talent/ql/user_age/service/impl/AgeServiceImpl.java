package talent.ql.user_age.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import talent.ql.user_age.service.AgeService;
import talent.ql.user_age.util.DateUtils;
import talent.ql.user_age.util.TranslatorUtils;

import java.time.DateTimeException;
import java.time.LocalDateTime;

/**
 * @author Brume
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class AgeServiceImpl implements AgeService {
    @Override
    public long calculateAge(String dob) {
        LocalDateTime dateTime;
        try {
            dateTime = DateUtils.parseUnixToLocalDateTime(dob);
        } catch (NumberFormatException e) {
            try {
                dateTime = DateUtils.parseStringToLocalDateTime(dob);
                if (dateTime.getYear() > DateUtils.getCurrentDate().getYear())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TranslatorUtils.toLocale("bad.year.date"));

            } catch (DateTimeException de) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TranslatorUtils.toLocale("date.time.bad.format"));
            }
        }
        log.info("TIMESTAMP ::: ::: " + dateTime);

        return DateUtils.differenceInYears(dateTime.toLocalDate());
    }


}

