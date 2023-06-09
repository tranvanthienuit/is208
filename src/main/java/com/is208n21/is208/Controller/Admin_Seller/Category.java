package com.is208n21.is208.Controller.Admin_Seller;



import com.is208n21.is208.Entity.CateList;
import com.is208n21.is208.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class Category {
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"/seller/xem-tat-ca-loai-sach/{page}", "/seller/xem-tat-ca-loai-sach", "/admin/xem-tat-ca-loai-sach/{page}", "/admin/xem-tat-ca-loai-sach"})
    public ResponseEntity<CateList> getAllCate(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        CateList cateList = new CateList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 12);
        Page<com.is208n21.is208.Entity.Model.Category> categoriesList = categoryService.getAllCategory(pageable);
        List<com.is208n21.is208.Entity.Model.Category> categoryListContent = categoriesList.getContent();
        if (categoryListContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            cateList.setCategoryList(categoryListContent);
            cateList.setCount(categoryService.getAllCategory().size());
            return new ResponseEntity<>(cateList, HttpStatus.OK);
        }
    }
}
