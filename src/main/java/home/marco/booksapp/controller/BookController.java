package home.marco.booksapp.controller;

import home.marco.booksapp.model.Author;
import home.marco.booksapp.model.Book;
import home.marco.booksapp.model.dto.BookForm;
import home.marco.booksapp.service.AuthorService;
import home.marco.booksapp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping(value = "/")
    public String home(Model model) {
       model.addAttribute("booksList", bookService.getAllBooks());

       return "index";
    }

    @GetMapping(value = "/showNewBookForm")
    public String showNewBookForm(Model model) {

        BookForm bookForm = new BookForm();
        bookForm.setBook(new Book());
        bookForm.setAuthor(new Author());
        List<Author> existingAuthors = authorService.getAllAuthors();

        model.addAttribute("bookForm", bookForm);
        model.addAttribute("existingAuthors", existingAuthors);

        return "new_book";
    }

    @PostMapping(value = "/saveBook")
    public String saveBook(@ModelAttribute("bookForm") BookForm bookForm, BindingResult result, Model model) {
        if (isInvalidBook(bookForm.getBook())) {
            result.addError(new FieldError("bookForm", "book.title", "Book Title cannot be empty"));
        }
        if (isInvalidAuthor(bookForm.getAuthor())) {
            result.addError(new FieldError("bookForm", "author.id","Author Selection is required"));
        }

        if (result.hasErrors()) {
            List<Author> existingAuthors = authorService.getAllAuthors();
            model.addAttribute("bookForm", bookForm);
            model.addAttribute("existingAuthors", existingAuthors);
            return "new_book";
        }
        Book book = bookForm.getBook();
        Author author = authorService.getAuthorById(bookForm.getAuthor().getId());
        book.setAuthor(author);
        bookService.saveBook(book);
        return "redirect:/";
    }

    @PostMapping(value = "/saveAuthor")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "new_author";
        }
        authorService.saveAuthor(author);
        return "redirect:/showNewBookForm";
    }

    @GetMapping(value = "/showUpdateBookForm/{id}")
    public String showUpdateBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        List<Author> existingAuthors = authorService.getAllAuthors();
        BookForm bookForm = new BookForm();
        bookForm.setBook(book);
        Author currentAuthor = new Author();
        currentAuthor.setId(book.getAuthor().getId());
        bookForm.setAuthor(currentAuthor);
        model.addAttribute("bookForm", bookForm);
        model.addAttribute("existingAuthors", existingAuthors);

        return "update_book";
    }

    @PostMapping(value = "/updateBook")
    public String updateBook(@ModelAttribute("bookForm") BookForm bookForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update_book";
        }
        if (isInvalidBook(bookForm.getBook())) {
            result.addError(new FieldError("bookForm", "book.title", "Book Title cannot be empty"));
        }
        if (isInvalidAuthor(bookForm.getAuthor())) {
            result.addError(new FieldError("bookForm", "author.id","Author Selection is required"));
        }

        if (result.hasErrors()) {
            List<Author> existingAuthors = authorService.getAllAuthors();
            model.addAttribute("bookForm", bookForm);
            model.addAttribute("existingAuthors", existingAuthors);
            return "update_book";
        }
        Book book = bookForm.getBook();
        Author author = authorService.getAuthorById(bookForm.getAuthor().getId());
        book.setAuthor(author);
        bookService.saveBook(book);
        return "redirect:/";
    }

    @GetMapping(value = "/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);

        return "redirect:/";
    }

    private Boolean isInvalidBook(Book book) {
        return book.getTitle() == null || book.getTitle().isEmpty();
    }

    private Boolean isInvalidAuthor(Author author) {
        return author == null || author.getId() == null;
    }
}
