package kg.attractor.jobsearch.controller.api;

import kg.attractor.jobsearch.storage.TemporalStorage;
import kg.attractor.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class FileController {
    private final TemporalStorage temporalStorage;

    @GetMapping("filePath/{filePath:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filePath, @RequestParam String fileType) throws IOException {
        if (fileType.equals("IMAGES"))
            return FileUtil.getOutputFile(filePath, FileUtil.defineFileType(filePath));
        else
            return FileUtil.getOutputFile(filePath, "data/files", FileUtil.defineFileType(filePath));
    }

    @GetMapping("video/{filePath:.+}")
    public ResponseEntity<InputStreamResource> downloadVide(@PathVariable String filePath) throws IOException {
        return FileUtil.getResponseEntityForFile(filePath, FileUtil.defineFileTypeVideo(filePath));
    }

    @GetMapping("/message/video/{filePath:.+}")
    public ResponseEntity<InputStreamResource> downloadVideoInMessages(@PathVariable String filePath) throws IOException {
        if (temporalStorage.isDataExist(filePath)) {
            ParameterizedTypeReference<ResponseEntity<InputStreamResource>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};
            return temporalStorage.getTemporalData(filePath, parameterizedTypeReference);
        }

        ResponseEntity<InputStreamResource> responseEntityForFile = FileUtil.getResponseEntityForFile(filePath, FileUtil.defineFileType(filePath));
        temporalStorage.addData(filePath, responseEntityForFile);
        return responseEntityForFile;
    }
}
