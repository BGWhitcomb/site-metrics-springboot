package site.railcartracker.site_metrics.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import site.railcartracker.site_metrics.model.BadOrderedRailcar;

@Repository
public interface BadOrderedRailcarRepository extends CrudRepository<BadOrderedRailcar, Integer> {
	//placeholder for repo method ideas, need to figure out query methods
	
	 BadOrderedRailcar findByInboundRailcar_InboundId(Integer inboundId);
	 
	 List<BadOrderedRailcar> findByIsActive(boolean isActive);
	
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
