package site.railcartracker.site_metrics.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.railcartracker.site_metrics.model.BadOrderedRailcar;
import site.railcartracker.site_metrics.model.InboundRailcar;
import site.railcartracker.site_metrics.repository.InboundRailcarRepository;

//service for comm between db and controller for abstraction
@Service
public class InboundRailcarService {

	@Autowired
	private InboundRailcarRepository inboundRailcarRepository;

	@Autowired
	private BadOrderedRailcarService badOrderedRailcarService;

	@Transactional
	public InboundRailcar createInboundRailcar(InboundRailcar inboundRailcar) {
		// Create the InboundRailcar
		InboundRailcar createdInboundRailcar = inboundRailcarRepository.save(inboundRailcar);

		// Log the creation timestamp
		LocalDateTime dateCreated = LocalDateTime.now();
		if (createdInboundRailcar != null) {
			System.out.println("Railcar " + createdInboundRailcar + " added successfully at " + dateCreated);
		}

		// Check if InboundRailcar is bad ordered and create the related
		// BadOrderedRailcar
		if (createdInboundRailcar.isBadOrdered()) {
			BadOrderedRailcar badOrderedRailcar = new BadOrderedRailcar();

			// Populate BadOrderedRailcar details from InboundRailcar
			badOrderedRailcar.setInboundRailcar(createdInboundRailcar); // Set the relationship
			badOrderedRailcar.setCarMark(createdInboundRailcar.getCarMark());
			badOrderedRailcar.setCarNumber(createdInboundRailcar.getCarNumber());
			
			// 1to1 rel with badorder and inspected date, might change this later
			badOrderedRailcar.setBadOrderDate(createdInboundRailcar.getInspectedDate());

			// Save BadOrderedRailcar using its own service layer
			BadOrderedRailcar createdBadOrder = badOrderedRailcarService.createBadOrder(badOrderedRailcar);

			// Optionally set the created BadOrderedRailcar in the InboundRailcar
			createdInboundRailcar.setBadOrderedRailcar(createdBadOrder);
		}

		return createdInboundRailcar;
	}

	public List<InboundRailcar> getAllInboundRailcars() {
		Iterable<InboundRailcar> allInboundRailcars = inboundRailcarRepository.findAll();
		return (List<InboundRailcar>) allInboundRailcars;
	}

	public InboundRailcar getInboundRailcarById(Integer inboundId) {
		return inboundRailcarRepository.findById(inboundId).orElse(null);

	}
	
	@Transactional
	private InboundRailcar updateInboundRailcar(Integer inboundId, InboundRailcar inboundRailcarDetails) {
		InboundRailcar inboundRailcar = getInboundRailcarById(inboundId);
		inboundRailcar.setCarMark(inboundRailcarDetails.getCarMark());
		inboundRailcar.setCarNumber(inboundRailcarDetails.getCarNumber());
		inboundRailcar.setRepaired(inboundRailcarDetails.isRepaired());
		inboundRailcar.setInspectedDate(inboundRailcarDetails.getInspectedDate());
		inboundRailcar.setBadOrdered(inboundRailcarDetails.isBadOrdered());
		return inboundRailcarRepository.save(inboundRailcar);

	}

	@Transactional
	public InboundRailcar updateInboundRailcarAndBadOrder(Integer inboundId, InboundRailcar inboundRailcarDetails) {
		// Update InboundRailcar
		InboundRailcar updatedInboundRailcar = updateInboundRailcar(inboundId, inboundRailcarDetails);

		// Handle the bad order logic
		if (updatedInboundRailcar.isBadOrdered()) {
			BadOrderedRailcar existingBadOrder = badOrderedRailcarService.getBadOrderByInboundRailcarId(inboundId);

			if (existingBadOrder != null) {
				// Update existing bad order
				badOrderedRailcarService.updateBadOrder(existingBadOrder.getBadOrderId(), existingBadOrder);
			} else {
				// Create new bad order
				BadOrderedRailcar newBadOrder = new BadOrderedRailcar();
				newBadOrder.setInboundRailcar(updatedInboundRailcar); // Set the relationship
				newBadOrder.setCarMark(updatedInboundRailcar.getCarMark());
				newBadOrder.setCarNumber(updatedInboundRailcar.getCarNumber());
				newBadOrder.setBadOrderDate(updatedInboundRailcar.getInspectedDate());
			}
		} else {
			// If no longer bad ordered, delete the associated BadOrderedRailcar
			badOrderedRailcarService.deleteBadOrderByInboundId(inboundId);
		}

		return updatedInboundRailcar;
	}

	@Transactional
	public void deleteInboundRailcar(Integer inboundId) {
		InboundRailcar inboundRailcar = getInboundRailcarById(inboundId);
		
		if (inboundRailcar != null) {
			inboundRailcarRepository.delete(inboundRailcar);
			System.out.println(inboundRailcar + " has been deleted.");
		}
		if (inboundRailcar.isBadOrdered()) {
			BadOrderedRailcar existingBadOrder = badOrderedRailcarService.getBadOrderByInboundRailcarId(inboundId);
			badOrderedRailcarService.deleteBadOrderByInboundId(inboundId);
			System.out.println(existingBadOrder + " with inboundId " + inboundId + " deleted.");
		} else {
			
			System.out.println(" No Bad Ordered Railcars assigned to " + inboundId);
		}
	}

//	public List<InboundRailcar> getInspectionsByDateRange(LocalDate startDate, LocalDate endDate) {
//		return inboundRailcarRepository.findByInspectedDateBetween(startDate, endDate);
//	}
//
//	public InboundRailcar getInspectionsByCarNumber(Integer carNumber) {
//		return inboundRailcarRepository.findByCarNumber(carNumber);
//
//	}
//
//	public List<InboundRailcar> getInspectionsByDate(LocalDate inspectedDate) {
//		return inboundRailcarRepository.findByInspectedDate(inspectedDate);
//
//	}

}
