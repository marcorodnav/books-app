package home.marco.booksapp.service;

import home.marco.booksapp.helper.CSVHelper;
import home.marco.booksapp.model.Author;
import home.marco.booksapp.model.Book;
import home.marco.booksapp.repository.AuthorRepository;
import home.marco.booksapp.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CSVServiceImpl implements CSVService {

    private Logger logger = LoggerFactory.getLogger(CSVServiceImpl.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public CSVServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void save(MultipartFile file) {
        try {
            logger.info("Starting process to save books from CSV");
            List<Book> booksRaw = CSVHelper.csvToBooks(file.getInputStream());
            logger.info("Found "+ booksRaw.size() + " books to load");
            logger.info("Starting curation of books to save to discard existing books");
            List<Book> books = curateAuthorsBooksList(booksRaw);
            logger.info("Total number of books to save: "+ books.size());
            bookRepository.saveAll(books);
        } catch (IOException e) {
            logger.error("Exception thrown while saving books: "+e.getMessage());
            throw new RuntimeException("Fail to store books data: " + e.getMessage());
        }
    }

    private List<Book> curateAuthorsBooksList(List<Book> booksRaw) {
        return booksRaw.stream().peek((Book book) -> {
                Author a = book.getAuthor();
                Author existingA = authorRepository.getByFirstNameAndLastName(a.getFirstName(), a.getLastName());
                if (existingA != null) {
                    book.setAuthor(existingA);
                } else {
                    authorRepository.save(a);
                }
            })
            .filter((Book book) -> !bookRepository.existsBookByTitle(book.getTitle()))
            .collect(Collectors.toList());
    }
}
