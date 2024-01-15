package com.laderatech.stockservice.controller;

import com.laderatech.stockservice.service.StockService;
import com.laderatech.stockservice.vo.StockVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/stock")
@Tag(
        name = "Stock Service - StockController",
        description = "Stock Controller Exposes REST APIs for Stock Service"

)
@CrossOrigin(origins = "*")
@Slf4j
public class StockController {


    private StockService stockService;

    @Operation(
            summary = "Save Stock REST APIs ",
            description = "Save Stock REST APIs is used to save stock object in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 CREATED"
    )
    @PostMapping
    private ResponseEntity<StockVo> createStock(@Valid @RequestBody StockVo stockVo){
        return new ResponseEntity<>(stockService.addStock(stockVo)
                            , HttpStatus.CREATED);
    }

    @Operation(
            summary = "GET Stock REST APIs ",
            description = "GET Stock REST APIs is used to get stock object in a database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 SUCCESS"
    )
    @GetMapping("{id}")
    private ResponseEntity<StockVo> getStockById(@PathVariable("id") Long id){
        return ResponseEntity.ok(stockService.getStockById(id));
    }

    @Operation(
            summary = "GET Stocks REST APIs ",
            description = "GET Stocks REST APIs is used to get stock objects from a database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 SUCCESS"
    )
    @GetMapping
    private ResponseEntity<List<StockVo>> findAllStocks(){
        log.info("Received a request for data.");
        return new ResponseEntity<>(stockService.findAllStocks(),HttpStatus.OK);
    }

    @Operation(
            summary = "UPDATE Stock REST APIs ",
            description = "UPDATE Stock REST APIs is used to update stock object in a database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 SUCCESS"
    )
    @PutMapping("{id}")
    private ResponseEntity<StockVo> updatedStock( @PathVariable("id") Long id,
                                                 @RequestBody @Valid StockVo stockVo){
        StockVo stockVoResponse=stockService.updateStock(id,stockVo);

        return new ResponseEntity<>(stockVoResponse,HttpStatus.OK);

    }

    @Operation(
            summary = "DELETE Stock REST APIs ",
            description = "DELETE Stock REST APIs is used to delete stock object in a database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 SUCCESS"
    )
    @DeleteMapping("{id}")
    private ResponseEntity<String> deleteStock(@PathVariable("id") Long id){
        stockService.deleteStockById(id);
        return new ResponseEntity<>("Stock Deleted Successfully",HttpStatus.OK);
    }

}
