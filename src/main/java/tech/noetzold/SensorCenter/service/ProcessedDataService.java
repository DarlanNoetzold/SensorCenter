package tech.noetzold.SensorCenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.noetzold.SensorCenter.model.ProcessedData;
import tech.noetzold.SensorCenter.repository.ProcessedDataRepository;

import java.util.List;

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
    public void consume(ProcessedData processedData) {
        saveProcessedData(processedData);
    }
}
