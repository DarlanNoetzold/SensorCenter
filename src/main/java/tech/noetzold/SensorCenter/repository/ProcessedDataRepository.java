package tech.noetzold.SensorCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.noetzold.SensorCenter.model.ProcessedData;

@Repository
public interface ProcessedDataRepository extends JpaRepository<ProcessedData, Long> {
}
