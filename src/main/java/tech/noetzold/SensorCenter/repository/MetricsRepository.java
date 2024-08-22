package tech.noetzold.SensorCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.noetzold.SensorCenter.model.Metrics;

@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Long> {
}
