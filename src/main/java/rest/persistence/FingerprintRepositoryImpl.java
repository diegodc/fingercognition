package rest.persistence;

import core.fingerprint.entities.Fingerprint;
import core.fingerprint.persistence.FingerprintRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * FingerprintRepositoy implementation using Spring Data MongoDb.
 */
public interface FingerprintRepositoryImpl extends FingerprintRepository, MongoRepository<Fingerprint, String> {

    @Override
    default long countValidFingerprints() {
        return this.countByIsValid(true);
    }

    @Override
    default long countInvalidFingerprints() {
        return this.countByIsValid(false);
    }

    long countByIsValid(boolean isValid);

}
