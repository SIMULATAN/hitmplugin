/**
 * @Author: Jakob Hofer
 * @Date: 4.9.21
 */

package at.hitm.hitmplugin.utils;

import java.util.UUID;

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
}
