package ma.baridalmaghrib.amana_supervision_backend.repository;

import ma.baridalmaghrib.amana_supervision_backend.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>, JpaSpecificationExecutor<Shipment> {

    // Recherche par code d'envoi exact (page "Rechercher un code d'envoi").
    Optional<Shipment> findByCodeEnvoi(String codeEnvoi);
}