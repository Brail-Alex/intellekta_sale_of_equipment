package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "sum_sale")
    private int sumSale;
    @Column(name = "date_receipt_goods")
    private String dateReceiptGoods;
    @Column(name = "date_sale")
    private String dateSale;
    @Column(name = "id_goods")
    private long idGoods;

    @Override
    public String toString() {
        return "Продажа: " +
                "ID: " + id +
                ", Сумма продажи: " + sumSale +
                ", Дата поставки товара: " + dateReceiptGoods + '\'' +
                ", Дата продажи: " + dateSale + '\'' +
                ", ID товара: " + idGoods ;
    }
}
