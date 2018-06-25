package fingerprint.interactors;

import fingerprint.entities.Fingerprint;
import fingerprint.entities.VerificationStats;
import fingerprint.persistence.FingerprintRepository;
import fingerprint.validation.FingerprintValidator;

import java.util.Optional;

/**
 * Define las funcionalidades de la aplicacion. El interactor separa el core de la aplicacion de
 * los frameworks utilizados. Facilitando la integracion y testing.
 */
public class FingerprintInteractor {

    private FingerprintRepository repository;
    private FingerprintValidator validator;

    /**
     * Crea un nuevo interactor asociado al repositorio dado.
     *
     * @param repository el repositorio utilizado por este interactor
     */
    public FingerprintInteractor(FingerprintRepository repository) {
        this.repository = repository;
        this.validator = new FingerprintValidator(4);
    }

    /**
     * Verifica la huella dada analizando su matriz de minucias.
     *
     * @param matrix la matriz de minucias
     * @return true si la huella es valida, false en caso contrario
     */
    public boolean verifyFingerprint(String[] matrix) {

        if (matrixIsNullOrEmpty(matrix))
            return false;
        else
            return verify(matrix);
    }

    private boolean matrixIsNullOrEmpty(String[] matrix) {
        return matrix == null || matrix.length == 0;
    }

    private boolean verify(String[] matrix) {
        String fingerPrintId = Encoder.serializeFingerprintMatrix(matrix);

        Fingerprint fingerprint = getFingerprint(fingerPrintId, matrix);

        return fingerprint.isValid();
    }

    private Fingerprint getFingerprint(String fingerPrintId, String[] matrix) {
        Optional<Fingerprint> optional = repository.findById(fingerPrintId);

        return optional.orElseGet(() -> buildFingerprint(matrix, fingerPrintId));

    }

    private Fingerprint buildFingerprint(String[] matrix, String fingerPrintId) {

        boolean isValid = validator.isFingerPrint(matrix);

        Fingerprint fingerprint = new Fingerprint(fingerPrintId, isValid);

        repository.save(fingerprint);

        return fingerprint;
    }

    /**
     * Retorna un VerificationStats con los datos de verificacion de huellas.
     *
     * @return las estadisticas de verificacion
     */
    public VerificationStats getStats() {
        long validFingerprints = repository.countValidFingerprints();
        long notValidFingerprints = repository.countInvalidFingerprints();

        return new VerificationStats(validFingerprints, notValidFingerprints);
    }

}
