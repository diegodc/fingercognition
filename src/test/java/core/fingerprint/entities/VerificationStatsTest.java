package core.fingerprint.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VerificationStatsTest {

    @Test
    void testConstruction() {
        assertStatsValues(40, 100, 0.4);
        assertStatsValues(10, 10, 1);
        assertStatsValues(0, 10, 0);
    }

    @Test
    void ratioIsRounded() {
        assertStatsValues(10, 3, 3.3);
        assertStatsValues(47, 300, 0.2);
        assertStatsValues(101, 67, 1.5);
    }

    @Test
    void ratioIsZeroIfInvalidFingerprintIsZero() {
        assertStatsValues(123, 0, 0);
        assertStatsValues(0, 0, 0);
    }

    private void assertStatsValues(int validFingerprints, int invalidFingerprints, double expectedRatio) {
        VerificationStats stats = new VerificationStats(validFingerprints, invalidFingerprints);

        assertEquals(validFingerprints, stats.getCount_valid_fingerPrint());
        assertEquals(invalidFingerprints, stats.getCount_not_valid_fingerPrint());
        assertEquals(expectedRatio, stats.getRatio());
    }

}