package kg.attractor.jobsearch.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.UniqueLogin;
import kg.attractor.jobsearch.dto.ChannelDto;
import kg.attractor.jobsearch.service.ChannelService;

import java.util.Objects;

public class ChannelUniqueLoginValidator implements ConstraintValidator<UniqueLogin, ChannelDto> {
    private final ChannelService channelService;

    public ChannelUniqueLoginValidator(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    public boolean isValid(ChannelDto channelDto, ConstraintValidatorContext constraintValidatorContext) {
        if (channelDto.getLogin() == null || channelDto.getLogin().isBlank()) {
            return true;
        }

        if (channelService.existByLogin(channelDto.getLogin())) {
            if (channelDto.getId() == null) {
                loginMustBeUniqueMessageBuild(constraintValidatorContext);
                return false;
            }

            ChannelDto createdChannel = channelService.findChannelById(channelDto.getId());
            boolean result = createdChannel != null && Objects.equals(createdChannel.getLogin(), channelDto.getLogin());
            if (!result) {
                loginMustBeUniqueMessageBuild(constraintValidatorContext);
            }
            return result;
        }
        return true;
    }

    private static void loginMustBeUniqueMessageBuild(ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate("Login must be unique")
                .addPropertyNode("login")
                .addConstraintViolation();
    }
}