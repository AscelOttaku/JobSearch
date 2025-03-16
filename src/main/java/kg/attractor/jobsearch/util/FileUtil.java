package kg.attractor.jobsearch.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
}
