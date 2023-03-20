package com.is208n21.is208.Entity;


import com.is208n21.is208.Entity.Model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSelect {
    private Book book;
    private Long total;
}
