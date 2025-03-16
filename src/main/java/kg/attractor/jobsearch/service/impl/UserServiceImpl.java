package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public ResponseEntity<String> uploadAvatar(MultipartFile file) {
        //ToDO save user avatar logic should be implemented

        try {
            String result = FileUtil.uploadFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(result);

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
