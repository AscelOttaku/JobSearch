package kg.attractor.jobsearch.factory;

import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.RoleService;
import kg.attractor.jobsearch.strategy.FavoritesSaveStrategy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FavoritesSaveFactory {
    private final AuthorizedUserService authorizedUserService;
    private final Map<String, FavoritesSaveStrategy> strategies;

    public FavoritesSaveFactory(AuthorizedUserService authorizedUserService, RoleService roleService) {
        this.authorizedUserService = authorizedUserService;
        strategies = setStrategies();
    }

    private static Map<String, FavoritesSaveStrategy> setStrategies() {
        return Arrays.stream(FavoritesSaveStrategy.values())
                .collect(Collectors.toMap(
                        FavoritesSaveStrategy::getRoleName,
                        Function.identity()
                ));
    }

    public FavoritesSaveStrategy createFavoritesSaveStrategy() {
        String accountType = authorizedUserService.getAuthorizedUser().getAccountType();
        return strategies.get(accountType);
    }
}
