package core.fingerprint.persistence;

import core.fingerprint.entities.Fingerprint;

import java.util.Optional;

/**
 * Repositorio de Huellas
 */
public interface FingerprintRepository {

    /**
     * Busca la huella en el repositorio segun el id dado.
     *
     * @param id el id de la huella
     * @return un Optional que contiene la huella en caso de que exista
     */
    Optional<Fingerprint> findById(String id);

    /**
     * Guarda la huella en el repositorio.
     *
     * @param fingerprint la huella a guardas
     * @return la huella
     */
    Fingerprint save(Fingerprint fingerprint);

    /**
     * Cuenta la cantidad de huellas validas registradas.
     *
     * @return el numero de huellas validas
     */
    long countValidFingerprints();

    /**
     * Cuenta la cantidad de huellas no validas registradas.
     *
     * @return el numero de huellas invalidas
     */
    long countInvalidFingerprints();

}
