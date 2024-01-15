package com.laderatech.stockservice.serviceImpl;

import com.laderatech.stockservice.exception.ResourceNotFoundException;
import com.laderatech.stockservice.mapperImpl.StockMapperImpl;
import com.laderatech.stockservice.model.Stock;
import com.laderatech.stockservice.repository.StockRepository;
import com.laderatech.stockservice.vo.StockVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockMapperImpl stockMapper;

    @InjectMocks
    private StockServiceImpl stockService;

    private StockVo validStockVo;
    private Stock validStock;

    @BeforeEach
    void setUp() {
        validStockVo = new StockVo();
        validStockVo.setProductCode(123L);
        validStockVo.setProductQuantity(7);
        validStockVo.setLocation("Warehouse A");
        validStockVo.setZipCode("123456");

        validStock = new Stock();
        validStock.setStockId(1L);
        validStock.setProductCode(123L);
        validStock.setProductQuantity(7);
        validStock.setLocation("Warehouse A");
        validStock.setZipCode("123456");
    }

    @Test
    @DisplayName("testAddStockWhenValidStockVoThenReturnStockVo")
    void testAddStockWhenValidStockVoThenReturnStockVo() {
        when(stockMapper.mapToStock(any(StockVo.class))).thenReturn(validStock);
        when(stockRepository.save(any(Stock.class))).thenReturn(validStock);
        when(stockMapper.mapToStockVo(any(Stock.class))).thenReturn(validStockVo);

        StockVo result = stockService.addStock(validStockVo);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(validStockVo);
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    @DisplayName("testAddStockWhenNullStockVoThenThrowException")
    void testAddStockWhenNullStockVoThenThrowException() {
        assertThatThrownBy(() -> stockService.addStock(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("stockVo must not be null");

        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    @DisplayName("testGetStockByIdWhenValidIdThenReturnStockVo")
    void testGetStockByIdWhenValidIdThenReturnStockVo() {
        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(validStock));
        when(stockMapper.mapToStockVo(any(Stock.class))).thenReturn(validStockVo);

        StockVo result = stockService.getStockById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(validStockVo);
    }

    @Test
    @DisplayName("testGetStockByIdWhenNullIdThenThrowException")
    void testGetStockByIdWhenNullIdThenThrowException() {
        assertThatThrownBy(() -> stockService.getStockById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id must not be null");
    }

    @Test
    @DisplayName("testFindAllStocksThenReturnListOfStockVos")
    void testFindAllStocksThenReturnListOfStockVos() {
        when(stockRepository.findAll()).thenReturn(Arrays.asList(validStock));
        when(stockMapper.mapToStockVo(any(Stock.class))).thenReturn(validStockVo);

        List<StockVo> result = stockService.findAllStocks();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(validStockVo);
    }

    @Test
    @DisplayName("testUpdateStockWhenValidIdAndStockVoThenReturnStockVo")
    void testUpdateStockWhenValidIdAndStockVoThenReturnStockVo() {
        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(validStock));
        when(stockRepository.save(any(Stock.class))).thenReturn(validStock);
        when(stockMapper.mapToStockVo(any(Stock.class))).thenReturn(validStockVo);

        StockVo result = stockService.updateStock(1L, validStockVo);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(validStockVo);
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    @DisplayName("testUpdateStockWhenNullIdThenThrowException")
    void testUpdateStockWhenNullIdThenThrowException() {
        assertThatThrownBy(() -> stockService.updateStock(null, validStockVo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id and stockVo must not be null");

        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    @DisplayName("testUpdateStockWhenValidIdAndNullStockVoThenThrowException")
    void testUpdateStockWhenValidIdAndNullStockVoThenThrowException() {
        assertThatThrownBy(() -> stockService.updateStock(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id and stockVo must not be null");

        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    @DisplayName("testDeleteStockByIdWhenValidIdThenDeleteStock")
    void testDeleteStockByIdWhenValidIdThenDeleteStock() {
        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(validStock));
        doNothing().when(stockRepository).delete(any(Stock.class));

        stockService.deleteStockById(1L);

        verify(stockRepository, times(1)).delete(any(Stock.class));
    }
}
