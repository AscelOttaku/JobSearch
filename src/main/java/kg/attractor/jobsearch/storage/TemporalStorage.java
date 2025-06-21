package kg.attractor.jobsearch.storage;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.core.ParameterizedTypeReference;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class TemporalStorage {
    private final Map<String, Object> temporalData = new HashMap<>();

    public void addData(String key, Object value) {
        if (key == null || key.isBlank() || value == null)
            throw new IllegalArgumentException("key is null or blank or value is null");

        this.temporalData.put(key, value);
    }

    public boolean isDataExist(String key) {
        Assert.notNull(key, "key is null");
        return this.temporalData.containsKey(key);
    }

    public <T> T getTemporalData(String key, Class<T> type) {
        if (key == null || key.isBlank())
            throw new IllegalArgumentException("key is null or blank");

        Object value = temporalData.get(key);

        if (type == null || !type.isInstance(value))
            throw new ClassCastException("value is null or cannot be cast to given type");

        return Optional.of(type.cast(temporalData.get(key)))
                .orElseThrow(() -> new NoSuchElementException("value do not exists by key " + key));
    }

    public <T> Optional<T> getOptionalTemporalData(String key, Class<T> type) {
        if (key == null || key.isBlank())
            return Optional.empty();

        Object value = temporalData.get(key);

        if (value == null) return Optional.empty();

        if (type == null || !type.isInstance(value))
            throw new ClassCastException("value is null or cannot be cast to given type");

        return Optional.of(type.cast(value));
    }

    public <T> T getTemporalData(String key, ParameterizedTypeReference<T> type) {
        if (key == null || key.isBlank())
            throw new IllegalArgumentException("key is null or blank");

        Object value = temporalData.get(key);

        if (value == null)
            throw new NoSuchElementException("value do not exists by key " + key);

        String typeName = type.getType().getTypeName();
        String valueTypeName = value.getClass().getTypeName();
        if (!typeName.startsWith(valueTypeName))
            throw new ClassCastException("value is null or cannot be cast to given type");

        return (T) value;
    }

    public void removeTemporalData(String key) {
        if (key == null || key.isBlank())
            throw new IllegalArgumentException("key is null or blank");

        temporalData.remove(key);
    }
}
