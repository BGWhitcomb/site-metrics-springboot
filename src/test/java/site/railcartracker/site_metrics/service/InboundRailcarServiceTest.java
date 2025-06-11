package site.railcartracker.site_metrics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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
import site.railcartracker.site_metrics.repository.InboundRailcarRepository;

@SpringBootTest
public class InboundRailcarServiceTest {

	@Mock
	private InboundRailcarRepository inboundRailcarRepository;

	@Mock
	private BadOrderedRailcarService badOrderedRailcarService;

	@InjectMocks
	private InboundRailcarService inboundRailcarService;

	private InboundRailcar inboundRailcar;
	private BadOrderedRailcar badOrderedRailcar;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		// Create mock InboundRailcar
		inboundRailcar = new InboundRailcar();
		inboundRailcar.setInboundId((long) 1);
		inboundRailcar.setCarMark("TEST");
		inboundRailcar.setCarNumber(123456);
		inboundRailcar.setInspectedDate(LocalDate.now());
		inboundRailcar.setBadOrdered(true);

		// Create mock BadOrderedRailcar
		badOrderedRailcar = new BadOrderedRailcar();
		badOrderedRailcar.setBadOrderId((long) 1);
		badOrderedRailcar.setCarMark("TEST");
		badOrderedRailcar.setCarNumber(123456);
		badOrderedRailcar.setBadOrderDate(LocalDate.now());
	}

	@Test
    public void testCreateInboundRailcar_WithBadOrder() {
        // Arrange
        when(inboundRailcarRepository.save(any(InboundRailcar.class))).thenReturn(inboundRailcar);
        when(badOrderedRailcarService.createBadOrder(any(BadOrderedRailcar.class))).thenReturn(badOrderedRailcar);

        // Act
        InboundRailcar createdRailcar = inboundRailcarService.createInboundRailcar(inboundRailcar);

        // Assert
        assertNotNull(createdRailcar);
        assertEquals(inboundRailcar.getCarMark(), createdRailcar.getCarMark());
        verify(inboundRailcarRepository, times(1)).save(any(InboundRailcar.class));
        verify(badOrderedRailcarService, times(1)).createBadOrder(any(BadOrderedRailcar.class));
    }

	@Test
	public void testCreateInboundRailcar_WithoutBadOrder() {
		// Arrange
		inboundRailcar.setBadOrdered(false);
		when(inboundRailcarRepository.save(any(InboundRailcar.class))).thenReturn(inboundRailcar);

		// Act
		InboundRailcar createdRailcar = inboundRailcarService.createInboundRailcar(inboundRailcar);

		// Assert
		assertNotNull(createdRailcar);
		assertFalse(createdRailcar.isBadOrdered());
		verify(inboundRailcarRepository, times(1)).save(any(InboundRailcar.class));
		verify(badOrderedRailcarService, never()).createBadOrder(any(BadOrderedRailcar.class));
	}

	@Test
    public void testGetAllInboundRailcars() {
        // Arrange
        when(inboundRailcarRepository.findAll()).thenReturn(Arrays.asList(inboundRailcar));

        // Act
        var railcars = inboundRailcarService.getAllInboundRailcars();

        // Assert
        assertEquals(1, railcars.size());
        verify(inboundRailcarRepository, times(1)).findAll();
    }

	@Test
    public void testGetInboundRailcarById() {
        // Arrange
        when(inboundRailcarRepository.findById(anyInt())).thenReturn(Optional.of(inboundRailcar));

        // Act
        InboundRailcar foundRailcar = inboundRailcarService.getInboundRailcarById(1);

        // Assert
        assertNotNull(foundRailcar);
        assertEquals(inboundRailcar.getCarMark(), foundRailcar.getCarMark());
        verify(inboundRailcarRepository, times(1)).findById(anyInt());
    }

//	@Test
//    public void testUpdateInboundRailcarAndBadOrder_CreateNewBadOrder() {
//        // Arrange
//        when(inboundRailcarRepository.findById(anyInt())).thenReturn(Optional.of(inboundRailcar));
//        when(inboundRailcarRepository.save(any(InboundRailcar.class))).thenReturn(inboundRailcar);
//        when(badOrderedRailcarService.getBadOrderByInboundRailcarId(anyInt())).thenReturn(null);
//        when(badOrderedRailcarService.createBadOrder(any(BadOrderedRailcar.class))).thenReturn(badOrderedRailcar);
//
//        // Act
//        InboundRailcar updatedRailcar = inboundRailcarService.updateInboundRailcarAndBadOrder(1, inboundRailcar);
//
//        // Assert
//        assertNotNull(updatedRailcar);
//        verify(badOrderedRailcarService, times(1)).createBadOrder(any(BadOrderedRailcar.class));
//    }
//
//	@Test
//    public void testUpdateInboundRailcarAndBadOrder_UpdateExistingBadOrder() {
//        // Arrange
//        when(inboundRailcarRepository.findById(anyInt())).thenReturn(Optional.of(inboundRailcar));
//        when(inboundRailcarRepository.save(any(InboundRailcar.class))).thenReturn(inboundRailcar);
//        when(badOrderedRailcarService.getBadOrderByInboundRailcarId(anyInt())).thenReturn(badOrderedRailcar);
//
//        // Act
//        InboundRailcar updatedRailcar = inboundRailcarService.updateInboundRailcarAndBadOrder(1, inboundRailcar);
//
//        // Assert
//        assertNotNull(updatedRailcar);
//        verify(badOrderedRailcarService, times(1)).updateBadOrder(anyInt(), any(BadOrderedRailcar.class));
//    }

	@Test
    public void testDeleteInboundRailcar_WithBadOrder() {
        // Arrange
        when(inboundRailcarRepository.findById(anyInt())).thenReturn(Optional.of(inboundRailcar));
        when(badOrderedRailcarService.getBadOrderByInboundRailcarId(anyInt())).thenReturn(badOrderedRailcar);

        // Act
        inboundRailcarService.deleteInboundRailcar(1);

        // Assert
        verify(inboundRailcarRepository, times(1)).delete(any(InboundRailcar.class));
        verify(badOrderedRailcarService, times(1)).deleteBadOrderByInboundId(anyInt());
    }

	@Test
	public void testDeleteInboundRailcar_WithoutBadOrder() {
		// Arrange
		inboundRailcar.setBadOrdered(false);
		when(inboundRailcarRepository.findById(anyInt())).thenReturn(Optional.of(inboundRailcar));

		// Act
		inboundRailcarService.deleteInboundRailcar(1);

		// Assert
		verify(inboundRailcarRepository, times(1)).delete(any(InboundRailcar.class));
		verify(badOrderedRailcarService, never()).deleteBadOrderByInboundId(anyInt());
	}
}
