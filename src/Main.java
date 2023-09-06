import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class Main {
    private static Pattern pattern = Pattern.compile("(\\d+|[IVXLCDM]+)\\s*([\\+\\-\\*\\/])\\s*(\\d+|[IVXLCDM]+)");

    public static void main(String[] args) throws Exception {
        String input = System.console().readLine();
        System.out.println(calc(input));
    }

    private static String calc(String expression)
    {
        Matcher match = pattern.matcher(expression);
        if(!match.matches())
            throw new IllegalArgumentException();

        String num1Str = match.group(1);
        String num2Str = match.group(3);
        String operationStr = match.group(2);

        if (IsRoman(num1Str) != IsRoman(num2Str))
            throw new IllegalArgumentException();

        boolean isRoman = IsRoman(num1Str) && IsRoman(num2Str);

        if(isRoman)
        {
            num1Str = Integer.toString(RomanToArabic(num1Str));
            num2Str = Integer.toString(RomanToArabic(num2Str));
        }

        int num1 = Integer.parseInt(num1Str);
        int num2 = Integer.parseInt(num2Str);

        if(num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10)
            throw new IllegalArgumentException();

        int result = 0;
        switch (operationStr)
        {
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            case "*": result = num1 * num2; break;
            case "/": result = num1 / num2; break;
        }

        if (isRoman && result < 1)
            throw new IllegalArgumentException();

        return isRoman ? ArabicToRoman(result) : Integer.toString(result);
    }

    private static Boolean IsRoman(String number)
    {
        return Pattern.matches("[IVXLCDM]+", number);
    }

    private static String ArabicToRoman(int number)
    {
        String[] romanNumerals = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

        int[] arabicNumerals = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

        StringBuilder sb = new StringBuilder();

        int i = arabicNumerals.length - 1;
        while(number > 0)
        {
            if(number >= arabicNumerals[i])
            {
                sb.append(romanNumerals[i]);
                number -= arabicNumerals[i];
            }
            else
            {
                i--;
            }
        }

        return sb.toString();
    }

    private static int RomanToArabic(String number)
    {
        Map<Character, Integer> romanToArabic = new HashMap<>();
        romanToArabic.put('I', 1);
        romanToArabic.put('V', 5);
        romanToArabic.put('X', 10);
        romanToArabic.put('L', 50);
        romanToArabic.put('C', 100);
        romanToArabic.put('D', 500);
        romanToArabic.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = number.length() - 1; i >= 0; i--) {
            int currentValue = romanToArabic.get(number.charAt(i));
            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            prevValue = currentValue;
        }

        return result;
    }
}
