package home.marco.booksapp.controller;

import home.marco.booksapp.model.Book;
import home.marco.booksapp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping(value = "/")
    public String home(Model model) {
       model.addAttribute("booksList", bookService.getAllBooks());

       return "index";
    }

    @GetMapping(value = "/showNewBookForm")
    public String showNewBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);

        return "new_book";
    }

    @PostMapping(value = "/saveBook")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);

        return "redirect:/";
    }

    @GetMapping(value = "/showUpdateBookForm/{id}")
    public String showUpdateBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);

        return "update_book";
    }

    @GetMapping(value = "/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);

        return "redirect:/";
    }
}
