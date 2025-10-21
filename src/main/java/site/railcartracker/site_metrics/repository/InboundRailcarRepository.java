package site.railcartracker.site_metrics.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import site.railcartracker.site_metrics.model.BadOrderedRailcar;
import site.railcartracker.site_metrics.model.InboundRailcar;

@Repository
public interface InboundRailcarRepository extends JpaRepository<InboundRailcar, Integer> {

	// Existing method
	Optional<BadOrderedRailcar> findByCarMarkAndCarNumberAndInspectedDate(String carMark, int carNumber,
			LocalDate inspectedDate);

	// Custom paginated queries.. will work on this more when I get to filters
	Page<InboundRailcar> findByCarMarkContainingIgnoreCase(String carMark, Pageable pageable);

	Page<InboundRailcar> findByInspectedDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

	@Query("SELECT i FROM InboundRailcar i WHERE "
			+ "(:carMark IS NULL OR LOWER(i.carMark) LIKE LOWER(CONCAT('%', :carMark, '%'))) AND "
			+ "(:startDate IS NULL OR i.inspectedDate >= :startDate) AND "
			+ "(:endDate IS NULL OR i.inspectedDate <= :endDate)")
	Page<InboundRailcar> findWithFilters(@Param("carMark") String carMark, @Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate, Pageable pageable);
}
