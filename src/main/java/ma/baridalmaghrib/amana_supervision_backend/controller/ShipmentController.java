package ma.baridalmaghrib.amana_supervision_backend.controller;

import lombok.RequiredArgsConstructor;
import ma.baridalmaghrib.amana_supervision_backend.dto.ShipmentDTO;
import ma.baridalmaghrib.amana_supervision_backend.service.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    // GET /api/shipments -> liste de tous les envois
    @GetMapping
    public List<ShipmentDTO> getAllShipments() {
        return shipmentService.getAllShipments();
    }

    // GET /api/shipments/{code} -> recherche par code d'envoi
    @GetMapping("/{code}")
    public ResponseEntity<ShipmentDTO> getShipmentByCode(@PathVariable String code) {
        return shipmentService.getShipmentByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}