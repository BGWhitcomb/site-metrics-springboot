// package site.railcartracker.site_metrics.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import site.railcartracker.site_metrics.dto.PdfImportResponse;
// import site.railcartracker.site_metrics.service.PdfImportService;

// @RestController
// @RequestMapping("/api/import")
// public class PdfImportController {

//     @Autowired
//     private PdfImportService pdfImportService;

//     @PostMapping("/upload")
//     public ResponseEntity<List<PdfImportResponse>> uploadPdfBatch(@RequestParam("files") List<MultipartFile> files) {
//         List<PdfImportResponse> responses = pdfImportService.processPdfBatch(files);
//         return ResponseEntity.ok(responses);
//     }

// }
