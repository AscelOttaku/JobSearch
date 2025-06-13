package kg.attractor.jobsearch.util;

import kg.attractor.jobsearch.dto.FIleInfoDto;
import kg.attractor.jobsearch.enums.MessageType;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtil {
    private static String directory = "data/photos";

    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String uploadFile(MultipartFile file) throws IOException {
        String fileName = getUniqueId() + "_" + file.getOriginalFilename();
        Path directoryPath = Paths.get(directory);

        if (!Files.isDirectory(directoryPath))
            Files.createDirectories(directoryPath);

        Path filePath = Paths.get(directory, fileName);

        if (!Files.exists(filePath))
            Files.createFile(filePath);

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public static String uploadFile(String filePath, MultipartFile file) throws IOException {
        directory = filePath;
        String path = uploadFile(file);

        directory = "data/photos";
        return path;
    }

    private static String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    @SneakyThrows
    public static ResponseEntity<?> getOutputFile(String filename, MediaType mediaType) {
        return getResponseEntityForFile(filename, directory, mediaType);
    }

    @SneakyThrows
    public static ResponseEntity<?> getOutputFile(String filename, String directory, MediaType mediaType) {
        return getResponseEntityForFile(filename, directory, mediaType);
    }

    private static ResponseEntity<?> getResponseEntityForFile(String filename, String directoryName, MediaType mediaType) throws IOException {
        try {
            byte[] image = Files.readAllBytes(Paths.get(directoryName + "/" + filename));
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

    public static MediaType defineFileType(String filePath) throws IOException {
        Assert.notNull(filePath, "filePath must not be null");
        String fileType = Files.probeContentType(Paths.get(filePath));
        return MediaType.parseMediaType(fileType);
    }

    @SneakyThrows
    public static void deleteFile(String filePath) {
        Assert.notNull(filePath, "filePath must not be null");
        Files.delete(Path.of(filePath));
    }

    @SneakyThrows
    public static FIleInfoDto createFileForMessages(MultipartFile multipartFile) {
        String filePath;
        MessageType messageType = MessageType.FILE;

        if (multipartFile.isEmpty())
            throw new IllegalArgumentException("File must not be empty");

        if (multipartFile.getContentType() != null && multipartFile.getContentType().startsWith("image/")) {
            filePath = FileUtil.uploadFile(multipartFile);
            messageType = MessageType.IMAGES;
        } else
            filePath = FileUtil.uploadFile("data/files", multipartFile);

        return FIleInfoDto.builder()
                .filePath(filePath)
                .messageType(messageType)
                .build();
    }
}
