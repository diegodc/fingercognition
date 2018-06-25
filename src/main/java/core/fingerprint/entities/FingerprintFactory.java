package core.fingerprint.entities;

import core.fingerprint.helpers.Encoder;
import core.fingerprint.validation.FingerprintValidator;

public class FingerprintFactory {

    private static final int LENGTH = 4;
    private static final int REQUIRED = 2;

    private static final FingerprintValidator validator = new FingerprintValidator(LENGTH, REQUIRED);

    /**
     * Construye y verifica una nueva huella segun la matriz de minucias dada.
     *
     * @param matrix la matriz de minucias de la huella
     * @return la nueva huella creada
     */
    public static Fingerprint newFingerprint(String[] matrix) {

        String fingerprintId = Encoder.serializeFingerprintMatrix(matrix);
        boolean isValid = validator.isFingerPrint(matrix);

        return new Fingerprint(fingerprintId, isValid);
    }

}
