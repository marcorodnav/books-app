package home.marco.booksapp;

import home.marco.booksapp.model.Book;
import home.marco.booksapp.repository.BookRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DbInit {

    private final BookRepository bookRepository;

    public DbInit(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    private void postConstruct() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Dune");
        book.setAuthor("Frank Herbert");
        bookRepository.save(book);
    }
}
