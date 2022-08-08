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

/**
 * @author Brume
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class AgeServiceImpl implements AgeService {
    @Override
    public long calculateAge(String timeStamp) {
        try {
            var dateTime = DateUtils.parseToLocalDateTime(timeStamp);
            if (dateTime.getYear() > DateUtils.getCurrentDate().getYear())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TranslatorUtils.toLocale("bad.year.date"));

            log.info("TIMESTAMP ::: ::: " + dateTime);
            return DateUtils.differenceInYears(dateTime.toLocalDate());
        } catch (DateTimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TranslatorUtils.toLocale("date.time.bad.format"));
        }
    }


}

