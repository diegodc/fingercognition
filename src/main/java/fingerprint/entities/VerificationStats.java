package fingerprint.entities;

/**
 * Estadisticas de la verificacion de huellas. El ratio se calcula como la proporcion
 * entre la cantidad de huellas validas y las no validas.
 */
public class VerificationStats {

    private final long validFingerprints;
    private final long invalidFingerprints;
    private final double ratio;

    public VerificationStats(long validFingerprints, long notValidFingerprints) {
        this.validFingerprints = validFingerprints;
        this.invalidFingerprints = notValidFingerprints;
        ratio = calculateRatio();
    }

    /**
     * Calcula el ratio, en caso que la cantidad de huellas no validas sea cero,
     * el ratio se define como cero.
     *
     * @return el ratio entre las huellas validas y no validas
     */
    private double calculateRatio() {
        if (invalidFingerprints == 0)
            return 0;

        return validFingerprints / (double) invalidFingerprints;
    }

    public long getCount_valid_fingerPrint() {
        return validFingerprints;
    }

    public long getCount_not_valid_fingerPrint() {
        return invalidFingerprints;
    }

    /**
     * El ratio es redondeado a un decimal.
     *
     * @return ratio entre verificaciones validas y no validas
     */
    public double getRatio() {
        return Double.parseDouble(String.format("%.1f", ratio));
    }

}
