package ma.baridalmaghrib.amana_supervision_backend.service;

import lombok.RequiredArgsConstructor;
import ma.baridalmaghrib.amana_supervision_backend.dto.ShipmentDTO;
import ma.baridalmaghrib.amana_supervision_backend.dto.StatisticsDTO;
import ma.baridalmaghrib.amana_supervision_backend.model.Shipment;
import ma.baridalmaghrib.amana_supervision_backend.model.StatutEnvoi;
import ma.baridalmaghrib.amana_supervision_backend.repository.ShipmentRepository;
import ma.baridalmaghrib.amana_supervision_backend.Specification.ShipmentSpecification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ma.baridalmaghrib.amana_supervision_backend.service.FileStorageService;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
        private final FileStorageService fileStorageService;

    public List<ShipmentDTO> getFilteredShipments(
            String code, String statut, String pod,
            LocalDate dateDepotFrom, LocalDate dateDepotTo,
            LocalDate dateStatutFrom, LocalDate dateStatutTo
    ) {
        var spec = ShipmentSpecification.withFilters(
                code, statut, pod, dateDepotFrom, dateDepotTo, dateStatutFrom, dateStatutTo
        );
        return shipmentRepository.findAll(spec).stream().map(this::toDTO).toList();
    }

    public Optional<ShipmentDTO> getShipmentByCode(String codeEnvoi) {
        return shipmentRepository.findByCodeEnvoi(codeEnvoi).map(this::toDTO);
    }

    public StatisticsDTO getStatistics(
            String code, String statut, String pod,
            LocalDate dateDepotFrom, LocalDate dateDepotTo,
            LocalDate dateStatutFrom, LocalDate dateStatutTo
    ) {
        var spec = ShipmentSpecification.withFilters(
                code, statut, pod, dateDepotFrom, dateDepotTo, dateStatutFrom, dateStatutTo
        );
        List<Shipment> shipments = shipmentRepository.findAll(spec);

        return new StatisticsDTO(
                buildStatusBreakdown(shipments),
                buildPodBreakdown(shipments),
                buildTimeline(shipments),
                buildCityBreakdown(shipments)
        );
    }

    private List<StatisticsDTO.StatusCountDTO> buildStatusBreakdown(List<Shipment> shipments) {
        return java.util.Arrays.stream(StatutEnvoi.values())
                .map(statutEnum -> {
                    long count = shipments.stream()
                            .filter(s -> s.getStatut() == statutEnum)
                            .count();
                    return new StatisticsDTO.StatusCountDTO(statutEnum.name(), count);
                })
                .toList();
    }

    private List<StatisticsDTO.PodCountDTO> buildPodBreakdown(List<Shipment> shipments) {
        long avec = shipments.stream().filter(Shipment::isPod).count();
        long sans = shipments.size() - avec;
        return List.of(
                new StatisticsDTO.PodCountDTO("avec", avec),
                new StatisticsDTO.PodCountDTO("sans", sans)
        );
    }

    private List<StatisticsDTO.TimelinePointDTO> buildTimeline(List<Shipment> shipments) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        Map<String, Long> grouped = shipments.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getDateDepot().format(formatter),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        return grouped.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new StatisticsDTO.TimelinePointDTO(e.getKey(), e.getValue()))
                .toList();
    }

    private List<StatisticsDTO.CityCountDTO> buildCityBreakdown(List<Shipment> shipments) {
        Map<String, Long> grouped = shipments.stream()
                .filter(s -> s.getVille() != null)
                .collect(Collectors.groupingBy(Shipment::getVille, Collectors.counting()));

        return grouped.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(e -> new StatisticsDTO.CityCountDTO(e.getKey(), e.getValue()))
                .toList();
    }

    private ShipmentDTO toDTO(Shipment s) {
        return new ShipmentDTO(
                s.getId(), s.getCodeEnvoi(), s.getExpediteur(), s.getDateDepot(),
                s.getStatut().name(), s.getDateStatut(), s.isPod(), s.getPodImageUrl(),
                s.getDateExport(), s.getVille()
        );
    }

        public ShipmentDTO addPod(Long shipmentId, org.springframework.web.multipart.MultipartFile file) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Envoi introuvable avec id " + shipmentId));

        String imageUrl = fileStorageService.store(file);
        shipment.setPod(true);
        shipment.setPodImageUrl(imageUrl);

        Shipment saved = shipmentRepository.save(shipment);
        return toDTO(saved);
    }
}