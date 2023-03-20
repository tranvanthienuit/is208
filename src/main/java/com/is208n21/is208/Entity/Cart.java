package com.is208n21.is208.Entity;


import com.is208n21.is208.Entity.Model.User;
import lombok.Data;

import java.util.List;

@Data
public class Cart {
    private List<CartBook> cartBooks;
    private User user;
    private String pay;
}
