// package site.railcartracker.site_metrics.integration;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.*;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.multipart.MultipartFile;

// import java.util.Map;

// @Component
// public class LocalAIClient {

//     @Value("${localai.api.url}")
//     private String localAiUrl;

//     private final RestTemplate restTemplate = new RestTemplate();

//     public String extractTextAndData(MultipartFile file) throws Exception {
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.MULTIPART_FORM_DATA);

//         HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);
//         ResponseEntity<Map> response = restTemplate.exchange(
//                 localAiUrl + "/v1/extract",
//                 HttpMethod.POST,
//                 entity,
//                 Map.class);

//         if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//             return response.getBody().toString();
//         } else {
//             throw new RuntimeException("Failed to extract data from LocalAI service");
//         }
//     }
// }
