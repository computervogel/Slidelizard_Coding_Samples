package ssw.exam.util;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt, Predicate<Integer> condition, String errorMessage) {
        for (; ; ) {
            System.out.println(prompt);
            final String input = scanner.nextLine();
            try {
                final int number = Integer.parseInt(input);
                if (condition.test(number)) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // Fall through
            }
            System.out.println(errorMessage);
        }
    }

    public static String readString(String prompt, Predicate<String> condition, String errorMessage) {
        for (; ; ) {
            System.out.println(prompt);
            final String input = scanner.nextLine();
            if (condition.test(input)) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    public static <T> T select(String prompt, T[] items) {
        System.out.println(prompt);
        for (int i = 0; i < items.length; i++) {
            System.out.printf(" %d: %s%n", i, items[i]);
        }
        final int selection = readInt("Select an item: ", i -> 0 <= i && i < items.length, "Invalid selection");
        return items[selection];
    }

    public static <T> T select(String prompt, List<T> items, Function<T, String> stringConverter) {
        System.out.println(prompt);
        for (int i = 0; i < items.size(); i++) {
            System.out.printf(" %d: %s%n", i, stringConverter.apply(items.get(i)));
        }
        final int selection = readInt("Select an item:  ", i -> 0 <= i && i < items.size(), "Invalid selection");
        return items.get(selection);
    }

    public static boolean yesNo(String prompt) {
        System.out.println(prompt);
        System.out.println(" 0: Yes");
        System.out.println(" 1: No");
        final int selection = readInt("Select an item: ", i -> i == 0 || i == 1, "Invalid selection");
        return selection == 0;
    }

    public static <T> void list(List<T> items, Function<T, String> stringConverter) {
        for (T item : items) {
            System.out.printf(" - %s%n", stringConverter.apply(item));
        }
    }
}
