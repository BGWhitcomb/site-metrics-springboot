package site.railcartracker.site_metrics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import site.railcartracker.site_metrics.model.BadOrderedRailcar;
import site.railcartracker.site_metrics.repository.BadOrderedRailcarRepository;

@Service
public class BadOrderedRailcarService {

	@Autowired
	private BadOrderedRailcarRepository badOrderedRailcarRepository;

//	@Autowired
//	private InboundRailcarRepository inboundRailcarRepository;
//	
//	@Autowired
//	private InboundRailcar inboundRailcar;

	public BadOrderedRailcar createBadOrder(BadOrderedRailcar badOrderedRailcar) {
		return badOrderedRailcarRepository.save(badOrderedRailcar);

	}

	public List<BadOrderedRailcar> getAllBadOrders() {
		Iterable<BadOrderedRailcar> allBadOrders = badOrderedRailcarRepository.findAll();
		return (List<BadOrderedRailcar>) allBadOrders;

	}

	public BadOrderedRailcar getBadOrderById(Integer id) {
		return badOrderedRailcarRepository.findById(id).orElse(null);

	}

	public BadOrderedRailcar getBadOrderByInboundRailcarId(Integer inboundId) {
		return badOrderedRailcarRepository.findByInboundRailcar_InboundId(inboundId);
	}
	
	@Transactional
	public BadOrderedRailcar updateBadOrder(Integer id, BadOrderedRailcar badOrderedRailcarDetails) {
		// Retrieve the existing BadOrderedRailcar using the bad order ID
		BadOrderedRailcar existingBadOrder = getBadOrderById(id);

		if (existingBadOrder != null) {
			// Update fields
			existingBadOrder.setCarMark(badOrderedRailcarDetails.getCarMark());
			existingBadOrder.setCarNumber(badOrderedRailcarDetails.getCarNumber());
			existingBadOrder.setBadOrderDate(badOrderedRailcarDetails.getBadOrderDate());
			existingBadOrder.setBadOrderReason(badOrderedRailcarDetails.getBadOrderReason());
			existingBadOrder.setRepairedDate(badOrderedRailcarDetails.getRepairedDate());

			// Save and return the updated BadOrderedRailcar
			return badOrderedRailcarRepository.save(existingBadOrder);
		} else {
			throw new EntityNotFoundException("BadOrderedRailcar not found with id: " + id);
		}
	}
	
	@Transactional
	public void deleteBadOrderByInboundId(Integer inboundId) {
		// Find the bad order by the inbound railcar ID
		BadOrderedRailcar badOrder = badOrderedRailcarRepository.findByInboundRailcar_InboundId(inboundId);

		// If the bad order exists, delete it
		if (badOrder != null) {
			System.out.println("Fetched bad order: " + badOrder);
			badOrderedRailcarRepository.delete(badOrder);
			System.out.println("Service - BadOrderedRailcar with inboundId " + inboundId + " deleted.");
		} else {
			System.out.println("No BadOrderedRailcar found for inboundId " + inboundId);
		}
	}

}
