package com.is208n21.is208.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chart {
    private List<month_book> month_book;
    private List<book_category> book_category;
    private List<month_user> month_user;
    private List<month_price> month_price;
}
