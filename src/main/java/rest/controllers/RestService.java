package rest.controllers;

import fingerprint.entities.VerificationStats;
import fingerprint.interactors.FingerprintInteractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestService {

    @Autowired
    private final FingerprintInteractor service;

    public RestService(FingerprintInteractor service) {
        this.service = service;
    }

    @PostMapping("fingerPrint")
    public ResponseEntity<String> handleFingerprintValidation(@RequestBody FingerprintValidationRequest request) {
        boolean result = service.verifyFingerprint(request.getMatrix());

        return (result)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("stats")
    public VerificationStats getStats() {
        return service.getStats();
    }

}
