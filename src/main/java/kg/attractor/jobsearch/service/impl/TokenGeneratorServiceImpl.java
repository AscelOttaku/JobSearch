package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.service.TokenGeneratorService;
import kg.attractor.jobsearch.storage.TemporalStorage;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenGeneratorServiceImpl implements TokenGeneratorService {
    private final TemporalStorage temporalStorage;

    @Override
    public String generateTokenForGroup(Long groupId, String uri) {
        Assert.isTrue(groupId != null && groupId > 0, "Group ID must be a positive number");
        Assert.notNull(uri, "URI must not be null");

        Optional<String> storedToken = temporalStorage.getOptionalTemporalData("groupToken_" + groupId, String.class);

        if (storedToken.isPresent()) {
            LocalDateTime createdTime = temporalStorage.getTemporalData("groupToken_" + groupId + "_created", LocalDateTime.class);
            Duration duration = Duration.between(createdTime, LocalDateTime.now());

            if (duration.toMinutes() <= 60)
                return storedToken.get();
        }

        String token = uri + "/groups_users/join_group/groupId/" + groupId + "/token/" + Util.generateUniqueValue();
        temporalStorage.addData("groupToken_" + groupId, token);
        temporalStorage.addData("groupToken_" + groupId + "_created", LocalDateTime.now());
        return token;
    }
}
