package site.railcartracker.site_metrics.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.railcartracker.site_metrics.model.InboundRailcar;
import site.railcartracker.site_metrics.repository.InboundRailcarRepository;


//service for comm between db and controller for abstraction
@Service
public class InboundRailcarService {

	@Autowired
	private InboundRailcarRepository inboundRailcarRepository;

	public InboundRailcar createInboundRailcar(InboundRailcar inboundRailcar) {
		return inboundRailcarRepository.save(inboundRailcar);
	}

	public List<InboundRailcar> getAllInboundRailcars() {
		Iterable<InboundRailcar> allInboundRailcars = inboundRailcarRepository.findAll();
		return (List<InboundRailcar>) allInboundRailcars;
	}

	public InboundRailcar getInboundRailcarById(Integer id) {
		return inboundRailcarRepository.findById(id).orElse(null);

	}

	public InboundRailcar updateInboundRailcar(Integer id, InboundRailcar inboundRailcarDetails) {
		InboundRailcar inboundRailcar = getInboundRailcarById(id);
        inboundRailcar.setCarMark(inboundRailcarDetails.getCarMark());
        inboundRailcar.setCarNumber(inboundRailcarDetails.getCarNumber());
        inboundRailcar.setRepaired(inboundRailcarDetails.isRepaired());
        inboundRailcar.setInspectedDate(inboundRailcarDetails.getInspectedDate());
        inboundRailcar.setBadOrdered(inboundRailcarDetails.isBadOrdered());
        return inboundRailcarRepository.save(inboundRailcar);

	}

	public void deleteInboundRailcar(Integer id) {
		InboundRailcar inboundRailcar = getInboundRailcarById(id);
		inboundRailcarRepository.delete(inboundRailcar);
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
