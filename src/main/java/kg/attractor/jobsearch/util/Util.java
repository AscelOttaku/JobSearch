package kg.attractor.jobsearch.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.text.CaseUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class Util {

    public static String convertToSneakyCase(String str) {
        return str
                .replaceAll("([A-Z])(?=[A-Z])", "$1_")
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
    }

    public static boolean notNullOrBlank(String value) {
        return value != null && !value.isBlank();
    }

    public static String convertToCamelCase(String str) {
        return CaseUtils.toCamelCase(str, false, '_');
    }

    public static String dateTimeFormat(LocalDateTime dateTime) {
        if (dateTime == null)
            return null;

        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
    }

    public static String encodePassword(PasswordEncoder passwordEncoder, String password) {
        Pattern bcryptPattern = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

        if (bcryptPattern.matcher(password).matches())
            return password;

        return passwordEncoder.encode(password);
    }

    public static Optional<Long> convertToLong(String arg) {
        try {
            return Optional.of(Long.parseLong(arg));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static String getSiteUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getRequestURI(), "");
    }

    public static String generateUniqueValue() {
        return UUID.randomUUID().toString();
    }

    public static String getUserArrivedUri(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return referer;
    }

    public static <T> List<T> makeSureListIsNotNull(List<T> list) {
        return list == null ? new ArrayList<>() : list;
    }

    public static void makeSureTextLengthMoreThenTwoElements(String skillName) {
        if (skillName == null || skillName.isBlank())
            throw new IllegalArgumentException("text is null or blank");

        if (skillName.length() <= 2)
            throw new IllegalArgumentException("text name cannot be equals or less then 2");
    }

    public static boolean isSkillCorrect(String skillName) {
        return skillName != null && !skillName.isBlank() && skillName.length() > 2;
    }

    public static Optional<Integer> findNumber(String arg) {
        Pattern pattern = Pattern.compile("\\[(\\d+)]");
        Matcher matcher = pattern.matcher(arg);

        return matcher.find() ? Optional.of(Integer.parseInt(matcher.group(1))) :
                Optional.empty();
    }
}
