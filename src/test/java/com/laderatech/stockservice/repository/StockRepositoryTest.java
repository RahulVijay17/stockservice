package com.laderatech.stockservice.repository;

import com.laderatech.stockservice.model.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StockRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void testSaveStock() {
        // Create a new stock
        Stock stock = new Stock();
        stock.setProductQuantity(8);
        stock.setProductCode(123456L);
        stock.setLocation("New York");
        stock.setZipCode("123456");
        stockRepository.save(stock);

        // Retrieve the stock from the database
        Optional<Stock> savedStock = stockRepository.findById(stock.getStockId());

        // Verify that the stock is saved correctly
        Assertions.assertTrue(savedStock.isPresent());
        Assertions.assertEquals(stock.getProductQuantity(), savedStock.get().getProductQuantity());
        Assertions.assertEquals(stock.getProductCode(), savedStock.get().getProductCode());
        Assertions.assertEquals(stock.getLocation(), savedStock.get().getLocation());
        Assertions.assertEquals(stock.getZipCode(), savedStock.get().getZipCode());
    }

    @Test
    public void testFindAllStocks() {
        // Create some test stocks
        Stock stock1 = new Stock();
        stock1.setProductQuantity(8);
        stock1.setProductCode(123456L);
        stock1.setLocation("New York");
        stock1.setZipCode("123456");
        entityManager.persist(stock1);

        Stock stock2 = new Stock();
        stock2.setProductQuantity(10);
        stock2.setProductCode(789012L);
        stock2.setLocation("Los Angeles");
        stock2.setZipCode("789012");
        entityManager.persist(stock2);

        // Retrieve all stocks from the database
        List<Stock> stocks = stockRepository.findAll();

        // Verify that the correct number of stocks is retrieved
        Assertions.assertEquals(4, stocks.size());
    }

    @Test
    public void testDeleteStock() {
        // Create a new stock
        Stock stock = new Stock();
        stock.setProductQuantity(8);
        stock.setProductCode(123456L);
        stock.setLocation("New York");
        stock.setZipCode("123456");
        entityManager.persist(stock);

        // Delete the stock from the database
        stockRepository.delete(stock);

        // Verify that the stock is deleted
        Optional<Stock> deletedStock = stockRepository.findById(stock.getStockId());
        Assertions.assertFalse(deletedStock.isPresent());
    }

    @Test
    public void testFindByIdWhenStockExistsThenReturnStock() {
        // Arrange
        Stock stock = new Stock();
        stock.setProductQuantity(8);
        stock.setProductCode(123456L);
        stock.setLocation("New York");
        stock.setZipCode("123456");
        Stock persistedStock = entityManager.persistFlushFind(stock);

        // Act
        Optional<Stock> foundStock = stockRepository.findById(persistedStock.getStockId());

        // Assert
        Assertions.assertTrue(foundStock.isPresent(), "Stock should be found with the given id");
        Assertions.assertEquals(persistedStock.getStockId(), foundStock.get().getStockId(), "Found stock id should match the persisted stock id");
        Assertions.assertEquals(persistedStock.getProductQuantity(), foundStock.get().getProductQuantity(), "Found stock quantity should match the persisted stock quantity");
        Assertions.assertEquals(persistedStock.getProductCode(), foundStock.get().getProductCode(), "Found stock product code should match the persisted stock product code");
        Assertions.assertEquals(persistedStock.getLocation(), foundStock.get().getLocation(), "Found stock location should match the persisted stock location");
        Assertions.assertEquals(persistedStock.getZipCode(), foundStock.get().getZipCode(), "Found stock zip code should match the persisted stock zip code");
    }

    @Test
    public void testFindByIdWhenStockDoesNotExistThenReturnEmptyOptional() {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        Optional<Stock> foundStock = stockRepository.findById(nonExistentId);

        // Assert
        Assertions.assertFalse(foundStock.isPresent(), "No stock should be found with a non-existent id");
    }


}