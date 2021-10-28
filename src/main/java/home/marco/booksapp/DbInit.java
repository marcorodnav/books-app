package home.marco.booksapp;

import home.marco.booksapp.model.Author;
import home.marco.booksapp.model.Book;
import home.marco.booksapp.repository.AuthorRepository;
import home.marco.booksapp.repository.BookRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DbInit {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public DbInit(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @PostConstruct
    private void postConstruct() {
//        Book book = new Book();
//        book.setId(1L);
//        book.setTitle("Dune");
//        Author author = new Author();
//        author.setId(1L);
//        author.setFirstName("Frank");
//        author.setLastName("Herbert");
//        authorRepository.save(author);
//        book.setAuthor(author);
//        bookRepository.save(book);
//
//        Book book2 = new Book();
//        book2.setId(2L);
//        book2.setTitle("1984");
//        Author author2 = new Author();
//        author2.setId(2L);
//        author2.setFirstName("George");
//        author2.setLastName("Orwell");
//        authorRepository.save(author2);
//        book2.setAuthor(author2);
//        bookRepository.save(book2);
    }
}
