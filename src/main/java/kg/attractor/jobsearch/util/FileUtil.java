package kg.attractor.jobsearch.util;

import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

public class FileUtil {
    private static final String DIRECTORY = "data/images";

    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String uploadFile(MultipartFile file) throws IOException {
        String fileName = getUniqueId() + "_" + file.getOriginalFilename();
        Path directoryPath = Paths.get(DIRECTORY);

        if (!Files.isDirectory(directoryPath))
            Files.createDirectories(directoryPath);

        Path filePath = Paths.get(DIRECTORY, fileName);

        if (!Files.exists(filePath))
            Files.createFile(filePath);

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    private static String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    @SneakyThrows
    public ResponseEntity<Object> getOutputFile(String filename, MediaType mediaType) {
        try {
            byte[] image = Files.readAllBytes(Paths.get(DIRECTORY + "/" + filename));
            Resource resource = new ByteArrayResource(image);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (NoSuchFileException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Image not found");
        }
    }
}
