package kg.attractor.jobsearch.factory;

import kg.attractor.jobsearch.strategy.favorites.FavoriteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FavoritesFactory {
    private Map<String, FavoriteStrategy> strategies;

    @Autowired
    private void setStrategies(@Lazy List<FavoriteStrategy> favoriteStrategies) {
        strategies = favoriteStrategies.stream()
                .collect(Collectors.toMap(
                        FavoriteStrategy::getRoleName,
                        Function.identity())
                );
    }

    public FavoriteStrategy createFavoritesStrategy(String userAccountType) {
        Assert.notNull(userAccountType, "userAccountType must not be null");
        return strategies.get(userAccountType);
    }
}
