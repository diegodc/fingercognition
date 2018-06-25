package fingerprint.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FingerprintTest {

    private Fingerprint validFingerprint;
    private Fingerprint invalidFingerprint;

    @BeforeEach
    void setUp() {
        validFingerprint = new Fingerprint("valid-id", true);
        invalidFingerprint = new Fingerprint("invalid-id", false);
    }

    @Test
    void testConstruction() {
        assertEquals("valid-id", validFingerprint.getId());
        assertTrue(validFingerprint.isValid());

        assertEquals("invalid-id", invalidFingerprint.getId());
        assertFalse(invalidFingerprint.isValid());
    }

    @Test
    void fingerprintIdCanNotBeNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Fingerprint(null, true));
        assertThrows(IllegalArgumentException.class, () -> new Fingerprint("", true));
    }

    @Test
    void equalsAndHashCodeContract() {
        Fingerprint anotherValid = new Fingerprint("valid-id", true);
        Fingerprint anotherInvalid = new Fingerprint("invalid-id", false);
        Fingerprint different = new Fingerprint("valid-id", false);
        Fingerprint alsoDifferent = new Fingerprint("diff", true);

        assertEquals(validFingerprint, anotherValid);
        assertEquals(invalidFingerprint, anotherInvalid);

        assertNotEquals(validFingerprint, invalidFingerprint);
        assertNotEquals(validFingerprint, different);
        assertNotEquals(validFingerprint, alsoDifferent);

        assertEquals(validFingerprint.hashCode(), anotherValid.hashCode());
        assertEquals(invalidFingerprint.hashCode(), anotherInvalid.hashCode());

        assertNotEquals(validFingerprint.hashCode(), invalidFingerprint.hashCode());
        assertNotEquals(validFingerprint.hashCode(), different.hashCode());
        assertNotEquals(validFingerprint.hashCode(), alsoDifferent.hashCode());
    }

}