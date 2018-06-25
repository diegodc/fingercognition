package core.fingerprint.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * Representa una huella digital en el sistema.
 * La matriz de minucias es serializada y almacenada como un string codificado en Base64.
 */
@Document(collection = "fingerprints")
public class Fingerprint {

    @Id
    private final String id;

    @Indexed(name = "valid_index")
    private final boolean isValid;

    public Fingerprint(String id, boolean isValid) {
        this.id = id;
        this.isValid = isValid;

        checkId();
    }

    private void checkId() {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("Fingerprint id cannot be null or empty");
    }

    public String getId() {
        return id;
    }

    public boolean isValid() {
        return isValid;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Fingerprint that = (Fingerprint) other;
        return Objects.equals(id, that.id) && isValid == that.isValid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isValid);
    }

}
