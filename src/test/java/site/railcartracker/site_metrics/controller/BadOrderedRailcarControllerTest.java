//package site.railcartracker.site_metrics.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import site.railcartracker.site_metrics.model.BadOrderedRailcar;
//import site.railcartracker.site_metrics.service.BadOrderedRailcarService;
//
//@WebMvcTest(BadOrderedRailcarController.class)
//public class BadOrderedRailcarControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private BadOrderedRailcarService badOrderedRailcarService;
//
//    @Test
//    public void testGetActiveBadOrderedRailcars() throws Exception {
//        // Arrange
//        BadOrderedRailcar activeBadOrder = new BadOrderedRailcar();
//        activeBadOrder.setCarMark("TEST");
//        activeBadOrder.setCarNumber(123456);
//        activeBadOrder.setActive(true);
//
//        List<BadOrderedRailcar> activeBadOrders = Arrays.asList(activeBadOrder);
//        when(badOrderedRailcarService.getActiveBadOrders(true)).thenReturn(activeBadOrders);
//
//        // Act and Assert
//        mockMvc.perform(get("/bad-orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("isActive", "true"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$[0].carMark").value("TEST"))
//            .andExpect(jsonPath("$[0].carNumber").value(123456))
//            .andExpect(jsonPath("$[0].active").value(true))
//            .andDo(print());
//    }
//
//    @Test
//    public void testGetAllBadOrderedRailcars() throws Exception {
//        // Arrange
//        BadOrderedRailcar badOrder1 = new BadOrderedRailcar();
//        badOrder1.setCarMark("TEST");
//        badOrder1.setCarNumber(123456);
//
//        BadOrderedRailcar badOrder2 = new BadOrderedRailcar();
//        badOrder2.setCarMark("TSET");
//        badOrder2.setCarNumber(654321);
//
//        List<BadOrderedRailcar> allBadOrders = Arrays.asList(badOrder1, badOrder2);
//        when(badOrderedRailcarService.getAllBadOrders()).thenReturn(allBadOrders);
//
//        // Act and Assert
//        mockMvc.perform(get("/bad-orders/all")
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$[0].carMark").value("TEST"))
//            .andExpect(jsonPath("$[1].carMark").value("TSET"))
//            .andDo(print());
//    }
//
//    @Test
//    public void testGetBadOrderedRailcarById() throws Exception {
//        // Arrange
//        BadOrderedRailcar badOrder = new BadOrderedRailcar();
//        badOrder.setCarMark("TEST");
//        badOrder.setCarNumber(123456);
//
//        when(badOrderedRailcarService.getBadOrderById(1)).thenReturn(badOrder);
//
//        // Act and Assert
//        mockMvc.perform(get("/bad-orders/1")
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.carMark").value("TEST"))
//            .andExpect(jsonPath("$.carNumber").value(123456))
//            .andDo(print());
//    }
//
//    @Test
//    public void testUpdateBadOrderedRailcar() throws Exception {
//        // Arrange
//        BadOrderedRailcar updatedBadOrder = new BadOrderedRailcar();
//        updatedBadOrder.setCarMark("TEST");
//        updatedBadOrder.setCarNumber(123456);
//        updatedBadOrder.setBadOrderReason("Brake issue");
//
//        when(badOrderedRailcarService.updateBadOrderAndInboundRailcar(any(Integer.class), any(BadOrderedRailcar.class)))
//            .thenReturn(updatedBadOrder);
//
//        // Act and Assert
//        mockMvc.perform(put("/bad-orders/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{ \"carMark\": \"ABC\", \"carNumber\": \"12345\", \"badOrderReason\": \"Brake issue\" }"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.carMark").value("TEST"))
//            .andExpect(jsonPath("$.carNumber").value(123456))
//            .andExpect(jsonPath("$.badOrderReason").value("Brake issue"))
//            .andDo(print());
//    }
//
//    @Test
//    public void testDeleteBadOrderedRailcar() throws Exception {
//        // No arrangement needed for delete
//
//        // Act and Assert
//        mockMvc.perform(delete("/bad-orders/1")
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent())
//            .andDo(print());
//    }
//}
//
