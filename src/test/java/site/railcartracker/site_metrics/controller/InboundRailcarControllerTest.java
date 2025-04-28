//package site.railcartracker.site_metrics.controller;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import site.railcartracker.site_metrics.model.InboundRailcar;
//import site.railcartracker.site_metrics.service.InboundRailcarService;
//
//@WebMvcTest(InboundRailcarController.class)
//public class InboundRailcarControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Mock
//	private InboundRailcarService inboundRailcarService;
//
//	@InjectMocks
//	private InboundRailcarController inboundRailcarController;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	private InboundRailcar railcar;
//	
//    LocalDate inspectedDate = LocalDate.parse("2024-09-01");
//
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//
//		railcar = new InboundRailcar();
//		railcar.setInboundId(1);
//		railcar.setCarMark("TEST");
//		railcar.setCarNumber(123456);
//		railcar.setInspectedDate(inspectedDate);
//		railcar.setBadOrdered(false);
//	}
//
//	@Test
//    public void testCreateInboundRailcar() throws Exception {
//        when(inboundRailcarService.createInboundRailcar(ArgumentMatchers.any(InboundRailcar.class)))
//    .thenReturn(railcar);
//
//        mockMvc.perform(post("/inspections")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(railcar)))
//            .andExpect(status().isCreated())
//            .andExpect(jsonPath("$.carMark").value("TEST"))
//            .andExpect(jsonPath("$.carNumber").value(123456))
//            .andExpect(jsonPath("$.inspectedDate").value(inspectedDate));
//    }
//
//	@Test
//	public void testGetAllInboundRailcars() throws Exception {
//		List<InboundRailcar> railcars = Arrays.asList(railcar);
//
//		when(inboundRailcarService.getAllInboundRailcars()).thenReturn(railcars);
//
//		mockMvc.perform(get("/inspections")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(1))
//				.andExpect(jsonPath("$[0].carMark").value("TEST")).andExpect(jsonPath("$[0].carNumber").value(123456));
//	}
//
//	@Test
//    public void testGetInboundRailcarById() throws Exception {
//        when(inboundRailcarService.getInboundRailcarById(1)).thenReturn(railcar);
//
//        mockMvc.perform(get("/inspections/1"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.carMark").value("TEST"))
//            .andExpect(jsonPath("$.carNumber").value(123456));
//    }
//
//	@Test
//	public void testUpdateInboundRailcar() throws Exception {
//		InboundRailcar updatedRailcar = new InboundRailcar();
//		updatedRailcar.setCarMark("TEST");
//		updatedRailcar.setCarNumber(654321);
//
//		when(inboundRailcarService.updateInboundRailcar(1, updatedRailcar)).thenReturn(updatedRailcar);
//
//		mockMvc.perform(put("/inspections/1").contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(updatedRailcar))).andExpect(status().isOk())
//				.andExpect(jsonPath("$.carMark").value("TEST")).andExpect(jsonPath("$.carNumber").value(654321));
//	}
//
//	@Test
//	public void testDeleteInboundRailcar() throws Exception {
//		mockMvc.perform(delete("/inspections/1")).andExpect(status().isNoContent());
//	}
//}
