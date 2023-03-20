package com.is208n21.is208.Entity;


import com.is208n21.is208.Entity.Model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReturn {
    private BookList bookList;
    private List<Book> bookOder;
    private List<Book> bookRating;
}
