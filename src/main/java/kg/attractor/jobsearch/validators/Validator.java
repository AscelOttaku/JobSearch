package kg.attractor.jobsearch.validators;

import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator {

    public static void isValidId(Long arg) {

        if (arg != null && arg > 0)
            return;

        throw new CustomIllegalArgException(
                "Illegal arg exception",
                CustomBindingResult.builder()
                        .className(Vacancy.class.getSimpleName())
                        .fieldName("id")
                        .rejectedValue(arg)
                        .build()
        );
    }

    public static boolean isValidUserAccountType(User user) {
        return user.getAccountType().equalsIgnoreCase("jobSeeker");
    }

    public boolean isStringNotValid(String arg) {
        return arg == null || arg.isBlank();
    }
}
