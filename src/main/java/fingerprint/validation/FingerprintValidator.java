package fingerprint.validation;

/**
 * Implementacion del algoritmo de verificacion de matrices de minucias.
 *
 * Dados los parametros:
 *     {sequenceLength} longitud de las secuencias buscadas
 *     {sequencesRequired} cantidad de secuencias requeridas
 *
 * Una matriz de minucias es valida, si contiene al menos la cantidad de secuencias requeridas,
 * cada una de la longitudos especificada, en las 4 direcciones posibles.
 */
public class FingerprintValidator {

    private final int sequencesRequired;
    private final int sequenceLength;

    private int sequencesFound;
    private String[] matrix;
    private int size;

    /**
     * Crea un nuevo validador que valida las matrices de minucias
     * buscando secuencias iguales de caracteres segun la longitud
     * de la secuencia y la cantidad dadas.
     *
     * @param sequenceLength la longitud de la secuencia
     * @param sequencesRequired la cantidad de secuencias
     */
    public FingerprintValidator(int sequenceLength, int sequencesRequired) {
        checkSequenceLength(sequenceLength);
        checkSequencesRequired(sequencesRequired);
        this.sequenceLength = sequenceLength;
        this.sequencesRequired = sequencesRequired;
    }

    private void checkSequenceLength(int sequenceLength) {
        if (sequenceLength < 2)
            throw new IllegalArgumentException("Sequence sequenceLength should be greater than 1");
    }

    private void checkSequencesRequired(int sequencesRequired) {
        if (sequencesRequired < 1)
            throw new IllegalArgumentException("Sequences required should be greater than 0");
    }

    public boolean isFingerPrint(String[] fingerprintMatrix) {
        initialize(fingerprintMatrix);

        /* Dados los requerimientos del algoritmo la matriz debe ser cuadrada */
        if (matrixIsNotSquare()) return false;

        /* Itera toda la matriz caracter por caracter */
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {

                char character = getCharAt(row, column);

                /* Si el caracter no es valido retorna inmediatamente */
                if (!isValid(character))
                    return false;

                /* Compara en las 4 direcciones posibles a partir de la posicion del caracter */
                compareRight(character, row, column);
                compareDown(character, row, column);
                compareDiagonalRight(character, row, column);
                compareDiagonalLeft(character, row, column);
            }
        }
        return enoughSequences();
    }

    private void initialize(String[] matrix) {
        sequencesFound = 0;
        size = matrix.length;
        this.matrix = matrix;
    }

    private boolean matrixIsNotSquare() {
        for (String line : matrix) {
            if (line.length() != size)
                return true;
        }
        return false;
    }

    private char getCharAt(int row, int column) {
        return matrix[row].charAt(column);
    }

    private boolean enoughSequences() {
        return sequencesFound >= sequencesRequired;
    }

    private void compareRight(char character, int row, int column) {
        if (sequenceFitsRightOrDown(column)) {
            for (int rightOffset = 1; rightOffset < sequenceLength; rightOffset++)
                if (character != getCharAt(row, column + rightOffset)) return;
            sequencesFound++;
        }
    }

    private void compareDown(char character, int row, int column) {
        if (sequenceFitsRightOrDown(row)) {
            for (int downOffset = 1; downOffset < sequenceLength; downOffset++)
                if (character != getCharAt(row + downOffset, column)) return;
            sequencesFound++;
        }
    }

    private void compareDiagonalRight(char character, int row, int column) {
        if ((sequenceFitsRightOrDown(row)) && (sequenceFitsRightOrDown(column)) ) {
            for (int diagonalRightOffset = 1; diagonalRightOffset < sequenceLength; diagonalRightOffset++)
                if (character != getCharAt(row + diagonalRightOffset, column + diagonalRightOffset)) return;
            sequencesFound++;
        }
    }

    private void compareDiagonalLeft(char character, int row, int column) {
        if (sequenceFitsRightOrDown(row) && sequenceFitsLeft(column)) {
            for (int diagonalLeftOffset = 1; diagonalLeftOffset < sequenceLength; diagonalLeftOffset++)
                if (character != getCharAt(row + diagonalLeftOffset, column - diagonalLeftOffset)) return;
            sequencesFound++;
        }
    }

    private boolean sequenceFitsRightOrDown(int position) {
        return position + sequenceLength - 1 < size
                && !enoughSequences();
    }

    private boolean sequenceFitsLeft(int column) {
        return column - sequenceLength + 1 >= 0
                && !enoughSequences();
    }

    private boolean isValid(char character) {
        return character == 'A'
                || character == 'T'
                || character == 'C'
                || character == 'G';
    }

}
