package site.railcartracker.site_metrics.dto;

public class PdfImportResponse {
    private String filename;
    private String status;
    private String extractedData;

    public PdfImportResponse(String filename, String status, String extractedData) {
        this.filename = filename;
        this.status = status;
        this.extractedData = extractedData;
    }

    // getters and setters
}
