package fingerprint.interactors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

class Encoder {

    /**
     * Serializa la matriz de minucias en un string codificado.
     *
     * @param matrix la matriz de minucias
     * @return un string codificado en Base64
     */
    static String serializeFingerprintMatrix(String[] matrix) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            new ObjectOutputStream(out).writeObject(matrix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

}
