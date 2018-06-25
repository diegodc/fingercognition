package rest.controllers;

/**
 * Un simple POJO para encapsular el request.
 */
public class FingerprintValidationRequest {

    private String[] matrix;

    public FingerprintValidationRequest() {}

    public FingerprintValidationRequest(String[] matrix) {
        this.matrix = matrix;
    }

    public String[] getMatrix() {
        return matrix;
    }

}
