import org.example.TriangleStateException;
import org.example.TriangleHeight;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TriangleHeightTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void revertStreams() {
        System.setOut(standardOut);
    }

    @Test
    public void testReadTriangleSideFromString() {
        Scanner inputScanner = new Scanner("string");
        Assertions.assertThrows(InputMismatchException.class, () -> TriangleHeight.readTriangleSide("", inputScanner));
    }

    @Test
    public void testReadTriangleSideFromNumber() {
        Scanner inputScanner = new Scanner("123,33");
        Assertions.assertEquals(123.33, TriangleHeight.readTriangleSide("", inputScanner), 0.001);
    }

    @Test
    public void testReadTriangleSidePrompt() {
        Scanner inputScanner = new Scanner("0");
        TriangleHeight.readTriangleSide("Test prompt", inputScanner);
        Assertions.assertEquals("Test prompt", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testValidateTriangleForLine() {
        TriangleStateException triangleStateException = Assertions.assertThrows(TriangleStateException.class,
                () -> TriangleHeight.validateTriangleSides(1, 2, 3));
        Assertions.assertEquals("Сумма любых двух сторон треугольника должна быть больше третьей",
                triangleStateException.getMessage());
    }

    @Test
    public void testValidateTriangleForZero() {
        TriangleStateException triangleStateException = Assertions.assertThrows(TriangleStateException.class,
                () -> TriangleHeight.validateTriangleSides(0, 2, 3));
        Assertions.assertEquals("Длина стороны должна быть больше нуля",
                triangleStateException.getMessage());
    }

    @Test
    public void testValidateTriangleForNegative() {
        TriangleStateException triangleStateException = Assertions.assertThrows(TriangleStateException.class,
                () -> TriangleHeight.validateTriangleSides(-1, 2, 3));
        Assertions.assertEquals("Длина стороны должна быть больше нуля",
                triangleStateException.getMessage());
    }

    @Test
    public void testCalculateHeightFormulaNumerator() {
        Assertions.assertEquals(3.4641,
                TriangleHeight.calculateHeightFormulaNumerator(2, 2, 2), 0.0001);
    }

    @Test
    public void testCalculateHeightToSide() {
        Assertions.assertEquals(12.990,
                TriangleHeight.calculateHeightToSide(194.8557, 15, 15, 15,
                        TriangleHeight.TRIANGLE_SIDE.A),
                0.001);
    }

    @Test
    public void testPrintHeight() {
        TriangleHeight.printHeight(TriangleHeight.TRIANGLE_SIDE.A, 99);
        Assertions.assertEquals("H(A): 99,00", outputStreamCaptor.toString().trim());
    }
}
