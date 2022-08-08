package talent.ql.user_age.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import talent.ql.user_age.service.AgeService;

import javax.validation.constraints.NotBlank;
import java.time.Duration;


/**
 * @author Brume
 **/
@RestController
@Validated
public class AgeController {
    @Autowired
    private  AgeService service;
    private final Bucket bucket;

    /**
     * limit api calls 3rps
     */
    public AgeController() {
        Bandwidth limit = Bandwidth.classic(3, Refill.of(3, Duration.ofSeconds(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping("/howold")
    public ResponseEntity<Long> calculateAge(@NotBlank(message = "{timeStamp.required}") @RequestParam String timeStamp) {
        if (bucket.tryConsume(1)) return ResponseEntity.ok(service.calculateAge(timeStamp));
        throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "You have exhausted your API Request Quota");
    }
}
