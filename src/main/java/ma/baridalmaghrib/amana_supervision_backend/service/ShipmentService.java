package ma.baridalmaghrib.amana_supervision_backend.service;

import lombok.RequiredArgsConstructor;
import ma.baridalmaghrib.amana_supervision_backend.dto.ShipmentDTO;
import ma.baridalmaghrib.amana_supervision_backend.model.Shipment;
import ma.baridalmaghrib.amana_supervision_backend.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public List<ShipmentDTO> getAllShipments() {
        return shipmentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<ShipmentDTO> getShipmentByCode(String codeEnvoi) {
        return shipmentRepository.findByCodeEnvoi(codeEnvoi)
                .map(this::toDTO);
    }

    // Convertit une entite Shipment (base de donnees) en ShipmentDTO (reponse API).
    private ShipmentDTO toDTO(Shipment s) {
        return new ShipmentDTO(
                s.getId(),
                s.getCodeEnvoi(),
                s.getExpediteur(),
                s.getDateDepot(),
                s.getStatut().name(),
                s.getDateStatut(),
                s.isPod(),
                s.getPodImageUrl(),
                s.getDateExport(),
                s.getVille()
        );
    }
}