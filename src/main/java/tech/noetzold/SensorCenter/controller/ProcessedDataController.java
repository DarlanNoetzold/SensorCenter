package tech.noetzold.SensorCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.SensorCenter.model.ProcessedData;
import tech.noetzold.SensorCenter.service.ProcessedDataService;

import java.util.List;

@RestController
@RequestMapping("/api/processed-data")
public class ProcessedDataController {

    @Autowired
    private ProcessedDataService processedDataService;

    @PostMapping
    public ProcessedData receiveProcessedData(@RequestBody ProcessedData processedData) {
        return processedDataService.saveProcessedData(processedData);
    }

    @GetMapping
    public List<ProcessedData> getAllProcessedData() {
        return processedDataService.getAllProcessedData();
    }
}
