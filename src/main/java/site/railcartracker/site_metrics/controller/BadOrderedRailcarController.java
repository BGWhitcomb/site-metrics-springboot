package site.railcartracker.site_metrics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.railcartracker.site_metrics.model.BadOrderedRailcar;
import site.railcartracker.site_metrics.service.BadOrderedRailcarService;

@RestController
@RequestMapping("/bad-orders")
public class BadOrderedRailcarController {

	@Autowired
	private BadOrderedRailcarService badOrderedRailcarService;

	@GetMapping
	public ResponseEntity<List<BadOrderedRailcar>> getActiveBadOrderedRailcars(BadOrderedRailcar badOrderedRailcar) {
		// method for retrieving all active bad orders set to true, this is what will
		// populate the table on page load
		List<BadOrderedRailcar> activeBadOrders = badOrderedRailcarService
				.getActiveBadOrders(badOrderedRailcar.isActive());
		return ResponseEntity.status(HttpStatus.OK).body(activeBadOrders);
	}

	@GetMapping("/all")
	public ResponseEntity<List<BadOrderedRailcar>> getAllBadOrderedRailcars() {
		// method for retrieving all bad orders
		List<BadOrderedRailcar> allBadOrders = badOrderedRailcarService.getAllBadOrders();
		return ResponseEntity.status(HttpStatus.OK).body(allBadOrders);
	}

	@GetMapping("/{badOrderId}")
	public ResponseEntity<BadOrderedRailcar> getBadOrderedRailcarById(@PathVariable Integer badOrderId) {
		// method for retrieving specific bad order by id
		BadOrderedRailcar badOrderById = badOrderedRailcarService.getBadOrderById(badOrderId);
		return ResponseEntity.status(HttpStatus.OK).body(badOrderById);
	}

	@PutMapping("/{badOrderId}")
	public ResponseEntity<BadOrderedRailcar> updateBadOrderedRailcar(@PathVariable Integer badOrderId,
			@RequestBody BadOrderedRailcar badOrderedRailcarDetails) {
		// method for updating bad order entries and inspections simultaneously
		BadOrderedRailcar updatedBadOrder = badOrderedRailcarService.updateBadOrderAndInboundRailcar(badOrderId,
				badOrderedRailcarDetails);
		return ResponseEntity.status(HttpStatus.OK).body(updatedBadOrder);

	}
// Change this plsssssssssss, i'll need to change this to search bo id.
	@DeleteMapping("/{badOrderId}")
	public ResponseEntity<Void> deleteBadOrderedRailcar(@PathVariable Integer badOrderId) {
		// method for deleting bad order entries, will need to add logic to delete
		// inbound railcar simultaneously
		badOrderedRailcarService.deleteBadOrderByInboundId(badOrderId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
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
