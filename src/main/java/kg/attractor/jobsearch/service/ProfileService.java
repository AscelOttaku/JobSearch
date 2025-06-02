package kg.attractor.jobsearch.service;

import java.util.Map;

public interface ProfileService {
    Map<String, Object> getProfile(int page, int size);

    Map<String, Object> getStatistics();
}
