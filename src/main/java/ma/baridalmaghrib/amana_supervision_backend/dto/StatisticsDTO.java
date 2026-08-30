package ma.baridalmaghrib.amana_supervision_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StatisticsDTO {
    private List<StatusCountDTO> statusBreakdown;
    private List<PodCountDTO> podBreakdown;
    private List<TimelinePointDTO> timeline;
    private List<CityCountDTO> byCity;

    @Getter
    @AllArgsConstructor
    public static class StatusCountDTO {
        private String statut;
        private long count;
    }

    @Getter
    @AllArgsConstructor
    public static class PodCountDTO {
        private String label; // "avec" ou "sans"
        private long count;
    }

    @Getter
    @AllArgsConstructor
    public static class TimelinePointDTO {
        private String yearMonth; // format "2026-06"
        private long count;
    }

    @Getter
    @AllArgsConstructor
    public static class CityCountDTO {
        private String ville;
        private long count;
    }
}