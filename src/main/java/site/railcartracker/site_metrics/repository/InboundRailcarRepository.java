package site.railcartracker.site_metrics.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import site.railcartracker.site_metrics.model.BadOrderedRailcar;
import site.railcartracker.site_metrics.model.InboundRailcar;

@Repository
public interface InboundRailcarRepository extends CrudRepository<InboundRailcar, Integer> {
	
	// Use this if I need to find a bad order by inspected date?
	Optional<BadOrderedRailcar> findByCarMarkAndCarNumberAndInspectedDate(String carMark, int carNumber, LocalDate inspectedDate);

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
