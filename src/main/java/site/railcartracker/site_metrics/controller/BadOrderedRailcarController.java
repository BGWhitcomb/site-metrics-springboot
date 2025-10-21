package site.railcartracker.site_metrics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<BadOrderedRailcar>> getBadOrderedRailcars(
			@org.springframework.web.bind.annotation.RequestParam(value = "ref", required = false) String ref) {
		List<BadOrderedRailcar> badOrders;
		if ("isActive".equalsIgnoreCase(ref)) {
			badOrders = badOrderedRailcarService.getActiveBadOrders(true);
		} else {
			badOrders = badOrderedRailcarService.getAllBadOrders();
		}
		return ResponseEntity.status(HttpStatus.OK).body(badOrders);
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

	@DeleteMapping("/{badOrderId}")
	public ResponseEntity<Void> deleteBadOrderedRailcar(@PathVariable Integer badOrderId) {
		// method for deleting bad order entries, will need to add logic to delete
		// inbound railcar simultaneously
		badOrderedRailcarService.deleteBadOrderByInboundId(badOrderId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
