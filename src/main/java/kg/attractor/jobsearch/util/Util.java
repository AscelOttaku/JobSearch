package kg.attractor.jobsearch.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.text.CaseUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

@UtilityClass
public class Util {

    public static String convertToSneakyCase(String str) {
        return str
                .replaceAll("([A-Z])(?=[A-Z])", "$1_")
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
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
}
