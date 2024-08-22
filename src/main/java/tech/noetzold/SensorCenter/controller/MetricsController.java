package tech.noetzold.SensorCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.SensorCenter.model.Metrics;
import tech.noetzold.SensorCenter.service.MetricsService;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    @Autowired
    private MetricsService metricsService;

    @PostMapping
    public Metrics receiveMetrics(@RequestBody Metrics metrics) {
        return metricsService.saveMetrics(metrics);
    }

    @GetMapping
    public List<Metrics> getAllMetrics() {
        return metricsService.getAllMetrics();
    }
}