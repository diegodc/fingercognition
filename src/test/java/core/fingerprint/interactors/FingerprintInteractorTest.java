package core.fingerprint.interactors;

import core.fingerprint.entities.Fingerprint;
import core.fingerprint.entities.VerificationStats;
import core.fingerprint.persistence.FingerprintRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FingerprintInteractorTest {

    private static final String VALID_ID
            = "rO0ABXVyABNbTGphdmEubGFuZy5TdHJpbmc7rdJW5+kde0cCAAB4cAAAAAR0AARBQUFBdAAEQUNUR3QABEFUR0NxAH4AAw==";
    private static final String INVALID_ID
            = "rO0ABXVyABNbTGphdmEubGFuZy5TdHJpbmc7rdJW5+kde0cCAAB4cAAAAAR0AARBQ1RHdAAEVEdBQ3EAfgACcQB+AAM=";

    private FingerprintRepository repository;
    private FingerprintInteractor interactor;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRepository();
        interactor = new FingerprintInteractor(repository);
    }

    @Test
    void verifiesFingerprint() {
        String[] nullMatrix = null;
        assertFalse(interactor.verifyFingerprint(nullMatrix));

        String[] emptyMatrix = {};
        assertFalse(interactor.verifyFingerprint(emptyMatrix));

        String[] validMatrix = {"AAAACT", "ACTGTG", "ATGCGC", "ACTGTG", "ATGGTG", "TGACCT"};
        assertTrue(interactor.verifyFingerprint(validMatrix));

        String[] invalidMatrix = {"ACTGAC", "TGACTG", "ACTGAC", "TGACTG", "ACTGAC", "TGACTG"};
        assertFalse(interactor.verifyFingerprint(invalidMatrix));
    }

    @Test
    void savesFingerprints() {
        String[] validMatrix = {"AAAA", "ACTG", "ATGC", "ACTG"};
        String[] invalidMatrix = {"ACTG", "TGAC", "ACTG", "TGAC"};

        assertTrue(interactor.verifyFingerprint(validMatrix));
        assertFalse(interactor.verifyFingerprint(invalidMatrix));

        assertTrue(repository.findById(VALID_ID).isPresent());
        assertTrue(repository.findById(INVALID_ID).isPresent());

        Fingerprint valid = repository.findById(VALID_ID).get();
        Fingerprint invalid = repository.findById(INVALID_ID).get();

        assertEquals(VALID_ID, valid.getId());
        assertEquals(INVALID_ID, invalid.getId());

        assertTrue(valid.isValid());
        assertFalse(invalid.isValid());
    }

    @Test
    void looksForFingerprintInTheRepository() {
        FingerprintRepository spy = spy(repository);

        interactor = new FingerprintInteractor(spy);

        String[] validMatrix = {"AAAA", "ACTG", "ATGC", "ACTG"};
        interactor.verifyFingerprint(validMatrix);

        verify(spy).findById(VALID_ID);

        Optional<Fingerprint> op = spy.findById(VALID_ID);
        assertTrue(op.isPresent());
        verify(spy).save(op.get());
    }

    @Test
    void savedFingerprintsAreNotSavedAgain() {
        repository.save(new Fingerprint(VALID_ID, true));

        FingerprintRepository spy = spy(repository);

        interactor = new FingerprintInteractor(spy);

        String[] validMatrix = {"AAAA", "ACTG", "ATGC", "ACTG"};
        interactor.verifyFingerprint(validMatrix);

        verify(spy).findById(VALID_ID);
        verifyNoMoreInteractions(spy);
    }

    @Test
    void interactorReturnsVerificationStats() {
        loadRepository();

        VerificationStats stats = interactor.getStats();

        assertEquals(23, stats.getCount_valid_fingerPrint());
        assertEquals(11, stats.getCount_not_valid_fingerPrint());
    }

    private void loadRepository() {
        for (int id = 0; id < 23; id++)
            repository.save(new Fingerprint("test-" + id, true));

        for (int id = 0; id < 11; id++)
            repository.save(new Fingerprint("test-nv-" + id, false));
    }

}