package fingerprint.validation;


public class FingerprintValidator {

    private final int sequencesRequired;
    private final int length;

    private int sequencesFound;
    private String[] matrix;
    private int size;

    public FingerprintValidator(int sequenceLength, int sequencesRequired) {
        checkSequenceLength(sequenceLength);
        checkSequencesRequired(sequencesRequired);
        length = sequenceLength;
        this.sequencesRequired = sequencesRequired;
    }

    private void checkSequenceLength(int sequenceLength) {
        if (sequenceLength < 2)
            throw new IllegalArgumentException("Sequence length should be greater than 1");
    }

    private void checkSequencesRequired(int sequencesRequired) {
        if (sequencesRequired < 1)
            throw new IllegalArgumentException("Sequences required should be greater than 0");
    }

    public boolean isFingerPrint(String[] fingerprintMatrix) {
        initialize(fingerprintMatrix);

        if (matrixIsNotSquare()) return false;

        //Iterar matrix
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {

                // Si el caracter no es valido retornar inmediatamente
                char character = getCharAt(row, column);
                if (!isValid(character))
                    return false;

                // Si ya se encontraron suficientes sequencias no compara mas
                if (!enoughSequences()) {
                    compareRight(character, row, column);
                    compareDown(character, row, column);
                    compareDiagonalRight(character, row, column);
                    compareDiagonalLeft(character, row, column);
                }
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
        if (column + length - 1 < size) {
            for (int i = 1; i < length; i++) {
                if (character != getCharAt(row, column + i)) {
                    return;
                }
            }
            sequencesFound++;
        }
    }

    private void compareDown(char character, int row, int column) {
        if (row + length - 1 < size) {
            for (int i = 1; i < length; i++) {
                if (character != getCharAt(row + i, column)) {
                    return;
                }
            }
            sequencesFound++;
        }
    }

    private void compareDiagonalRight(char character, int row, int column) {
        if ((row + length - 1 < size) && (column + length - 1 < size) ) {
            for (int i = 1; i < length; i++) {
                if (character != getCharAt(row + i, column + i)) {
                    return;
                }
            }
            sequencesFound++;
        }
    }

    private void compareDiagonalLeft(char character, int row, int column) {
        if (row + length - 1 < size && column - length + 1 >= 0) {
            for (int i = 1; i < length; i++) {
                if (character != getCharAt(row + i, column - i)) {
                    return;
                }
            }
            sequencesFound++;
        }
    }

    private boolean isValid(char character) {
        return character == 'A'
                || character == 'T'
                || character == 'C'
                || character == 'G';
    }

}
