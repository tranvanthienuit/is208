package com.is208n21.is208.Controller.Admin_Seller;



import com.is208n21.is208.Entity.Model.Orderss;
import com.is208n21.is208.Entity.Model.OrderssDetail;
import com.is208n21.is208.Entity.OrderssDelist;
import com.is208n21.is208.Service.BookService;
import com.is208n21.is208.Service.OrderssDeSevice;
import com.is208n21.is208.Service.OrderssSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
public class OrderssDe {
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    OrderssSevice orderssSevice;
    @Autowired
    BookService bookService;

    @DeleteMapping(value = {"/seller/xoa-Orderss-detail/{OrderssDeId}", "/seller/xoa-Orderss-detail", "/admin/xoa-Orderss-detail/{OrderssDeId}", "/admin/xoa-Orderss-detail"})
    public ResponseEntity<String> removeOrderssDe(@PathVariable(value = "OrderssDeId", required = false) String OrderssDeId) throws Exception {
        if (orderssDeSevice.findOrderssDe(OrderssDeId) != null) {
            orderssDeSevice.removeByOrderssDeId(OrderssDeId);
            return new ResponseEntity<>("successful", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = {"/seller/xem-tat-ca-Orderss-Detail/{page}", "/seller/xem-tat-ca-Orderss-Detail", "/admin/xem-tat-ca-Orderss-Detail/{page}", "/admin/xem-tat-ca-Orderss-Detail"})
    public ResponseEntity<OrderssDelist> getAllOrderssDe(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        OrderssDelist orderssDelist = new OrderssDelist();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 12);
        Page<OrderssDetail> OrderssDetailPage = orderssDeSevice.getAllOrderssDe(pageable);
        List<OrderssDetail> orderssDetailPageContent = OrderssDetailPage.getContent();
        if (orderssDetailPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            orderssDelist.setOrderssDelists(orderssDetailPageContent);
            orderssDelist.setCount(orderssDeSevice.getAllOrderssDe().size());
            return new ResponseEntity<>(orderssDelist, HttpStatus.OK);
        }
    }

    @GetMapping(value = {"/seller/tim-Orderssde/{userName}", "/seller/tim-Orderss", "/admin/tim-Orderssde/{userName}", "/admin/tim-Orderss"})
    public ResponseEntity<List<OrderssDetail>> findOrderssDe(@PathVariable(name = "userName", required = false) String userName) {
        if (userName == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            List<OrderssDetail> orderssDetailList = new ArrayList<>();
            List<com.is208n21.is208.Entity.Model.Orderss> orderss = orderssSevice.findOrder(userName);
            for (Orderss orderss1 : orderss) {
                List<OrderssDetail> orderssDetailList1 = orderssDeSevice.findOrderssDetailsByOrderss(orderss1.getOrderssId());
                orderssDetailList.addAll(orderssDetailList1);
            }
            return new ResponseEntity<>(orderssDetailList, HttpStatus.OK);
        }
    }

}
