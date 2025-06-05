package kg.attractor.jobsearch.controller.api;

import kg.attractor.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class FileController {

    @GetMapping("filePath/{filePath:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filePath, @RequestParam String fileType) throws IOException {
        if (fileType.equals("IMAGES"))
            return FileUtil.getOutputFile(filePath, FileUtil.defineFileType(filePath));
        else
            return FileUtil.getOutputFile(filePath, "data/files", FileUtil.defineFileType(filePath));
    }
}
