package home.marco.booksapp.service;

import home.marco.booksapp.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    void saveBook(Book book);

    Book getBookById(Long id);

    void deleteBook(Long id);
}
