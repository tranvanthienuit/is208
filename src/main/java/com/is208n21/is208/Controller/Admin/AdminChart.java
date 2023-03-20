package com.is208n21.is208.Controller.Admin;



import com.is208n21.is208.Entity.*;
import com.is208n21.is208.Service.OrderssDeSevice;
import com.is208n21.is208.Service.OrderssSevice;
import com.is208n21.is208.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class AdminChart {
    @Autowired
    OrderssSevice orderssSevice;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    UserService userService;

    @GetMapping(value = "/admin/chart")
    public ResponseEntity<?> chart() {
        List<month_book> bookAndMonth = orderssSevice.getBookAndMonth();
        if (orderssSevice.getBookAndMonth() == null || orderssSevice.getBookAndMonth().isEmpty()) {
            bookAndMonth.clear();
        }
        List<book_category> bookAndCategory = orderssDeSevice.getBookAndCategory();
        if (bookAndCategory == null || bookAndCategory.isEmpty()) {
            bookAndCategory.clear();
        }
        List<month_price> priceAndMonth = orderssDeSevice.getPriceAndMonth();
        if (priceAndMonth == null || priceAndMonth.isEmpty()) {
            priceAndMonth.clear();
        }
        List<month_user> userAndMonth = userService.getUserAndMonnth();
        if (userAndMonth == null || userAndMonth.isEmpty()) {
            userAndMonth.clear();
        }
        Chart chart = new Chart(bookAndMonth, bookAndCategory, userAndMonth, priceAndMonth);
        return new ResponseEntity<>(chart, HttpStatus.OK);
    }
}
