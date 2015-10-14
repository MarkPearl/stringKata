import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringCalculatorTest {

    private StringCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new StringCalculator();
    }

    @Test
    public void testZeroNumbers() throws Exception {
        int sum = calculator.sum("");
        assertEquals(0, sum);
    }

    @Test
    public void testOneNumber() throws Exception {
        int sum = calculator.sum("2");
        assertEquals(2, sum);

        sum = calculator.sum("4");
        assertEquals(4, sum);
    }

    @Test
    public void testTwoNumbers() throws Exception {
        int sum = calculator.sum("1,2");
        assertEquals(3, sum);
    }

    @Test
    public void testManyNumbers() throws Exception {
        int sum = calculator.sum("1,2,3,4,5");
        assertEquals(15, sum);
    }

    @Test
    public void testNewlineDelimiter() throws Exception {
        int sum = calculator.sum("1\n2\n3,4,5");
        assertEquals(15, sum);
    }

    @Test
    public void testDefinedDelimiter() throws Exception {
        int sum = calculator.sum("//;\n1;2");
        assertEquals(3, sum);
    }

    @Test
    public void testNegativesNotAllowed() throws Exception {
        try {
            calculator.sum("-1,2,-3");
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().startsWith(StringCalculator.NEGATIVES_NOT_ALLOWED));
            assertTrue(e.getMessage().endsWith("-1 -3"));
        }
    }

    @Test
    public void testIgnoreBigNumbers() throws Exception {
        int sum = calculator.sum("1,1000,1001,345678");
        assertEquals(1001, sum);

    }

    @Test
    public void testDelimiterOfAnyLength() throws Exception {
        int sum = calculator.sum("//***\n1***2");
        assertEquals(3, sum);
    }

    @Test
    public void testMultipleDefinedDelimiters() throws Exception {
        int sum = calculator.sum("//[*][%]\n1*2%3");
        assertEquals(6, sum);
    }

    @Test
    public void testMultipleDefinedDelimitersOfAnyLength() throws Exception {
        int sum = calculator.sum("//[...][;;][%]\n1%2;;3...4%5");
        assertEquals(15, sum);
    }
}
