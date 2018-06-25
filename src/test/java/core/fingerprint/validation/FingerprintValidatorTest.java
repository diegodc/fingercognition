package core.fingerprint.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FingerprintValidatorTest {

    private FingerprintValidator validator = new FingerprintValidator(2, 2);

    @BeforeEach
    void setUp() {
    }

    @Test
    void twoCharLengthSingleSequence() {
        validator = new FingerprintValidator(2, 1);

        assertFingerprint("AA CT");
        assertFingerprint("CT AA");
        assertFingerprint("AC AG");
        assertFingerprint("GA TA");
        assertFingerprint("AC TA");
        assertFingerprint("AC AT");

        assertNotFingerprint("AC TG");
    }

    @Test
    void twoCharLengthDoubleSequence() {
        assertFingerprint("AA CC");
        assertFingerprint("AC AC");
        assertFingerprint("AC CA");

        assertNotFingerprint("AA CG");
        assertNotFingerprint("CG AA");
        assertNotFingerprint("AG AT");
        assertNotFingerprint("CT AT");
    }

    @Test
    void horizontalSequences() {
        validator = new FingerprintValidator(4, 1);

        assertFingerprint("AAAATG TGACTG ACTGAC TGACTG ACTGAC TGACTG");
        assertFingerprint("ACTGAC TGTTTT ACTGAC TGACTG ACTGAC TGACTG");
        assertFingerprint("ACTGAC TGACTG ACCCCA TGACTG ACTGAC TGACTG");
        assertFingerprint("ACTGAC TGACTG ACTGAC TGTTTT ACTGAC TGACTG");
        assertFingerprint("ACTGAC TGACTG ACTGAC TGACTG ACAAAA TGACTG");
        assertFingerprint("ACTGAC TGACTG ACTGAC TGACTG ACTGAC TCGGGG");

        validator = new FingerprintValidator(4, 2);

        assertNotFingerprint("AAAATG TGACTG ACTGAC TGACTG ACTGAC TGACTG");
        assertNotFingerprint("ACTGAC TGTTTT ACTGAC TGACTG ACTGAC TGACTG");
        assertNotFingerprint("ACTGAC TGACTG ACCCCA TGACTG ACTGAC TGACTG");
        assertNotFingerprint("ACTGAC TGACTG ACTGAC TGTTTT ACTGAC TGACTG");
        assertNotFingerprint("ACTGAC TGACTG ACTGAC TGACTG ACAAAA TGACTG");
        assertNotFingerprint("ACTGAC TGACTG ACTGAC TGACTG ACTGAC TCGGGG");
    }

    @Test
    void verticalSequences() {
        validator = new FingerprintValidator(4, 1);

        assertFingerprint("ACTGAC AGACTG ACTGAC AGACTG TCTGAC TGACTG");
        assertFingerprint("ACTGAC TCACTG ACTGAC TCACTG AGTGAC TGACTG");
        assertFingerprint("ACTGAC TGACTG ACAGAC TGACTG ACAGAC TGGCTG");
        assertFingerprint("ACTGAC TGACCG ACTGTC TGACTG ACTGTC TGACTG");
        assertFingerprint("ACTGAC TGACTG ACTGAC TGACAG ACTGAC TGACAG");
        assertFingerprint("ACTGAC TGACTA ACTGAG TGACTG ACTGAG TGACTG");

        validator = new FingerprintValidator(4, 2);

        assertNotFingerprint("ACTGAC AGACTG ACTGAC AGACTG TCTGAC TGACTG");
        assertNotFingerprint("ACTGAC TCACTG ACTGAC TCACTG AGTGAC TGACTG");
        assertNotFingerprint("ACTGAC TGACTG ACAGAC TGACTG ACAGAC TGGCTG");
        assertNotFingerprint("ACTGAC TGACCG ACTGTC TGACTG ACTGTC TGACTG");
        assertNotFingerprint("ACTGAC TGACTG ACTGAC TGACAG ACTGAC TGACAG");
        assertNotFingerprint("ACTGAC TGACTA ACTGAG TGACTG ACTGAG TGACTG");
    }

    @Test
    void diagonalSequences() {
        validator = new FingerprintValidator(4, 1);

        assertFingerprint("ACTGAC TAACTG ACAGAC TGAATG ACTGGC TGACTG");
        assertFingerprint("ACTGAC TGACTG ACGGAC TGAGTG ACTGGC TGACTT");
        assertFingerprint("ACTGAC TGATTG ACTGTC TGACTT ACTGAC TGACTG");
        assertFingerprint("ACTGAC TGACTG ACTGAC TAACTG ACAGAC TGAATG");
        assertFingerprint("ACTGAC TAGCTG ACGGAC TGAAGG ACTGAG TGACGA");
        assertFingerprint("ACTGAC TAACTG ACGGAC TGAGTG ACTGGC TGACTG");

        assertFingerprint("ACTGAC TGGCTG AGTGAC GGACTG ACTGAC TGACTG");
        assertFingerprint("ACTGAC TGAATG ACAGAC TAACTG CCTGAC TGACTG");
        assertFingerprint("ACTGAC TGACCG ACTCAC TGCCTG AATGAC TGACTG");
        assertFingerprint("ACTGAC TGACTG ACTGAC TGATTG ACTGTC TGACTT");
        assertFingerprint("ACTGAC TGACAG ACTTAC TGTCTG ATTGAC TGACTG");
        assertFingerprint("ACTGAC TAACTG ACGGAC TGAGTG ACTGGC TGACTG");

        validator = new FingerprintValidator(4, 2);

        assertNotFingerprint("ACTGAC TAACTG ACAGAC TGAATG ACTGGC TGACTG");
        assertNotFingerprint("ACTGAC TGACTG ACGGAC TGAGTG ACTGGC TGACTT");
        assertNotFingerprint("ACTGAC TGATTG ACTGTC TGACTT ACTGAC TGACTG");
        assertNotFingerprint("ACTGAC TGACTG ACTGAC TAACTG ACAGAC TGAATG");
        assertNotFingerprint("ACTGAC TAGCTG ACGGAC TGAAGG ACTGAG TGACGA");
        assertNotFingerprint("ACTGAC TAACTG ACGGAC TGAGTG ACTGGC TGACTG");

        assertNotFingerprint("ACTGAC TGGCTG AGTGAC GGACTG ACTGAC TGACTG");
        assertNotFingerprint("ACTGAC TGAATG ACAGAC TAACTG CCTGAC TGACTG");
        assertNotFingerprint("ACTGAC TGACCG ACTCAC TGCCTG AATGAC TGACTG");
        assertNotFingerprint("ACTGAC TGACTG ACTGAC TGATTG ACTGTC TGACTT");
        assertNotFingerprint("ACTGAC TGACAG ACTTAC TGTCTG ATTGAC TGACTG");
        assertNotFingerprint("ACTGAC TAACTG ACGGAC TGAGTG ACTGGC TGACTG");
    }

    @Test
    void differentSequences() {
        validator = new FingerprintValidator(4, 2);

        assertFingerprint("ACTGAC TGACTG ACTGCC TCACTG ACCGAC TCACTG");
        assertFingerprint("ACTGAC TGACTA CCCCAC TGAATG ACAGAC TGACTG");
        assertFingerprint("ACTGAC TGACTG ACTAAT TGACTG AATTAC AGTCTG");
        assertFingerprint("CCCCAC TGACTG GCTGAC TGACTG ACGGAC TGAGTG");
        assertFingerprint("ACTGAC TGACTG ATTTTC TGTCGG ATTGAC CGACTG");
        assertFingerprint("ACATAC TGTCTG ATAGAG TGACTG ACTGAG TGACTA");
        assertFingerprint("ACTGAC TGACTG AAAAAC TGACTG ACTGAC TGACTG");

        validator = new FingerprintValidator(4, 3);

        assertNotFingerprint("ACTGAC TGACTG ACTGCC TCACTG ACCGAC TCACTG");
        assertNotFingerprint("ACTGAC TGACTA CCCCAC TGAATG ACAGAC TGACTG");
        assertNotFingerprint("ACTGAC TGACTG ACTAAT TGACTG AATTAC AGTCTG");
        assertNotFingerprint("CCCCAC TGACTG GCTGAC TGACTG ACGGAC TGAGTG");
        assertNotFingerprint("ACTGAC TGACTG ATTTTC TGTCGG ATTGAC CGACTG");
        assertNotFingerprint("ACATAC TGTCTG ATAGAG TGACTG ACTGAG TGACTA");
        assertNotFingerprint("ACTGAC TGACTG AAAAAC TGACTG ACTGAC TGACTG");
    }

    @Test
    void onlySquareMatrixAreValid() {
        assertFingerprint("AA AA");
        assertFingerprint("AAA AAA AAA");
        assertFingerprint("AAAA AAAA AAAA AAAA");
        assertFingerprint("ACACAC ACACAC ACACAC ACACAC ACACAC ACACAC");
        assertFingerprint("ACTGACTG ACTGACTG ACTGACTG ACTGACTG ACTGACTG ACTGACTG ACTGACTG ACTGACTG");

        assertNotFingerprint("AT C");
        assertNotFingerprint("A C");
        assertNotFingerprint("A CG");
        assertNotFingerprint("AAA CG");
        assertNotFingerprint("AAA CGCG AAA");
        assertNotFingerprint("AAAA CGCG AAA");
        assertNotFingerprint("AAAA CGCG AAAAA");
    }

    @Test
    void validatesCharactersInMatrix() {
        assertFingerprint("AA AG");
        assertFingerprint("ATC GTT TAG");
        assertNotFingerprint("XT CG");
        assertNotFingerprint("ATC CGA ATX");
        assertNotFingerprint("AAAA AAAA AAAA AAAX");
    }

    @Test
    void sequenceLengthShouldBeGreaterThanOne() {
        assertThrows(IllegalArgumentException.class, () -> new FingerprintValidator(1, 2));
    }

    @Test
    void sequencesRequiredShouldBeGreaterThanZero() {
        assertThrows(IllegalArgumentException.class, () -> new FingerprintValidator(2, 0));
    }

    private void assertFingerprint(String matrix) {
        assertTrue(validator.isFingerPrint(matrix.split(" ")));
    }

    private void assertNotFingerprint(String matrix) {
        assertFalse(validator.isFingerPrint(matrix.split(" ")));
    }

}