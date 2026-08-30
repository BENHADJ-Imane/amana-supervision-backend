package ma.baridalmaghrib.amana_supervision_backend.config;

import lombok.RequiredArgsConstructor;
import ma.baridalmaghrib.amana_supervision_backend.model.Shipment;
import ma.baridalmaghrib.amana_supervision_backend.model.StatutEnvoi;
import ma.baridalmaghrib.amana_supervision_backend.repository.ShipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
// Insere quelques envois de test au demarrage, UNIQUEMENT si la table est vide.
// Equivalent de mockData.js cote frontend - a retirer ou desactiver
// une fois que de vraies donnees existeront.
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ShipmentRepository shipmentRepository;

    @Override
    public void run(String... args) {
        if (shipmentRepository.count() > 0) {
            return; // deja des donnees, on ne fait rien
        }

        Shipment s1 = new Shipment();
        s1.setCodeEnvoi("QB183609979MA");
        s1.setExpediteur("Academie Regional De L'Education Et Formation Regi");
        s1.setDateDepot(LocalDateTime.of(2026, 6, 19, 13, 51));
        s1.setStatut(StatutEnvoi.LIVRE);
        s1.setDateStatut(LocalDateTime.of(2026, 6, 22, 13, 17));
        s1.setPod(false);
        s1.setVille("Rabat");

        Shipment s2 = new Shipment();
        s2.setCodeEnvoi("QB183610012MA");
        s2.setExpediteur("Office National des Chemins de Fer");
        s2.setDateDepot(LocalDateTime.of(2026, 6, 16, 14, 5));
        s2.setStatut(StatutEnvoi.LIVRE);
        s2.setDateStatut(LocalDateTime.of(2026, 6, 17, 23, 0));
        s2.setPod(false);
        s2.setVille("Casablanca");

        Shipment s3 = new Shipment();
        s3.setCodeEnvoi("QB183610587MA");
        s3.setExpediteur("Agence Urbaine de Rabat");
        s3.setDateDepot(LocalDateTime.of(2026, 6, 15, 9, 45));
        s3.setStatut(StatutEnvoi.LIVRE);
        s3.setDateStatut(LocalDateTime.of(2026, 6, 16, 13, 16));
        s3.setPod(true);
        s3.setPodImageUrl("https://placehold.co/500x350?text=POD+Signature");
        s3.setDateExport(LocalDateTime.of(2026, 6, 16, 15, 0));
        s3.setVille("Rabat");

        Shipment s4 = new Shipment();
        s4.setCodeEnvoi("QB183611203MA");
        s4.setExpediteur("Ministere de la Jeunesse et des Sports");
        s4.setDateDepot(LocalDateTime.of(2026, 6, 8, 12, 1));
        s4.setStatut(StatutEnvoi.LIVRE);
        s4.setDateStatut(LocalDateTime.of(2026, 6, 12, 14, 54));
        s4.setPod(false);
        s4.setVille("Fes");

        Shipment s5 = new Shipment();
        s5.setCodeEnvoi("QB183611998MA");
        s5.setExpediteur("Caisse Nationale de Securite Sociale");
        s5.setDateDepot(LocalDateTime.of(2026, 6, 10, 13, 12));
        s5.setStatut(StatutEnvoi.EN_COURS);
        s5.setDateStatut(LocalDateTime.of(2026, 6, 11, 23, 0));
        s5.setPod(false);
        s5.setVille("Tanger");

        Shipment s6 = new Shipment();
        s6.setCodeEnvoi("QB183612440MA");
        s6.setExpediteur("Academie Regionale Marrakech-Safi");
        s6.setDateDepot(LocalDateTime.of(2026, 6, 3, 12, 49));
        s6.setStatut(StatutEnvoi.LIVRE);
        s6.setDateStatut(LocalDateTime.of(2026, 6, 4, 13, 24));
        s6.setPod(true);
        s6.setPodImageUrl("https://placehold.co/500x350?text=POD+Signature");
        s6.setDateExport(LocalDateTime.of(2026, 6, 5, 9, 30));
        s6.setVille("Marrakech");

        Shipment s7 = new Shipment();
        s7.setCodeEnvoi("QB183613087MA");
        s7.setExpediteur("Agence Nationale de la Conservation Fonciere");
        s7.setDateDepot(LocalDateTime.of(2026, 5, 21, 14, 52));
        s7.setStatut(StatutEnvoi.EN_ATTENTE);
        s7.setDateStatut(LocalDateTime.of(2026, 5, 21, 23, 0));
        s7.setPod(false);
        s7.setVille("Agadir");

        shipmentRepository.saveAll(List.of(s1, s2, s3, s4, s5, s6, s7));
    }
}