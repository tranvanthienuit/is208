package com.is208n21.is208.Entity;


import com.is208n21.is208.Entity.Model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateList {
    List<Category> categoryList;
    int count;
}
