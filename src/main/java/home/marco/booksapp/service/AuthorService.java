package home.marco.booksapp.service;

import home.marco.booksapp.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    void saveAuthor(Author author);

    Author getAuthorById(Long id);
}
