package kg.attractor.jobsearch.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.NonEmptyMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class NotEmptyMultipartFileValidator implements ConstraintValidator<NonEmptyMultipartFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file != null && !file.isEmpty();
    }
}
