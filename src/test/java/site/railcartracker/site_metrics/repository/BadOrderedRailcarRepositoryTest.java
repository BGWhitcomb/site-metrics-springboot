//package site.railcartracker.site_metrics.repository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import site.railcartracker.site_metrics.model.BadOrderedRailcar;
//import site.railcartracker.site_metrics.model.InboundRailcar;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//public class BadOrderedRailcarRepositoryTest {
//
//	@Autowired
//	private BadOrderedRailcarRepository badOrderedRailcarRepository;
//
//	@Autowired
//	private InboundRailcarRepository inboundRailcarRepository;
//
//	private InboundRailcar inboundRailcar;
//	private BadOrderedRailcar badOrderedRailcar;
//
//	@BeforeEach
//	public void setUp() {
//		// Setting up sample inbound railcar
//		LocalDate inspectedDate = LocalDate.now();
//		inboundRailcar = new InboundRailcar();
//		inboundRailcar.setCarMark("TEST");
//		inboundRailcar.setCarNumber(123456);
//		inboundRailcar.setInspectedDate(inspectedDate);
//
//		// Save inbound railcar to use its inboundId for bad orders
//		inboundRailcarRepository.save(inboundRailcar);
//
//		// Setting up sample bad ordered railcar
//		LocalDate badOrderDate = LocalDate.now();
//		badOrderedRailcar = new BadOrderedRailcar();
//		badOrderedRailcar.setCarMark("TEST");
//		badOrderedRailcar.setCarNumber(123456);
//		badOrderedRailcar.setBadOrderDate(badOrderDate);
//		badOrderedRailcar.setBadOrderReason("Brake issue");
//		badOrderedRailcar.setActive(true);
//		badOrderedRailcar.setInboundRailcar(inboundRailcar);
//
//		// Save bad ordered railcar
//		badOrderedRailcarRepository.save(badOrderedRailcar);
//	}
//
//	@Test
//	public void testFindByInboundRailcar_InboundId() {
//		// Act
//		BadOrderedRailcar foundBadOrder = badOrderedRailcarRepository
//				.findByInboundRailcar_InboundId(inboundRailcar.getInboundId());
//
//		// Assert
//		assertNotNull(foundBadOrder);
//		assertEquals(inboundRailcar.getInboundId(), foundBadOrder.getInboundRailcar().getInboundId());
//		assertEquals("TEST", foundBadOrder.getCarMark());
//	}
//
//	@Test
//	public void testFindByIsActive() {
//		// Act
//		List<BadOrderedRailcar> activeBadOrders = badOrderedRailcarRepository.findByIsActive(true);
//
//		// Assert
//		assertNotNull(activeBadOrders);
//		assertEquals(1, activeBadOrders.size());
//		assertTrue(activeBadOrders.get(0).isActive());
//	}
//
//	@Test
//	public void testFindByIsActive_NoActiveBadOrders() {
//		// Arrange
//		// Deactivate the existing bad order railcar
//		badOrderedRailcar.setActive(false);
//		badOrderedRailcarRepository.save(badOrderedRailcar);
//
//		// Act
//		List<BadOrderedRailcar> activeBadOrders = badOrderedRailcarRepository.findByIsActive(true);
//
//		// Assert
//		assertTrue(activeBadOrders.isEmpty());
//	}
//}
