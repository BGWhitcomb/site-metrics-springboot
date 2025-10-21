package site.railcartracker.site_metrics.controller;

import java.util.ArrayList;

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

import site.railcartracker.site_metrics.model.InboundRailcar;
import site.railcartracker.site_metrics.service.InboundRailcarService;

@RestController
@RequestMapping("/inspections")
public class InboundRailcarController {

	@Autowired
	private InboundRailcarService inboundRailcarService;

	@PostMapping
	public ResponseEntity<List<InboundRailcar>> createMultipleInboundRailcars(
			@RequestBody List<InboundRailcar> inboundRailcars) {
		List<InboundRailcar> createdRailcars = new ArrayList<>();

		for (InboundRailcar railcar : inboundRailcars) {
			InboundRailcar createdRailcar = inboundRailcarService.createInboundRailcar(railcar);
			createdRailcars.add(createdRailcar);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(createdRailcars);
	}

	@GetMapping
	public ResponseEntity<List<InboundRailcar>> getAllInboundRailcars() {
		// method for retrieving all inbound inspections
		List<InboundRailcar> allInboundRailcars = inboundRailcarService.getAllInboundRailcars();
		return ResponseEntity.status(HttpStatus.OK).body(allInboundRailcars);
	}

	@GetMapping("/{inboundId}")
	public ResponseEntity<InboundRailcar> getInboundRailcarById(@PathVariable Integer inboundId) {
		InboundRailcar inboundRailcarById = inboundRailcarService.getInboundRailcarById(inboundId);
		return ResponseEntity.status(HttpStatus.OK).body(inboundRailcarById);
		// method for retrieving specific railcar by id
	}

	@PutMapping("/{inboundId}")
	public ResponseEntity<InboundRailcar> updateInboundRailcar(@PathVariable Integer inboundId,
			@RequestBody InboundRailcar inboundRailcarDetails) {
		// method for updating inbound railcar and also checking for bad order existence
		// and updating simultaneously
		InboundRailcar updatedInboundRailcar = inboundRailcarService.updateInboundRailcar(inboundId,
				inboundRailcarDetails);
		return ResponseEntity.status(HttpStatus.OK).body(updatedInboundRailcar);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteInboundRailcars(@RequestBody List<Integer> inboundIds) {
		inboundRailcarService.deleteInboundRailcars(inboundIds);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
