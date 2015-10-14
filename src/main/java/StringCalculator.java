import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringCalculator {

    public static final String DEFAULT_DELIMITER = ",";
    public static final String DELIMITER_STRING_PREFIX = "//";
    public static final String NEGATIVES_NOT_ALLOWED = "negatives not allowed";
    public static final int BIG_NUMBER = 1000;
    public static final String NEW_LINE = "\n";
    public static final String DELIMITER_PREFIX = "[";

    public int sum(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        numbers = replaceDelimiters(numbers);
        List<Integer> parsedNumbers = parseNumbers(numbers);

        checkForNegativeNumbers(parsedNumbers);

        return calculateSum(parsedNumbers);
    }

    void checkForNegativeNumbers(List<Integer> parsedNumbers) {
        StringBuffer negativeNumbers = new StringBuffer();
        for (Integer parsedNumber : parsedNumbers) {
            if (parsedNumber < 0) {
                negativeNumbers.append(" ").append(parsedNumber);
            }
        }
        if (negativeNumbers.length() > 0) {
            throw new RuntimeException(NEGATIVES_NOT_ALLOWED + negativeNumbers);
        }
    }

    String replaceDelimiters(String numbers) {
        if (numbers.startsWith(DELIMITER_STRING_PREFIX)) {
            int delimiterEndIndex = numbers.indexOf(NEW_LINE);
            String delimiterString = getDelimiterString(numbers, delimiterEndIndex);

            numbers = numbers.substring(delimiterEndIndex + 1);
            if (delimiterString.startsWith(DELIMITER_PREFIX)) {
                String[] delimiters = getDelimiters(delimiterString);
                for (String delimiter : delimiters) {
                    numbers = numbers.replaceAll(Pattern.quote(delimiter), DEFAULT_DELIMITER);
                }
            } else {
                numbers = numbers.replaceAll(Pattern.quote(delimiterString), DEFAULT_DELIMITER);
            }
        }

        numbers = numbers.replaceAll(Pattern.quote(NEW_LINE), DEFAULT_DELIMITER);
        return numbers;
    }

    private String[] getDelimiters(String delimiterString) {
        delimiterString = delimiterString.substring(0, delimiterString.length() - 1).substring(1);
        return delimiterString.split(Pattern.quote("]["));
    }

    private String getDelimiterString(String numbers, int delimiterEndIndex) {
        return numbers.substring(2, delimiterEndIndex);
    }

    List<Integer> parseNumbers(String numbers) {
        List<Integer> parsedNumbers = new ArrayList<Integer>();
        for (String number : numbers.split(DEFAULT_DELIMITER)) {
            parsedNumbers.add(Integer.parseInt(number));
        }
        return parsedNumbers;
    }

    int calculateSum(List<Integer> parsedNumbers) {
        int sum = 0;
        for (Integer parsedNumber : parsedNumbers) {
            if (parsedNumber <= BIG_NUMBER) {
                sum += parsedNumber;
            }
        }
        return sum;
    }
}
