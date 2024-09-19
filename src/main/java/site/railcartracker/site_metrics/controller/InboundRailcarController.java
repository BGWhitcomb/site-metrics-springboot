package site.railcartracker.site_metrics.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.railcartracker.site_metrics.model.BadOrderedRailcar;
import site.railcartracker.site_metrics.model.InboundRailcar;
import site.railcartracker.site_metrics.service.BadOrderedRailcarService;
import site.railcartracker.site_metrics.service.InboundRailcarService;

@RestController
@RequestMapping("/inspections")
public class InboundRailcarController {

	@Autowired
	private InboundRailcarService inboundRailcarService;

	@Autowired
	private BadOrderedRailcarService badOrderedRailcarService;

	@PostMapping
	public ResponseEntity<InboundRailcar> createInboundRailcar(@RequestBody InboundRailcar inboundRailcar) {
		// Create the InboundRailcar
		InboundRailcar createdInboundRailcar = inboundRailcarService.createInboundRailcar(inboundRailcar);

		LocalDateTime dateCreated = LocalDateTime.now();
		if (createdInboundRailcar != null) {
			System.out.println("Railcar " + createdInboundRailcar + " added successfully at " + dateCreated);
		}

		// Check if InboundRailcar is bad ordered
		if (createdInboundRailcar.isBadOrdered()) {
			// Create BadOrderedRailcar instance if bad order flag is true
			BadOrderedRailcar badOrderedRailcar = new BadOrderedRailcar();

			// Populate BadOrderedRailcar details from InboundRailcar
			badOrderedRailcar.setInboundRailcar(createdInboundRailcar); // Set the relationship
			badOrderedRailcar.setCarMark(createdInboundRailcar.getCarMark()); // Copy over common properties
			badOrderedRailcar.setCarNumber(createdInboundRailcar.getCarNumber());
			badOrderedRailcar.setBadOrderDate(createdInboundRailcar.getInspectedDate());

			// Save BadOrderedRailcar
			BadOrderedRailcar createdBadOrder = badOrderedRailcarService.createBadOrder(badOrderedRailcar);

			// Set the created BadOrderedRailcar in the InboundRailcar (if needed)
			createdInboundRailcar.setBadOrderedRailcar(createdBadOrder);
		}

		// Return the response with the created InboundRailcar (now potentially linked
		// with BadOrderedRailcar)
		return ResponseEntity.status(HttpStatus.CREATED).body(createdInboundRailcar);
	}

	@GetMapping
	public ResponseEntity<List<InboundRailcar>> getAllInboundRailcars(@RequestBody InboundRailcar inboundRailcar) {
		List<InboundRailcar> allInboundRailcars = inboundRailcarService.getAllInboundRailcars();
		return ResponseEntity.status(HttpStatus.OK).body(allInboundRailcars);
		// method for retrieving all inbound inspections
	}

	@GetMapping("/{inboundId}")
	public ResponseEntity<InboundRailcar> getInboundRailcarById(@PathVariable Integer inboundId) {
		InboundRailcar idOfInboundRailcar = inboundRailcarService.getInboundRailcarById(inboundId);
		return ResponseEntity.status(HttpStatus.OK).body(idOfInboundRailcar);
		// method for retrieving specific railcar by id? not sure if needed
	}

	@PutMapping("/{inboundId}")
	public ResponseEntity<InboundRailcar> updateRailcar(@PathVariable Integer inboundId,
			@RequestBody InboundRailcar inboundRailcarDetails) {

		//Update the InboundRailcar
		InboundRailcar updatedInboundRailcar = inboundRailcarService.updateInboundRailcar(inboundId,
				inboundRailcarDetails);

		//Check if the InboundRailcar is marked as bad ordered
		if (updatedInboundRailcar.isBadOrdered()) {
			// Look for an existing BadOrderedRailcar by InboundRailcar ID
			System.out.println("Looking for bad order..");

			BadOrderedRailcar existingBadOrder = badOrderedRailcarService.getBadOrderByInboundRailcarId(inboundId);

			if (existingBadOrder != null) {
				// Update the existing BadOrderedRailcar
				existingBadOrder.setCarMark(updatedInboundRailcar.getCarMark());
				existingBadOrder.setCarNumber(updatedInboundRailcar.getCarNumber());
				existingBadOrder.setBadOrderDate(updatedInboundRailcar.getInspectedDate());

				System.out.println("Bad order: " + existingBadOrder + " is updated");

				// Update the BadOrderedRailcar in the service
				badOrderedRailcarService.updateBadOrder(existingBadOrder.getBadOrderId(), existingBadOrder);
			} else {
				// Create a new BadOrderedRailcar if none exists
				BadOrderedRailcar newBadOrder = new BadOrderedRailcar();
				newBadOrder.setInboundRailcar(updatedInboundRailcar); // Set the relationship
				newBadOrder.setCarMark(updatedInboundRailcar.getCarMark());
				newBadOrder.setCarNumber(updatedInboundRailcar.getCarNumber());
				newBadOrder.setBadOrderDate(updatedInboundRailcar.getInspectedDate());

				System.out.println("New bad order created: " + newBadOrder);

				// Save the new BadOrderedRailcar
				badOrderedRailcarService.createBadOrder(newBadOrder);
			}
		} else {
			// If the InboundRailcar is no longer bad ordered, delete the associated
			// BadOrderedRailcar
			System.out.println("Attempting to delete bad order...");
			badOrderedRailcarService.deleteBadOrderByInboundId(inboundId);
		}

		// Return the response with the updated InboundRailcar
		return ResponseEntity.status(HttpStatus.OK).body(updatedInboundRailcar);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteInboundRailcar(@PathVariable Integer id) {
		inboundRailcarService.deleteInboundRailcar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

//	@GetMapping("/date-range")
//	public ResponseEntity<List<InboundRailcar>> getInspectionsByDateRange(@RequestParam LocalDate startDate,
//			@RequestParam LocalDate endDate) {
//				List<InboundRailcar> railcarsInspectedBetweenDates = inboundRailcarService.getInspectionsByDateRange(startDate, endDate);
//				return ResponseEntity.status(HttpStatus.OK).body(railcarsInspectedBetweenDates);
//		// method for retrieving inspections by date range
//	}
//
//	@GetMapping("/car/{carNumber}")
//	public ResponseEntity<InboundRailcar> getInspectionsByCarNumber(@PathVariable Integer carNumber) {
//		InboundRailcar carNumberResults = inboundRailcarService.getInspectionsByCarNumber(carNumber);
//		return ResponseEntity.status(HttpStatus.OK).body(carNumberResults);
//		// method for car number search, returns all matching car numbers in the
//		// database...add car mark too?
//	}
//
//	@GetMapping("/car/{inspectedDate}")
//	public ResponseEntity<List<InboundRailcar>> getInspectionsByDate(@PathVariable LocalDate inspectedDate) {
//		List<InboundRailcar> railcarsInspectedOnDate = inboundRailcarService.getInspectionsByDate(inspectedDate);
//		return ResponseEntity.status(HttpStatus.OK).body(railcarsInspectedOnDate);
//		//method for inspection date search, to get all inspections on a specific date
//
//	}

}
