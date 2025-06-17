package kg.attractor.jobsearch.util;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.model.*;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.text.CaseUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static boolean isMessageLink(String message) {
        return message != null && !message.isBlank() && (message.startsWith("http://")
                || message.startsWith("https://"));
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

    public static Optional<String> findPrefix(String arg) {
        if (arg.startsWith("*"))
            return Optional.of(arg.substring(0, 1));

        return Optional.empty();
    }

    public static Optional<String> findSuffix(String arg) {
        if (arg.endsWith("*"))
            return Optional.of(arg.substring(arg.length() - 1));

        return Optional.empty();
    }

    public static boolean isDouble(Object arg) {
        switch (arg) {
            case null -> {
                return false;
            }
            case Double ignored -> {
                return true;
            }
            case String str -> {
                try {
                    Double.parseDouble(str);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + arg);
        }
    }

    public static boolean isString(Object arg) {
        return arg instanceof String;
    }

    public String replaceFirstOR_(String arg) {
        if (arg == null || arg.isBlank()) {
            return arg;
        }
        return arg.replaceFirst("OR_", "");
    }

    public <T> List<String> getAllFieldsNamesOfClass(T arg) {
        Class<?> currentClass = arg.getClass();

        if (currentClass == Object.class)
            return List.of();

        var objectsFields = Arrays.stream(currentClass.getDeclaredFields())
                .filter(field -> !isPrimitiveOrWrapper(field.getType()) && !getSKillClasses().contains(field.getType()) && !isDateType(field.getType()))
                .flatMap(field -> {
                    if (field.getType() == List.class) {
                        var fields = getTypeOfGenericList(field)
                                .map(Class::getDeclaredFields)
                                .orElseThrow(() -> new IllegalArgumentException("Field type is not a List or is empty"));

                        return Arrays.stream(fields)
                                .map(name -> field.getName() + "." + name.getName());
                    }
                    var fieldValue = getFieldValue(field, arg);
                    return Arrays.stream(fieldValue.getClass().getDeclaredFields())
                            .map(name -> field.getName() + "." + name);
                })
                .toList();

        var allPrimitiveFieldsNames = Arrays.stream(currentClass.getDeclaredFields())
                .filter(field -> isPrimitiveOrWrapper(field.getType()) || isDateType(field.getType()) || field.getType() == Category.class)
                .map(Field::getName)
                .filter(name -> Character.isLowerCase(name.charAt(0)))
                .filter(name -> !name.equals("serialVersionUID") && !name.equals("id") && !name.equals("respondedApplications"))
                .collect(Collectors.toList());

        allPrimitiveFieldsNames.addAll(objectsFields);
        return allPrimitiveFieldsNames;
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> T getFieldValue(Field field, T arg) {
        Assert.notNull(field, "field must not be null");
        Assert.notNull(arg, "arg must not be null");

        Class<?> fieldType = field.getType();
        Constructor<?> constructor = fieldType.getDeclaredConstructor();
        constructor.setAccessible(true);
        return (T) constructor.newInstance();
    }

    public boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz == Boolean.class ||
                clazz == Byte.class ||
                clazz == Character.class ||
                clazz == Short.class ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Float.class ||
                clazz == Double.class ||
                clazz == String.class;
    }

    public boolean isDateType(Class<?> clazz) {
        return clazz == LocalDateTime.class || clazz == Date.class || clazz == LocalDate.class;
    }

    @SneakyThrows
    public Optional<Class<?>> getTypeOfGenericList(Field field) {
        Type type = field.getGenericType();
        if (type instanceof ParameterizedType parameterizedType) {
            Type elementType = parameterizedType.getActualTypeArguments()[0];
            if (elementType instanceof Class<?> elementClass) {
                return Optional.of(elementClass);
            }
        }
        return Optional.empty();
    }

    public List<Class<?>> getSKillClasses() {
        return Stream.of(Category.class, Role.class, User.class, RespondedApplication.class)
                .toList();
    }
}

