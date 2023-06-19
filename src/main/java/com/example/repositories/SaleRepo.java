package com.example.repositories;

import com.example.entity.Sale;
import com.example.exception.EntityAlreadyExistException;
import com.example.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SaleRepo {
    private final JdbcTemplate jdbcTemplate;
    private final ISaleRepo iSaleRepo;
    @Autowired
    public SaleRepo(JdbcTemplate jdbcTemplate, ISaleRepo iSaleRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.iSaleRepo = iSaleRepo;
    }

    public Sale getSaleById(long id) throws EntityNotFoundException {
        Sale sale = jdbcTemplate.queryForObject("SELECT * FROM sales WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Sale.class));
        if (sale == null) {
            throw new EntityNotFoundException("Продажа по заданному ID не найдена.");
        }
        return sale;
    }

    public Sale createSale(Sale sale) throws EntityAlreadyExistException {
        if (iSaleRepo.findAllById(sale.getId()) != null) {
            throw new EntityAlreadyExistException("Номер продажи уже существует.");
        }
        return iSaleRepo.save(sale);
    }

    public List<Sale> getAllSale() {
        return iSaleRepo.findAll();
    }

//    public int getCountRecord() {
//        return getAllSale().toArray().length;
//    }
//
//    public List<Sale> getSalesBySumSaleMore100() {
//        return jdbcTemplate.query("SELECT * FROM sales WHERE sum_sale >= 100;", new BeanPropertyRowMapper<>(Sale.class));
//    }
}
