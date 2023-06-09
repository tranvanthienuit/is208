package com.is208n21.is208.Controller.Admin_Seller;



import com.is208n21.is208.Entity.OrderssList;
import com.is208n21.is208.Service.BookService;
import com.is208n21.is208.Service.OrderssDeSevice;
import com.is208n21.is208.Service.OrderssSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@Transactional
public class Orderss {
    @Autowired
    OrderssSevice orderssSevice;
    @Autowired
    OrderssDeSevice orderssDeSevice;
    @Autowired
    BookService bookService;

    @GetMapping(value = {"/seller/xem-tat-ca-Orderss/{page}", "/seller/xem-tat-ca-Orderss", "/admin/xem-tat-ca-Orderss/{page}", "/admin/xem-tat-ca-Orderss"})
    public ResponseEntity<OrderssList> getAllOrderss(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        OrderssList orderssList = new OrderssList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 12);
        Page<com.is208n21.is208.Entity.Model.Orderss> OrderssPage = orderssSevice.getAllOrderss(pageable);
        List<com.is208n21.is208.Entity.Model.Orderss> orderssPageContent = OrderssPage.getContent();
        if (orderssPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            orderssList.setOrderssList(orderssPageContent);
            orderssList.setCount(orderssSevice.getAllOrderss().size());
            return new ResponseEntity<>(orderssList, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = {"/seller/xoa-Orderss/{OrderssId}", "/seller/xoa-Orderss", "/admin/xoa-Orderss/{OrderssId}", "/admin/xoa-Orderss"})
    public ResponseEntity<String> removeOrderss(@PathVariable(value = "OrderssId", required = false) String OrderssId) throws Exception {
        com.is208n21.is208.Entity.Model.Orderss orderss = orderssSevice.findByOrderssId(OrderssId);
        if (orderss != null) {
            orderssSevice.removeByOrderssId(orderss.getOrderssId());
            return new ResponseEntity<>("successful", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = {"/seller/timorderss", "/admin/timorderss"})
    public ResponseEntity<List<com.is208n21.is208.Entity.Model.Orderss>> findOrderss(@RequestBody Map<String, Object> keysearch) {
        if (keysearch == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            List<com.is208n21.is208.Entity.Model.Orderss> orderssList = orderssSevice.findOrder(keysearch.get("keysearch").toString());
            return new ResponseEntity<>(orderssList, HttpStatus.OK);
        }
    }

    @PostMapping(value = {"/seller/sua-orderss", "/admin/sua-orderss"})
    public ResponseEntity<?> editeStatus(@RequestBody com.is208n21.is208.Entity.Model.Orderss orderss) {
        com.is208n21.is208.Entity.Model.Orderss orderss1 = orderssSevice.findByOrderssId(orderss.getOrderssId());
        orderss1.setTelephone(orderss.getTelephone());
        orderss1.setAddress(orderss.getAddress());
        orderss1.setStatus(orderss.getStatus());
        orderss1.setPay(orderss.getPay());
        orderssSevice.saveOrderss(orderss1);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }
}
