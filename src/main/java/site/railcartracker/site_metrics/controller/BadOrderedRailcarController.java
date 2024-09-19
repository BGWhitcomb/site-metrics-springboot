package site.railcartracker.site_metrics.controller;

import java.time.LocalDate;
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
@RequestMapping("/bad-orders")
public class BadOrderedRailcarController {

	@Autowired
	private BadOrderedRailcarService badOrderedRailcarService;

	@Autowired
	private InboundRailcarService inboundRailcarService;

//	@PostMapping
//	public ResponseEntity<BadOrderedRailcar> createBadOrderedRailcar(@RequestBody BadOrderedRailcar badOrderedRailcar) {
//		// Fetch the associated InboundRailcar based on the inboundId or other
//		// identifier
//		InboundRailcar inboundRailcar = inboundRailcarService
//				.getInboundRailcarById(badOrderedRailcar.getInboundRailcar().getInboundId());
//
//		if (inboundRailcar == null) {
//			// If inbound railcar doesn't exist, return error response
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//		}
//
//		// Set isBadOrdered to true for the InboundRailcar
//		inboundRailcar.setBadOrdered(true);
//
//		// Link the BadOrderedRailcar to the InboundRailcar
//		badOrderedRailcar.setInboundRailcar(inboundRailcar);
//
//		// Save the BadOrderedRailcar (which will also save InboundRailcar due to
//		// cascade = ALL)
//		BadOrderedRailcar createdBadOrder = badOrderedRailcarService.createBadOrder(badOrderedRailcar);
//
//		// Return a response entity with the newly created BadOrderedRailcar object
//		return ResponseEntity.status(HttpStatus.CREATED).body(createdBadOrder);
//	}

	@GetMapping
	public ResponseEntity<List<BadOrderedRailcar>> getAllBadOrderedRailcars(
			@RequestBody BadOrderedRailcar badOrderedRailcar) {
		List<BadOrderedRailcar> allBadOrders = badOrderedRailcarService.getAllBadOrders();
		return ResponseEntity.status(HttpStatus.OK).body(allBadOrders);
		// method for retrieving all bad orders
	}

	@GetMapping("/{id}")
	public ResponseEntity<BadOrderedRailcar> getBadOrderedRailcarById(@PathVariable int id) {
		BadOrderedRailcar idOfBadOrder = badOrderedRailcarService.getBadOrderById(id);
		return ResponseEntity.status(HttpStatus.OK).body(idOfBadOrder);
		// method for retrieving specific bad order by id? not sure if needed
	}

	@PutMapping("/{id}")
	public ResponseEntity<BadOrderedRailcar> updateBadOrderedRailcar(@PathVariable int id,
			@RequestBody BadOrderedRailcar badOrderedRailcarDetails) {
		BadOrderedRailcar updatedBadOrder = badOrderedRailcarService.updateBadOrder(id, badOrderedRailcarDetails);
		return ResponseEntity.status(HttpStatus.OK).body(updatedBadOrder);
		// method for updating bad order entries
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBadOrderedRailcar(@PathVariable int id) {
		badOrderedRailcarService.deleteBadOrderByInboundId(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		// method for deleting bad order entries
	}

//	@GetMapping("/date-range")
//	public ResponseEntity<List<InboundRailcar>> getInspectionsByDateRange(@RequestParam LocalDate startDate,
//			@RequestParam LocalDate endDate) {
//		// method for retrieving inspections by date range
//	}

//	@GetMapping("/car/{carNumber}")
//	public ResponseEntity<BadOrderedRailcar> getBadOrderByCarNumber(@PathVariable int carNumber) {
//		return null;
//		// method for car number search, returns all matching car numbers in the
//		// database...add car mark too?
//	}
//
//	@GetMapping("/car/{repairedDate}")
//	public ResponseEntity<InboundRailcar> getBadOrderByDate(@PathVariable LocalDate repairedDate) {
//		return null;
//		//method for repair date search
//
//	}

}
