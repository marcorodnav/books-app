package home.marco.booksapp.controller;

import home.marco.booksapp.model.Book;
import home.marco.booksapp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "new_book";
        }
        bookService.saveBook(book);
        return "redirect:/";
    }

    @GetMapping(value = "/showUpdateBookForm/{id}")
    public String showUpdateBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);

        return "update_book";
    }

    @PostMapping(value = "/updateBook")
    public String updateBook(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "update_book";
        }
        bookService.saveBook(book);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);

        return "redirect:/";
    }
}
