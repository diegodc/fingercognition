package core.fingerprint.interactors;

import core.fingerprint.entities.Fingerprint;
import core.fingerprint.persistence.FingerprintRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository implements FingerprintRepository {

    private Map<String, Fingerprint> repository = new HashMap<>();

    @Override
    public Optional<Fingerprint> findById(String id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Fingerprint save(Fingerprint fingerprint) {
        repository.put(fingerprint.getId(), fingerprint);
        return fingerprint;
    }

    @Override
    public long countValidFingerprints() {
        return repository.values().stream().filter(Fingerprint::isValid).count();
    }

    @Override
    public long countInvalidFingerprints() {
        return repository.values().stream().filter(fp -> !fp.isValid()).count();
    }

}
