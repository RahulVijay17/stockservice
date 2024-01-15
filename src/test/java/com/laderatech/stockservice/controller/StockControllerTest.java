package com.laderatech.stockservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laderatech.stockservice.service.StockService;
import com.laderatech.stockservice.vo.StockVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateStockWhenValidStockVoThenReturn201AndStockVo() throws Exception {
        StockVo stockVo = new StockVo();
        stockVo.setProductQuantity(7);
        stockVo.setProductCode(123456L);
        stockVo.setLocation("Warehouse A");
        stockVo.setZipCode("123456");

        given(stockService.addStock(stockVo)).willReturn(stockVo);

        mockMvc.perform(post("/api/v2/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockVo)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(stockVo)));
    }

    @Test
    public void testGetStockByIdWhenValidStockIdThenReturn200AndStockVo() throws Exception {
        Long stockId = 1L;
        StockVo stockVo = new StockVo();
        stockVo.setStockId(stockId);
        stockVo.setProductQuantity(7);
        stockVo.setProductCode(123456L);
        stockVo.setLocation("Warehouse A");
        stockVo.setZipCode("123456");

        given(stockService.getStockById(stockId)).willReturn(stockVo);

        mockMvc.perform(get("/api/v2/stock/{id}", stockId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stockVo)));
    }

    @Test
    public void testFindAllStocksThenReturn200AndStockVoList() throws Exception {
        StockVo stockVo1 = new StockVo();
        stockVo1.setProductQuantity(7);
        stockVo1.setProductCode(123456L);
        stockVo1.setLocation("Warehouse A");
        stockVo1.setZipCode("123456");

        StockVo stockVo2 = new StockVo();
        stockVo2.setProductQuantity(8);
        stockVo2.setProductCode(654321L);
        stockVo2.setLocation("Warehouse B");
        stockVo2.setZipCode("654321");

        List<StockVo> stockVoList = Arrays.asList(stockVo1, stockVo2);

        given(stockService.findAllStocks()).willReturn(stockVoList);

        mockMvc.perform(get("/api/v2/stock"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stockVoList)));
    }

    @Test
    public void testUpdatedStockWhenValidStockIdAndStockVoThenReturn200AndStockVo() throws Exception {
        Long stockId = 1L;
        StockVo stockVo = new StockVo();
        stockVo.setProductQuantity(7);
        stockVo.setProductCode(123456L);
        stockVo.setLocation("Warehouse A");
        stockVo.setZipCode("123456");

        given(stockService.updateStock(stockId, stockVo)).willReturn(stockVo);

        mockMvc.perform(put("/api/v2/stock/{id}", stockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockVo)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stockVo)));
    }

    @Test
    public void testDeleteStockWhenValidStockIdThenReturn200AndMessage() throws Exception {
        Long stockId = 1L;
        String expectedMessage = "Stock Deleted Successfully";

        mockMvc.perform(delete("/api/v2/stock/{id}", stockId))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }
}
