// package site.railcartracker.site_metrics.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;
// import site.railcartracker.site_metrics.dto.PdfImportResponse;
// import site.railcartracker.site_metrics.integration.LocalAIClient;

// import java.util.ArrayList;
// import java.util.List;

// @Service
// public class PdfImportService {

//     @Autowired
//     private LocalAIClient localAIClient;

//     @Autowired
//     private RailcarEtlService railcarEtlService;

//     public List<PdfImportResponse> processPdfBatch(List<MultipartFile> files) {
//         List<PdfImportResponse> results = new ArrayList<>();

//         for (MultipartFile file : files) {
//             try {
//                 String extractedJson = localAIClient.extractTextAndData(file);
                
//                 // transform and load into db
//                 railcarEtlService.transformAndLoad(extractedJson);

//                 PdfImportResponse response = new PdfImportResponse(file.getOriginalFilename(), "SUCCESS",
//                         extractedJson);
//                 results.add(response);
//             } catch (Exception e) {
//                 results.add(new PdfImportResponse(file.getOriginalFilename(), "ERROR: " + e.getMessage(), null));
//             }
//         }
//         return results;
//     }
// }
