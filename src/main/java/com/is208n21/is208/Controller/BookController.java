package com.is208n21.is208.Controller;



import com.is208n21.is208.Entity.BookList;
import com.is208n21.is208.Entity.Filter;
import com.is208n21.is208.Entity.Model.Book;
import com.is208n21.is208.Entity.Model.Rating;
import com.is208n21.is208.Entity.Model.User;
import com.is208n21.is208.Sercurity.userDetail;
import com.is208n21.is208.Service.BookService;
import com.is208n21.is208.Service.CategoryService;
import com.is208n21.is208.Service.RatingService;
import com.is208n21.is208.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Transactional
public class BookController {
    @Autowired
    BookService booksService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    RatingService ratingService;

    @PostMapping(value = {"/tim-sach"})
    public ResponseEntity<List<Book>> findBook(@RequestBody Map<String, Object> infoBook) throws Exception {
        List<Book> books = booksService.searchBook(infoBook.get("infoBook").toString());
        if (infoBook.get("infoBook").toString() != null) {
            if (books.size() != 0) {
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = {"/xem-chi-tiet-sach/{bookId}", "/xem-chi-tiet-sach"})
    public ResponseEntity<Book> productdetail(@PathVariable(value = "bookId", required = false) String bookId) throws Exception {
        Book book = booksService.findBookByBookId(bookId);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sach-moi")
    public ResponseEntity<List<Book>> findbyarrive() throws Exception {
        Pageable pageable = PageRequest.of(0, 12, Sort.by("dayAdd").descending());
        Page<Book> bookPage = booksService.getBookByDayAdd(pageable);
        List<Book> bookList = bookPage.getContent();
        if (bookList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping(value = {"/search/book"})
    public ResponseEntity<List<String>> searchByNameBook(@RequestBody Map<String, String> keywword) {
        List<String> searchString = booksService.searchAuto(keywword.get("keyword"));
        return new ResponseEntity<>(searchString, HttpStatus.OK);
    }

    @GetMapping(value = {"/search/{categoryId}/{page}", "/search/{page}"})
    public ResponseEntity<?> findBookByCategory(@PathVariable(name = "page", required = false) Integer page, @PathVariable(value = "categoryId", required = false) String categoryId) {
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 12);
        List<Book> book = booksService.findBooksByCategoryId(categoryId, pageable);
        BookList bookList = new BookList();
        bookList.setBookList(book);
        bookList.setCount(booksService.findBooksByCategoryId(categoryId).size());
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("/search/{page}")
    public ResponseEntity<?> findBookByCondition(@RequestBody Filter keyword,
                                                 @PathVariable(name = "page", required = false) Integer page) {
        if (page == null)
            page = 0;
        Pageable pageable = null;
        if (keyword.isMa())
            pageable = PageRequest.of(page, 12, Sort.by("price").ascending());
        else
            pageable = PageRequest.of(page, 12, Sort.by("price").descending());
        BookList bookList = booksService.findBookByCondition(keyword, pageable);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/data-filter/{categoryId}")
    public ResponseEntity<?> findDataFilter(@PathVariable("categoryId") String categoryId) {
        return new ResponseEntity<>(booksService.dataFilters(categoryId), HttpStatus.OK);
    }

    @PostMapping(value = {"/danh-gia-sach/{bookId}/{star}", "/danh-gia-sach"})
    public ResponseEntity<?> appriciateBook(@PathVariable(value = "bookId", required = false) String bookId, @PathVariable(value = "star", required = false) int star) {
        Rating rating = new Rating();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.equals(authentication.getName(), "anonymousUser")) {
            return new ResponseEntity<>("error", HttpStatus.OK);
        }
        userDetail userDetail = (userDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUserId(userDetail.getUserId());
        Book book = booksService.findBookByBookId(bookId);
        rating.setUser(user);
        rating.setBook(book);
        rating.setRating(star);
        ratingService.save(rating);
        book.setRating(rating.getRating());
        booksService.saveBook(book);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }
}
