package site.railcartracker.site_metrics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import site.railcartracker.site_metrics.model.BadOrderedRailcar;
import site.railcartracker.site_metrics.model.InboundRailcar;
import site.railcartracker.site_metrics.repository.BadOrderedRailcarRepository;
import site.railcartracker.site_metrics.repository.InboundRailcarRepository;

@Service
public class BadOrderedRailcarService {

	@Autowired
	private BadOrderedRailcarRepository badOrderedRailcarRepository;
	
	@Autowired
	private InboundRailcarRepository inboundRailcarRepository;
	

	public BadOrderedRailcar createBadOrder(BadOrderedRailcar badOrderedRailcar) {
		return badOrderedRailcarRepository.save(badOrderedRailcar);

	}

	public List<BadOrderedRailcar> getAllBadOrders() {
		Iterable<BadOrderedRailcar> allBadOrders = badOrderedRailcarRepository.findAll();
		return (List<BadOrderedRailcar>) allBadOrders;

	}
	
	public List<BadOrderedRailcar> getActiveBadOrders(boolean isActive) {
		//returns a list of bad order railcars that have not been assigned a repair date
		List<BadOrderedRailcar> activeBadOrders = badOrderedRailcarRepository.findByIsActive(isActive);
		return (List<BadOrderedRailcar>) activeBadOrders;
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
			existingBadOrder.setBadOrderDescription(badOrderedRailcarDetails.getBadOrderDescription());
			existingBadOrder.setRepairedDate(badOrderedRailcarDetails.getRepairedDate());
			
			//repair date population makes isActive = false, used for filtering in the front end for active bad order tracking

			// Save and return the updated BadOrderedRailcar
			return badOrderedRailcarRepository.save(existingBadOrder);
		} else {
			throw new EntityNotFoundException("BadOrderedRailcar not found with id: " + id);
		}
	}
	
	@Transactional
    public BadOrderedRailcar updateBadOrderAndInboundRailcar(int badOrderId, BadOrderedRailcar badOrderedRailcarDetails) {

        // Fetch the existing BadOrderedRailcar by its ID
        BadOrderedRailcar existingBadOrder = badOrderedRailcarRepository.findById(badOrderId)
                .orElseThrow(() -> new EntityNotFoundException("BadOrderedRailcar not found with id: " + badOrderId));

        // Fetch the associated InboundRailcar
        InboundRailcar inboundRailcar = existingBadOrder.getInboundRailcar();

        if (inboundRailcar == null) {
            throw new EntityNotFoundException("InboundRailcar not found for BadOrderedRailcar id: " + badOrderId);
        }

        // Update fields in BadOrderedRailcar
        existingBadOrder.setCarMark(badOrderedRailcarDetails.getCarMark());
        existingBadOrder.setCarNumber(badOrderedRailcarDetails.getCarNumber());
        existingBadOrder.setBadOrderDate(badOrderedRailcarDetails.getBadOrderDate());
        existingBadOrder.setBadOrderDescription(badOrderedRailcarDetails.getBadOrderDescription());
        existingBadOrder.setRepairedDate(badOrderedRailcarDetails.getRepairedDate());

        // Update related fields in InboundRailcar
        inboundRailcar.setCarMark(badOrderedRailcarDetails.getCarMark());
        inboundRailcar.setCarNumber(badOrderedRailcarDetails.getCarNumber());
        
        //1to1 relation with bad order date to inspection date.. i will most likely change this
        inboundRailcar.setInspectedDate(badOrderedRailcarDetails.getBadOrderDate());
   

        //Save both entities
        inboundRailcarRepository.save(inboundRailcar);
        return badOrderedRailcarRepository.save(existingBadOrder);
    }
//	change this to just delete by bo id..
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
			System.out.println("No BadOrderedRailcar found for " + inboundId);
		}
	}

}
