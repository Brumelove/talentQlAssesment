package talent.ql.user_age.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talent.ql.user_age.service.AgeService;

import javax.validation.constraints.NotBlank;


/**
 * @author Brume
 **/
@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/howold")
public class AgeController {
    private final AgeService service;


    @GetMapping("/{timeStamp}")
    public ResponseEntity<Long> calculateAge(@NotBlank(message = "{timeStamp.required}") @PathVariable String timeStamp) {
        return ResponseEntity.ok(service.calculateAge(timeStamp));
    }
}
