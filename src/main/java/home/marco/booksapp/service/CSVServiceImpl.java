package home.marco.booksapp.service;

import home.marco.booksapp.helper.CSVHelper;
import home.marco.booksapp.model.Author;
import home.marco.booksapp.model.Book;
import home.marco.booksapp.repository.AuthorRepository;
import home.marco.booksapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CSVServiceImpl implements CSVService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public CSVServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
       this.bookRepository = bookRepository;
       this.authorRepository = authorRepository;
    }
    @Override
    public void save(MultipartFile file) {
        try {
            List<Book> booksRaw = CSVHelper.csvToBooks(file.getInputStream());
            List<Book> books = curateAuthorsBooksList(booksRaw);
            bookRepository.saveAll(books);
        } catch(IOException e) {
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
