package rest.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FingerprintValidationRequestTest {

    @Test
    void getMatrix() {
        String[] matrix = {"AC", "TG"};
        FingerprintValidationRequest request = new FingerprintValidationRequest(matrix);

        assertEquals(matrix, request.getMatrix());
    }

}