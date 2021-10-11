package home.marco.booksapp.model.dto;

import home.marco.booksapp.model.Author;
import home.marco.booksapp.model.Book;

import javax.validation.constraints.NotNull;

public class BookForm {

    private Book book;

    private Author author;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
