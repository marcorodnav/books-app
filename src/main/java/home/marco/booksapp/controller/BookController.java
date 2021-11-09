package home.marco.booksapp.controller;

import home.marco.booksapp.model.Author;
import home.marco.booksapp.model.Book;
import home.marco.booksapp.controller.dto.BookForm;
import home.marco.booksapp.service.AuthorService;
import home.marco.booksapp.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping(value = "/")
    public String home(Model model) {
        logger.info("Home Request");
        model.addAttribute("booksList", bookService.getAllBooks());

        return "index";
    }

    @GetMapping(value = "/showNewBookForm")
    public String showNewBookForm(Model model) {

        logger.info("New Book request");
        BookForm bookForm = new BookForm();
        bookForm.setBook(new Book());
        bookForm.setAuthor(new Author());
        List<Author> existingAuthors = authorService.getAllAuthors();

        logger.info("Found "+existingAuthors.size()+" existing authors");
        model.addAttribute("bookForm", bookForm);
        model.addAttribute("existingAuthors", existingAuthors);

        return "new_book";
    }

    @PostMapping(value = "/saveBook")
    public String saveBook(@ModelAttribute("bookForm") BookForm bookForm, BindingResult result, Model model) {
        if (isInvalidBook(bookForm.getBook())) {
            logger.error("Invalid book: empty title");
            result.addError(new FieldError("bookForm", "book.title", "Book Title cannot be empty"));
        }
        if (isInvalidAuthor(bookForm.getAuthor())) {
            logger.error("Invalid author: Author needs to be present");
            result.addError(new FieldError("bookForm", "author.id","Author Selection is required"));
        }

        if (result.hasErrors()) {
            logger.error("Errors present when saving book, aborting process");
            List<Author> existingAuthors = authorService.getAllAuthors();
            model.addAttribute("bookForm", bookForm);
            model.addAttribute("existingAuthors", existingAuthors);
            return "new_book";
        }
        Book book = bookForm.getBook();
        Author author = authorService.getAuthorById(bookForm.getAuthor().getId());
        book.setAuthor(author);
        bookService.saveBook(book);
        logger.info("Book: "+book.getTitle()+" saved successfully");
        return "redirect:/";
    }

    @GetMapping(value = "/showUpdateBookForm/{id}")
    public String showUpdateBookForm(@PathVariable("id") Long id, Model model) {
        logger.info("Showing Update Book form");
        Book book = bookService.getBookById(id);
        List<Author> existingAuthors = authorService.getAllAuthors();
        logger.info("Found "+ existingAuthors.size() + " existing authors");
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

        if (isInvalidBook(bookForm.getBook())) {
            logger.error("Invalid book title provided");
            result.addError(new FieldError("bookForm", "book.title", "Book Title cannot be empty"));
        }
        if (isInvalidAuthor(bookForm.getAuthor())) {
            logger.error("Invalid author provided");
            result.addError(new FieldError("bookForm", "author.id","Author Selection is required"));
        }

        if (result.hasErrors()) {
            logger.error("Errors found, returning to view update book");
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
        logger.info("Received request to delete book with id: "+id);
        bookService.deleteBook(id);
        logger.info("Book deleted successfully");
        return "redirect:/";
    }

    private Boolean isInvalidBook(Book book) {
        return book.getTitle() == null || book.getTitle().isEmpty();
    }

    private Boolean isInvalidAuthor(Author author) {
        return author == null || author.getId() == null;
    }
}
