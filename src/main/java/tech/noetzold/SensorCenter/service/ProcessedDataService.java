package tech.noetzold.SensorCenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.noetzold.SensorCenter.model.ProcessedData;
import tech.noetzold.SensorCenter.repository.ProcessedDataRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ProcessedDataService {

    @Autowired
    private ProcessedDataRepository processedDataRepository;

    public ProcessedData saveProcessedData(ProcessedData processedData) {
        return processedDataRepository.save(processedData);
    }

    public List<ProcessedData> getAllProcessedData() {
        return processedDataRepository.findAll();
    }

    @KafkaListener(topics = "${spring.kafka.topic.processed-data}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Map<String, Object> data) {
        // Converte o Map recebido em um objeto ProcessedData
        ProcessedData processedData = convertMapToProcessedData(data);
        saveProcessedData(processedData);
    }

    private ProcessedData convertMapToProcessedData(Map<String, Object> data) {
        ProcessedData processedData = new ProcessedData();
        processedData.setSensorType((String) data.get("sensorType"));
        processedData.setValue((Double) data.get("value"));
        processedData.setCoordinates((String) data.get("coordinates"));
        processedData.setProcessorId((String) data.get("processorId"));

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
            processedData.setTimestamp(dateTime);
        }

        return processedData;
    }
}
