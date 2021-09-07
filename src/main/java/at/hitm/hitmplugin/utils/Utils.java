/**
 * @Author: Jakob Hofer
 * @Date: 4.9.21
 */

package at.hitm.hitmplugin.utils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utils {
    public static UUID getUUID(String input) {
        if (!input.contains("-"))
            input = input.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
        try {
            return UUID.fromString(input);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> getElementsStartingWith(List<String> input, String prefix) {
        return input.stream().filter(e -> e.startsWith(prefix)).collect(Collectors.toList());
    }
}
