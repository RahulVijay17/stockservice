package com.laderatech.stockservice.mapperImpl;

import com.laderatech.stockservice.mapper.StockMapper;
import com.laderatech.stockservice.model.Stock;
import com.laderatech.stockservice.vo.StockVo;

import org.springframework.stereotype.Service;

@Service
public class StockMapperImpl implements StockMapper {

    @Override
    public StockVo mapToStockVo(Stock stock) {
        if (stock==null){
            return null;
        }else {
            StockVo stockVo= new StockVo();
            stockVo.setStockId(stock.getStockId());
            stockVo.setProductCode(stock.getProductCode());
            stockVo.setProductQuantity(stock.getProductQuantity());
            stockVo.setZipCode(stock.getZipCode());
            stockVo.setLocation(stock.getLocation());
            return  stockVo;

        }
    }

    @Override
    public Stock mapToStock(StockVo stockVo) {
        if (stockVo==null){
            return null;
        }else {
            Stock stock= new Stock();
            stock.setStockId(stockVo.getStockId());
            stock.setProductCode(stockVo.getProductCode());
            stock.setProductQuantity(stockVo.getProductQuantity());
            stock.setZipCode(stockVo.getZipCode());
            stock.setLocation(stockVo.getLocation());
            return stock;
        }
    }
}
