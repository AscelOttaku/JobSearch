package kg.attractor.jobsearch.exceptions.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.body.InputElementExceptionBody;
import kg.attractor.jobsearch.exceptions.body.ValidationExceptionBody;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
@Valid
public class GlobalControllerAdvice {
    private final ErrorService errorService;
    private final AuthorizedUserService authorizedUserService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionBody handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request
    ) {
        return errorService.handleValidationException(ex, request);
    }

    @ExceptionHandler({NoSuchElementException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(
            Model model, RuntimeException ex, HttpServletRequest request
    ) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(CustomIllegalArgException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleCustomIllegalArgException(
            CustomIllegalArgException ex, HttpServletRequest request, Model model
    ) {
        InputElementExceptionBody body = errorService.handleInvalidArgumentException(ex, request);

        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("message", body.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHandlerMethodValidationException(
            HandlerMethodValidationException ex, HttpServletRequest request, Model model
    ) {
        Map<String, Object> exceptionBody = errorService.handleMethodValidationException(ex, request);

        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("message", exceptionBody.get("message"));
        model.addAttribute("details", request);

        return "errors/error";
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleDateTimeParseException(DateTimeParseException ex) {
        return errorService.handleDateTimeParserException(ex);
    }

    @ExceptionHandler({IllegalArgumentException.class, IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(
            Exception ex,
            HttpServletRequest request,
            Model model) {

        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ModelAttribute
    public void addUserToModels(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken))
            model.addAttribute("authorizedUser", authorizedUserService.getAuthorizedUser());
    }
}
