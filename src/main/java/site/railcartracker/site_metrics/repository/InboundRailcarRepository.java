package site.railcartracker.site_metrics.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import site.railcartracker.site_metrics.model.InboundRailcar;

@Repository
public interface InboundRailcarRepository extends CrudRepository<InboundRailcar, Integer> {

//	// Find all railcars inspected between two dates
//	List<InboundRailcar> findByInspectedDateBetween(LocalDate startDate, LocalDate endDate);
//
//	// Find the most recent inspection for a given car number
//	InboundRailcar findByCarNumber(Integer carNumber);
//
//	// Find all railcars by car mark and number
//	List<InboundRailcar> findByCarMarkAndNumber(String carMark, Integer carNumber);
//
//	// Find all railcars by inspection date
//	List<InboundRailcar> findByInspectedDate(LocalDate inspectedDate);
}
