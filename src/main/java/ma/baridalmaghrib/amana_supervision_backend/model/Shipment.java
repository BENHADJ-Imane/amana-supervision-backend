package ma.baridalmaghrib.amana_supervision_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_envoi", nullable = false, unique = true)
    private String codeEnvoi;

    @Column(nullable = false)
    private String expediteur;

    @Column(name = "date_depot", nullable = false)
    private LocalDateTime dateDepot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEnvoi statut;

    @Column(name = "date_statut")
    private LocalDateTime dateStatut;

    @Column(nullable = false)
    private boolean pod = false;

    @Column(name = "pod_image_url")
    private String podImageUrl;

    @Column(name = "date_export")
    private LocalDateTime dateExport;

    @Column
    private String ville;
}