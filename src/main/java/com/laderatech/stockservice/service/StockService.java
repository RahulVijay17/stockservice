package com.laderatech.stockservice.service;

import com.laderatech.stockservice.vo.StockVo;

import java.util.List;

public interface StockService {

    StockVo addStock(StockVo stockVo);

    StockVo getStockById(Long id);

    List<StockVo> findAllStocks();

    StockVo updateStock(Long id,StockVo stockVo);

    void deleteStockById(long id);

}
