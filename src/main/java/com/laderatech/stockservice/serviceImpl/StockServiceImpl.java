package com.laderatech.stockservice.serviceImpl;

import com.laderatech.stockservice.exception.ResourceNotFoundException;
import com.laderatech.stockservice.mapperImpl.StockMapperImpl;
import com.laderatech.stockservice.model.Stock;
import com.laderatech.stockservice.repository.StockRepository;
import com.laderatech.stockservice.service.StockService;
import com.laderatech.stockservice.vo.StockVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMapperImpl stockMapper;

    @Override
    public StockVo addStock(StockVo stockVo) {
        log.info("Adding new stock: {}", stockVo);
        if (Objects.nonNull(stockVo)) {
            Stock stock = stockMapper.mapToStock(stockVo);
            stock.setCreatedBy("admin");

            Stock savedStock = stockRepository.save(stock);
            log.info("Stock added successfully: {}", savedStock);

            return stockMapper.mapToStockVo(savedStock);
        } else {
            throw new IllegalArgumentException("stockVo must not be null");
        }
    }

    @Override
    public StockVo getStockById(Long id) {
        log.info("Fetching stock by ID: {}", id);
        if (Objects.nonNull(id)) {
            return stockRepository.findById(id)
                    .map(stockMapper::mapToStockVo)
                    .orElseThrow(() -> {
                        log.warn("Stock with ID {} not found", id);
                        return new ResourceNotFoundException("Stock", "id", id);
                    });
        } else {
            throw new IllegalArgumentException("id must not be null");
        }
    }

    @Override
    public List<StockVo> findAllStocks() {
        log.info("Fetching all stocks");
        List<StockVo> stockVoList = stockRepository.findAll().stream()
                .map(stockMapper::mapToStockVo)
                .collect(Collectors.toList());

        log.info("Fetched {} stocks", stockVoList.size());
        return stockVoList;
    }

    @Override
    public StockVo updateStock(Long id, StockVo stockVo) {
        log.info("Updating stock with ID: {}", id);
        if (Objects.nonNull(id) && Objects.nonNull(stockVo)) {
            return stockRepository.findById(id)
                    .map(stock -> {
                        log.info("Updating stock details: {}", stockVo);
                        stock.setProductCode(stockVo.getProductCode());
                        stock.setProductQuantity(stockVo.getProductQuantity());
                        stock.setLocation(stockVo.getLocation());
                        stock.setZipCode(stockVo.getZipCode());
                        stock.setUpdatedBy("admin");

                        Stock updatedStock = stockRepository.save(stock);
                        log.info("Stock updated successfully: {}", updatedStock);

                        return updatedStock;
                    })
                    .map(stockMapper::mapToStockVo)
                    .orElseThrow(() -> {
                        log.warn("Stock with ID {} not found", id);
                        return new ResourceNotFoundException("Stock", "id", id);
                    });
        } else {
            throw new IllegalArgumentException("id and stockVo must not be null");
        }
    }

    @Override
    public void deleteStockById(long id) {
        log.info("Deleting stock with ID: {}", id);
        if (Objects.nonNull(id)) {
            stockRepository.findById(id)
                    .ifPresent(stock -> {
                        stockRepository.delete(stock);
                        log.info("Stock deleted successfully: {}", stock);
                    });
        } else {
            throw new IllegalArgumentException("id must not be null");
        }
    }
}