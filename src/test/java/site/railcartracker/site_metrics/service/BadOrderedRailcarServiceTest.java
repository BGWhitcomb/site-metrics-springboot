package site.railcartracker.site_metrics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import site.railcartracker.site_metrics.model.BadOrderedRailcar;
import site.railcartracker.site_metrics.model.InboundRailcar;
import site.railcartracker.site_metrics.repository.BadOrderedRailcarRepository;
import site.railcartracker.site_metrics.repository.InboundRailcarRepository;

@SpringBootTest
public class BadOrderedRailcarServiceTest {

	@InjectMocks
	private BadOrderedRailcarService badOrderedRailcarService;

	@Mock
	private BadOrderedRailcarRepository badOrderedRailcarRepository;

	@Mock
	private InboundRailcarRepository inboundRailcarRepository;

	private BadOrderedRailcar badOrderedRailcar;
	private InboundRailcar inboundRailcar;
	
	LocalDate badOrderDate = LocalDate.now();
	

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		// Initialize test data
		badOrderedRailcar = new BadOrderedRailcar();
		badOrderedRailcar.setBadOrderId((long) 1);
		badOrderedRailcar.setCarMark("TEST");
		badOrderedRailcar.setCarNumber(123456);
		badOrderedRailcar.setBadOrderDate(badOrderDate);
		badOrderedRailcar.setBadOrderDescription("Brake Issue");

		inboundRailcar = new InboundRailcar();
		inboundRailcar.setInboundId((long) 1);
		inboundRailcar.setCarMark("TEST");
		inboundRailcar.setCarNumber(123456);
		badOrderedRailcar.setInboundRailcar(inboundRailcar);
	}

	@Test
    public void testCreateBadOrder() {
        // Arrange
        when(badOrderedRailcarRepository.save(badOrderedRailcar)).thenReturn(badOrderedRailcar);
        
        // Act
        BadOrderedRailcar createdBadOrder = badOrderedRailcarService.createBadOrder(badOrderedRailcar);
        
        // Assert
        assertNotNull(createdBadOrder);
        assertEquals("TEST", createdBadOrder.getCarMark());
        verify(badOrderedRailcarRepository, times(1)).save(badOrderedRailcar);
    }

	@Test
	public void testGetAllBadOrders() {
		// Arrange
		List<BadOrderedRailcar> badOrdersList = new ArrayList<>();
		badOrdersList.add(badOrderedRailcar);
		when(badOrderedRailcarRepository.findAll()).thenReturn(badOrdersList);

		// Act
		List<BadOrderedRailcar> result = badOrderedRailcarService.getAllBadOrders();

		// Assert
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		verify(badOrderedRailcarRepository, times(1)).findAll();
	}

	@Test
	public void testGetActiveBadOrders() {
		// Arrange
		List<BadOrderedRailcar> activeBadOrders = new ArrayList<>();
		activeBadOrders.add(badOrderedRailcar);
		when(badOrderedRailcarRepository.findByIsActive(true)).thenReturn(activeBadOrders);

		// Act
		List<BadOrderedRailcar> result = badOrderedRailcarService.getActiveBadOrders(true);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		verify(badOrderedRailcarRepository, times(1)).findByIsActive(true);
	}

	@Test
    public void testGetBadOrderById() {
        // Arrange
        when(badOrderedRailcarRepository.findById(1)).thenReturn(Optional.of(badOrderedRailcar));
        
        // Act
        BadOrderedRailcar foundBadOrder = badOrderedRailcarService.getBadOrderById(1);
        
        // Assert
        assertNotNull(foundBadOrder);
        assertEquals("TEST", foundBadOrder.getCarMark());
        verify(badOrderedRailcarRepository, times(1)).findById(1);
    }

	@Test
	public void testUpdateBadOrder() {
		// Arrange
		BadOrderedRailcar updatedDetails = new BadOrderedRailcar();
		updatedDetails.setCarMark("TSET");
		updatedDetails.setCarNumber(654321);
		updatedDetails.setBadOrderDescription("Wheel Issue");

		when(badOrderedRailcarRepository.findById(1)).thenReturn(Optional.of(badOrderedRailcar));
		when(badOrderedRailcarRepository.save(badOrderedRailcar)).thenReturn(badOrderedRailcar);

		// Act
		BadOrderedRailcar updatedBadOrder = badOrderedRailcarService.updateBadOrder(1, updatedDetails);

		// Assert
		assertNotNull(updatedBadOrder);
		assertEquals("TSET", updatedBadOrder.getCarMark());
		verify(badOrderedRailcarRepository, times(1)).findById(1);
		verify(badOrderedRailcarRepository, times(1)).save(badOrderedRailcar);
	}

	@Test
	public void testUpdateBadOrderAndInboundRailcar() {
		// Arrange
		BadOrderedRailcar updatedBadOrderDetails = new BadOrderedRailcar();
		updatedBadOrderDetails.setCarMark("TEST");
		updatedBadOrderDetails.setCarNumber(654321);
		updatedBadOrderDetails.setBadOrderDescription("Updated Reason");
		updatedBadOrderDetails.setBadOrderDate(badOrderDate);
		updatedBadOrderDetails.setRepairedDate(LocalDate.parse("2024-09-18"));

		// Mock the existing bad order and inbound railcar
		when(badOrderedRailcarRepository.findById(1)).thenReturn(Optional.of(badOrderedRailcar));
		when(badOrderedRailcarRepository.save(ArgumentMatchers.any(BadOrderedRailcar.class))).thenReturn(badOrderedRailcar);
		when(inboundRailcarRepository.save(ArgumentMatchers.any(InboundRailcar.class))).thenReturn(inboundRailcar);

		// Act
		BadOrderedRailcar updatedBadOrder = badOrderedRailcarService.updateBadOrderAndInboundRailcar(1,
				updatedBadOrderDetails);

		// Assert
		assertNotNull(updatedBadOrder);
		assertEquals("TEST", updatedBadOrder.getCarMark());
		assertEquals(654321, updatedBadOrder.getCarNumber());
		assertEquals("Updated Reason", updatedBadOrder.getBadOrderDescription());
		assertEquals(badOrderDate, updatedBadOrder.getBadOrderDate());

		// Verify that the bad order and inbound railcar were updated and saved
		verify(badOrderedRailcarRepository, times(1)).findById(1);
		verify(badOrderedRailcarRepository, times(1)).save(ArgumentMatchers.any(BadOrderedRailcar.class));
		verify(inboundRailcarRepository, times(1)).save(ArgumentMatchers.any(InboundRailcar.class));
	}

	@Test
    public void testDeleteBadOrderByInboundId() {
        // Arrange
        when(badOrderedRailcarRepository.findByInboundRailcar_InboundId(1)).thenReturn(badOrderedRailcar);
        doNothing().when(badOrderedRailcarRepository).delete(badOrderedRailcar);
        
        // Act
        badOrderedRailcarService.deleteBadOrderByInboundId(1);
        
        // Assert
        verify(badOrderedRailcarRepository, times(1)).findByInboundRailcar_InboundId(1);
        verify(badOrderedRailcarRepository, times(1)).delete(badOrderedRailcar);
    }
}
