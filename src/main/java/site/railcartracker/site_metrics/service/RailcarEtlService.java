package site.railcartracker.site_metrics.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.railcartracker.site_metrics.model.InboundRailcar;
import site.railcartracker.site_metrics.model.BadOrderedRailcar;
import site.railcartracker.site_metrics.repository.InboundRailcarRepository;
import site.railcartracker.site_metrics.repository.BadOrderedRailcarRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class RailcarEtlService {

    @Autowired
    private InboundRailcarRepository inboundRepo;

    @Autowired
    private BadOrderedRailcarRepository badOrderRepo;

    private final ObjectMapper mapper = new ObjectMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void transformAndLoad(String extractedJson) throws Exception {
        JsonNode root = mapper.readTree(extractedJson);

        boolean isBadOrder = root.path("isBadOrdered").asBoolean(false);

        if (isBadOrder) {
            BadOrderedRailcar badOrder = mapToBadOrderedRailcar(root);
            badOrderRepo.save(badOrder);
        } else {
            InboundRailcar inbound = mapToInboundRailcar(root);
            inboundRepo.save(inbound);
        }
    }

    private InboundRailcar mapToInboundRailcar(JsonNode node) {
        InboundRailcar railcar = new InboundRailcar();
        railcar.setCarMark(node.path("carMark").asText());
        railcar.setCarNumber(node.path("carNumber").asInt());
        railcar.setRepaired(node.path("isRepaired").asBoolean(false));
        railcar.setInspectedDate(parseLocalDate(node.path("inspectedDate").asText()));
        railcar.setBadOrdered(node.path("isBadOrdered").asBoolean(false));
        return railcar;
    }

    private BadOrderedRailcar mapToBadOrderedRailcar(JsonNode node) {
        BadOrderedRailcar badOrdered = new BadOrderedRailcar();
        badOrdered.setCarMark(node.path("carMark").asText());
        badOrdered.setCarNumber(node.path("carNumber").asInt());
        badOrdered.setBadOrderDescription(node.path("badOrderReason").asText());
        badOrdered.setBadOrderDate(parseLocalDate(node.path("badOrderDate").asText()));

        if (node.hasNonNull("repairedDate")) {
            badOrdered.setRepairedDate(parseLocalDate(node.path("repairedDate").asText()));
        }

        // Optional: also persist inbound record for completeness
        // InboundRailcar inbound = mapToInboundRailcar(node);
        // inboundRepo.save(inbound);

        return badOrdered;
    }

    private LocalDate parseLocalDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            // fallback: handle non-standard date format from OCR
            try {
                return LocalDate.parse(dateStr.trim());
            } catch (DateTimeParseException ignored) {
                return null;
            }
        }
    }
}
