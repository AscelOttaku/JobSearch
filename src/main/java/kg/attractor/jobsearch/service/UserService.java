package kg.attractor.jobsearch.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseEntity<String> uploadAvatar(MultipartFile file);
}
