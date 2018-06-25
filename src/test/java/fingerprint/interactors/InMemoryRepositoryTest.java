package fingerprint.interactors;

import fingerprint.entities.Fingerprint;
import fingerprint.persistence.FingerprintRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {

    private FingerprintRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRepository();
    }

    @Test
    void initialState() {
        assertEquals(0, repository.countValidFingerprints());
        assertEquals(0, repository.countInvalidFingerprints());

        assertFalse(repository.findById("anyone").isPresent());
    }

    @Test
    void savesFingerprints() {
        Fingerprint valid = new Fingerprint("valid-fp", true);
        Fingerprint invalid = new Fingerprint("invalid-fp", false);

        repository.save(valid);
        repository.save(invalid);

        Optional<Fingerprint> op1 = repository.findById("valid-fp");
        assertTrue(op1.isPresent());

        Optional<Fingerprint> op2 = repository.findById("invalid-fp");
        assertTrue(op2.isPresent());

        assertSame(valid, op1.get());
        assertSame(invalid, op2.get());
    }

    @Test
    void fingerprintCount() {
        loadRepository();

        assertEquals(23, repository.countValidFingerprints());
        assertEquals(11, repository.countInvalidFingerprints());
    }

    @Test
    void findsAll() {
        loadRepository();

        for (int id = 0; id < 23; id++)
            assertTrue(repository.findById("test-" + id).isPresent());

        for (int id = 0; id < 11; id++)
            assertTrue(repository.findById("test-nv-" + id).isPresent());
    }

    private void loadRepository() {
        for (int id = 0; id < 23; id++)
            repository.save(new Fingerprint("test-" + id, true));

        for (int id = 0; id < 11; id++)
            repository.save(new Fingerprint("test-nv-" + id, false));
    }

}