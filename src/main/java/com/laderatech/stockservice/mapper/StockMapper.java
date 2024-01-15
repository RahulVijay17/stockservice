package com.laderatech.stockservice.mapper;

import com.laderatech.stockservice.model.Stock;
import com.laderatech.stockservice.vo.StockVo;

public interface StockMapper {

    StockVo mapToStockVo(Stock stock);

    Stock mapToStock(StockVo stockVo);
}
