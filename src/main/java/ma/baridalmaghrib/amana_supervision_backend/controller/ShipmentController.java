package ma.baridalmaghrib.amana_supervision_backend.controller;

import lombok.RequiredArgsConstructor;
import ma.baridalmaghrib.amana_supervision_backend.dto.ShipmentDTO;
import ma.baridalmaghrib.amana_supervision_backend.dto.StatisticsDTO;
import ma.baridalmaghrib.amana_supervision_backend.service.ShipmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    // GET /api/shipments?code=...&statut=...&pod=...&dateDepotFrom=...&dateDepotTo=...
    // Tous les parametres sont optionnels. Sans aucun parametre = tous les envois.
    @GetMapping
    public List<ShipmentDTO> getFilteredShipments(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String pod,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDepotFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDepotTo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStatutFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStatutTo
    ) {
        return shipmentService.getFilteredShipments(
                code, statut, pod, dateDepotFrom, dateDepotTo, dateStatutFrom, dateStatutTo
        );
    }

    @GetMapping("/{code}")
    public ResponseEntity<ShipmentDTO> getShipmentByCode(@PathVariable String code) {
        return shipmentService.getShipmentByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
        // GET /api/shipments/statistics?statut=...&pod=...&... (memes filtres que la liste)
    @GetMapping("/statistics")
    public StatisticsDTO getStatistics(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String pod,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDepotFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDepotTo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStatutFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStatutTo
    ) {
        return shipmentService.getStatistics(
                code, statut, pod, dateDepotFrom, dateDepotTo, dateStatutFrom, dateStatutTo
        );
    }

        // POST /api/shipments/{id}/pod (multipart/form-data avec un champ "file")
    @PostMapping("/{id}/pod")
    public ResponseEntity<ShipmentDTO> addPod(
            @PathVariable Long id,
            @RequestParam("file") org.springframework.web.multipart.MultipartFile file
    ) {
        try {
            ShipmentDTO updated = shipmentService.addPod(id, file);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}