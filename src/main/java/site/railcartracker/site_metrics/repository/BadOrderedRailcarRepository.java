package site.railcartracker.site_metrics.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import site.railcartracker.site_metrics.model.BadOrderedRailcar;

@Repository
public interface BadOrderedRailcarRepository extends CrudRepository<BadOrderedRailcar, Integer> {
	// placeholder for repo method ideas, need to figure out query methods
	// refactor this to use bo id
	BadOrderedRailcar findByInboundRailcar_InboundId(Integer inboundId);

	Optional<BadOrderedRailcar> findByCarMarkAndCarNumberAndInboundRailcar_InboundId(String carMark, Integer carNumber,
			Long inboundId);

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
