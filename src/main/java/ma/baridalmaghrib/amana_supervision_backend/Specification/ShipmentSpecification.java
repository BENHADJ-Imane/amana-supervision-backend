package ma.baridalmaghrib.amana_supervision_backend.Specification;

import jakarta.persistence.criteria.Predicate;
import ma.baridalmaghrib.amana_supervision_backend.model.Shipment;
import ma.baridalmaghrib.amana_supervision_backend.model.StatutEnvoi;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Construit une requete de filtrage dynamique, equivalent de filterShipments.js
// cote frontend, mais executee directement en base de donnees.
public class ShipmentSpecification {

    public static Specification<Shipment> withFilters(
            String code,
            String statut,
            String pod,
            LocalDate dateDepotFrom,
            LocalDate dateDepotTo,
            LocalDate dateStatutFrom,
            LocalDate dateStatutTo
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (code != null && !code.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("codeEnvoi")), "%" + code.toLowerCase() + "%"));
            }

            if (statut != null && !statut.isBlank()) {
                try {
                    StatutEnvoi statutEnum = StatutEnvoi.valueOf(statut.toUpperCase());
                    predicates.add(cb.equal(root.get("statut"), statutEnum));
                } catch (IllegalArgumentException ignored) {
                    // statut inconnu envoye -> on ignore ce filtre plutot que de planter
                }
            }

            if (pod != null && !pod.isBlank()) {
                if (pod.equalsIgnoreCase("avec")) {
                    predicates.add(cb.isTrue(root.get("pod")));
                } else if (pod.equalsIgnoreCase("sans")) {
                    predicates.add(cb.isFalse(root.get("pod")));
                }
            }

            if (dateDepotFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateDepot"), dateDepotFrom.atStartOfDay()));
            }
            if (dateDepotTo != null) {
                LocalDateTime endOfDay = LocalDateTime.of(dateDepotTo, LocalTime.MAX);
                predicates.add(cb.lessThanOrEqualTo(root.get("dateDepot"), endOfDay));
            }

            if (dateStatutFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateStatut"), dateStatutFrom.atStartOfDay()));
            }
            if (dateStatutTo != null) {
                LocalDateTime endOfDay = LocalDateTime.of(dateStatutTo, LocalTime.MAX);
                predicates.add(cb.lessThanOrEqualTo(root.get("dateStatut"), endOfDay));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}