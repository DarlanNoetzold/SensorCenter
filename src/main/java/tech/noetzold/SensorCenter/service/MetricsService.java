package tech.noetzold.SensorCenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.noetzold.SensorCenter.model.Metrics;
import tech.noetzold.SensorCenter.repository.MetricsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MetricsService {

    @Autowired
    private MetricsRepository metricsRepository;

    public Metrics saveMetrics(Metrics metrics) {
        return metricsRepository.save(metrics);
    }

    public List<Metrics> getAllMetrics() {
        return metricsRepository.findAll();
    }

    @KafkaListener(topics = "${spring.kafka.topic.metrics}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Map<String, Object> data) {
        Metrics metrics = convertMapToMetrics(data);
        saveMetrics(metrics);
    }

    private Metrics convertMapToMetrics(Map<String, Object> data) {
        Metrics metrics = new Metrics();
        metrics.setCpuUsage((Double) data.get("cpuUsage"));
        metrics.setMemoryUsage(((Number) data.get("memoryUsage")).longValue());
        metrics.setThreadCount((Integer) data.get("threadCount"));
        metrics.setTotalDataReceived(((Number) data.get("totalDataReceived")).longValue());
        metrics.setTotalDataFiltered(((Number) data.get("totalDataFiltered")).longValue());
        metrics.setTotalDataCompressed(((Number) data.get("totalDataCompressed")).longValue());
        metrics.setTotalDataAggregated(((Number) data.get("totalDataAggregated")).longValue());
        metrics.setTotalDataAfterHeuristics(((Number) data.get("totalDataAfterHeuristics")).longValue());
        metrics.setErrorCount(((Number) data.get("errorCount")).longValue());
        metrics.setProcessorId((String) data.get("processorId"));

        metrics.setVarianceMap((Map<String, Double>) data.get("varianceMap"));

        // Converte o ArrayList de timestamp para LocalDateTime, se presente
        List<Integer> timestamp = (List<Integer>) data.get("timestamp");
        if (timestamp != null && timestamp.size() == 7) {
            LocalDateTime dateTime = LocalDateTime.of(
                    timestamp.get(0),
                    timestamp.get(1),
                    timestamp.get(2),
                    timestamp.get(3),
                    timestamp.get(4),
                    timestamp.get(5),
                    timestamp.get(6)
            );
            metrics.setTimestamp(dateTime);
        }

        return metrics;
    }
}

