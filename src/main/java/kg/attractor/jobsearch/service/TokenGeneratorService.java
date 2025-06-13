package kg.attractor.jobsearch.service;

public interface TokenGeneratorService {
    String generateTokenForGroup(Long groupId, String uri);
}
