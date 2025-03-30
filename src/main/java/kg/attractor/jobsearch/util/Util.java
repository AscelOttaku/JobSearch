package kg.attractor.jobsearch.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {

    public static String convertToSneakyCase(String str) {
        return str
                .replaceAll("([A-Z])(?=[A-Z])", "$1_")
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
    }
}
