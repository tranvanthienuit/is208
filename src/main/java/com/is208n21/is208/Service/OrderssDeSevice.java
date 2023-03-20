package com.is208n21.is208.Service;



import com.is208n21.is208.Entity.BookSelect;
import com.is208n21.is208.Entity.Model.Book;
import com.is208n21.is208.Entity.Model.OrderssDetail;
import com.is208n21.is208.Entity.book_category;
import com.is208n21.is208.Entity.month_price;
import com.is208n21.is208.Repository.BookRepository;
import com.is208n21.is208.Repository.OrderssDeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderssDeSevice {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    OrderssDeRepository orderssDeRepository;

    public void removeByOrderssDeId(String orderssDeId) {
        OrderssDetail orderssDetail = orderssDeRepository.findOrderssDetailByOrderssDeId(orderssDeId);
        orderssDeRepository.delete(orderssDetail);
    }

    public void saveOrderssDe(OrderssDetail orderssDetail) {
        orderssDeRepository.save(orderssDetail);
    }

    public Page<OrderssDetail> getAllOrderssDe(Pageable pageable) {
        return orderssDeRepository.findAll(pageable);
    }

    public List<OrderssDetail> findOrderssDe(String orderssId) {
        return orderssDeRepository.findAllByOrderssId(orderssId);
    }

    public List<OrderssDetail> findOrderssDetailsByOrderss(String orderssId) {
        return orderssDeRepository.findOrderssDetailsByOrderss(orderssId);
    }

    public List<Book> getBookFromBorrDe(Pageable pageable) {
        List<BookSelect> objects = orderssDeRepository.getBookFromBorrDe(pageable);
        List<Book> bookList = new ArrayList<>();
        for (BookSelect bookSelect : objects) {
            bookList.add(bookSelect.getBook());
        }
        return bookList;
    }

    public List<OrderssDetail> getAllOrderssDe() {
        return orderssDeRepository.findAll();
    }

    public List<book_category> getBookAndCategory() {
        return orderssDeRepository.getBookAndCategory();
    }

    public List<month_price> getPriceAndMonth() {
        return orderssDeRepository.getPriceAndMonth();
    }
}
