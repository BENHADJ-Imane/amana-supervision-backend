package ma.baridalmaghrib.amana_supervision_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ShipmentDTO {
    private Long id;
    private String codeEnvoi;
    private String expediteur;
    private LocalDateTime dateDepot;
    private String statut;
    private LocalDateTime dateStatut;
    private boolean pod;
    private String podImageUrl;
    private LocalDateTime dateExport;
    private String ville;
}