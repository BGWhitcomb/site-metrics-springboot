package site.railcartracker.site_metrics.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.railcartracker.site_metrics.model.BadOrderedRailcar;
import site.railcartracker.site_metrics.model.InboundRailcar;
import site.railcartracker.site_metrics.repository.BadOrderedRailcarRepository;
import site.railcartracker.site_metrics.repository.InboundRailcarRepository;

//service for comm between db and controller for abstraction
@Service
public class InboundRailcarService {

	@Autowired
	private InboundRailcarRepository inboundRailcarRepository;

	@Autowired
	private BadOrderedRailcarService badOrderedRailcarService;

	@Autowired
	private BadOrderedRailcarRepository badOrderedRailcarRepository;

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
			Optional<BadOrderedRailcar> existing = badOrderedRailcarRepository
					.findByCarMarkAndCarNumberAndInboundRailcar_InboundId(createdInboundRailcar.getCarMark(),
							createdInboundRailcar.getCarNumber(), createdInboundRailcar.getInboundId());
			System.out.println("Finding Existing Bad Order");
			if (existing.isPresent()) {
				// Optionally link the existing bad order to the inbound railcar
				createdInboundRailcar.setBadOrderedRailcar(existing.get());
				System.out.println("Existing bad order, Railcars have been linked.");
			} else {
				// Create and save new BadOrderedRailcar
				BadOrderedRailcar badOrderedRailcar = new BadOrderedRailcar();
				badOrderedRailcar.setInboundRailcar(createdInboundRailcar);
				badOrderedRailcar.setCarMark(createdInboundRailcar.getCarMark());
				badOrderedRailcar.setCarNumber(createdInboundRailcar.getCarNumber());
				badOrderedRailcar.setBadOrderDate(createdInboundRailcar.getInspectedDate());
				badOrderedRailcar = badOrderedRailcarService.createBadOrder(badOrderedRailcar);
				createdInboundRailcar.setBadOrderedRailcar(badOrderedRailcar);
				System.out.println("No Existing Bad Order Found Created Bad Order");
			}
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
	public InboundRailcar updateInboundRailcar(Integer inboundId, InboundRailcar inboundRailcarDetails) {
		InboundRailcar inboundRailcar = getInboundRailcarById(inboundId);
		inboundRailcar.setCarMark(inboundRailcarDetails.getCarMark());
		inboundRailcar.setCarNumber(inboundRailcarDetails.getCarNumber());
		inboundRailcar.setRepaired(inboundRailcarDetails.isRepaired());
		inboundRailcar.setInspectedDate(inboundRailcarDetails.getInspectedDate());
		inboundRailcar.setEmpty(inboundRailcarDetails.isEmpty());
		inboundRailcar.setBadOrdered(inboundRailcarDetails.isBadOrdered());
		return inboundRailcarRepository.save(inboundRailcar);

		// add logic for updating both models?

	}

	// might use later, need to debug to update inbound
//	@Transactional
//	public InboundRailcar updateInboundRailcarAndBadOrder(Integer inboundId, InboundRailcar inboundRailcarDetails) {
//		// Update InboundRailcar
//		InboundRailcar updatedInboundRailcar = updateInboundRailcar(inboundId, inboundRailcarDetails);
//
//		// Handle the bad order logic
//		if (updatedInboundRailcar.isBadOrdered()) {
//			BadOrderedRailcar existingBadOrder = badOrderedRailcarService.getBadOrderByInboundRailcarId(inboundId);
//			
//			
//			badOrderedRailcarService.updateBadOrderAndInboundRailcar(existingBadOrder.getBadOrderId(), existingBadOrder);
//
//			if (existingBadOrder != null) {
//				// Update existing bad order
//				existingBadOrder.setCarMark(updatedInboundRailcar.getCarMark());
//				existingBadOrder.setCarNumber(updatedInboundRailcar.getCarNumber());
//				existingBadOrder.setBadOrderDate(updatedInboundRailcar.getInspectedDate());
//
//				System.out.println("Bad order: " + existingBadOrder + " is updated");
//
//				// Update the BadOrderedRailcar in the service
//				badOrderedRailcarService.updateBadOrder(existingBadOrder.getBadOrderId(), existingBadOrder);
//				
//			} //else {
////				// Create new bad order
////				BadOrderedRailcar newBadOrder = new BadOrderedRailcar();
////				newBadOrder.setInboundRailcar(updatedInboundRailcar);
////				newBadOrder.setCarMark(updatedInboundRailcar.getCarMark());
////				newBadOrder.setCarNumber(updatedInboundRailcar.getCarNumber());
////				newBadOrder.setBadOrderDate(updatedInboundRailcar.getInspectedDate());
////				
////				System.out.println("railcar " + newBadOrder + "created at " + LocalDateTime.now());
////				
////			}
//		} else {
//			// If no longer bad ordered, delete the associated BadOrderedRailcar
//			badOrderedRailcarService.deleteBadOrderByInboundId(inboundId);
//			
//			System.out.println("Inbound Service - deleted bad order, inbound inspection record no longer associated with a bad order.");
//			
//		}
//
//		return updatedInboundRailcar;
//	}

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

	@Transactional
	public void deleteInboundRailcars(List<Integer> inboundIds) {
		for (Integer id : inboundIds) {
			deleteInboundRailcar(id);
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
