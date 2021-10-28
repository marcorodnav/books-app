package home.marco.booksapp.helper;

import home.marco.booksapp.model.Author;
import home.marco.booksapp.model.Book;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static String TYPE = "text/csv";
    public static String[] HEADERS = {"Title", "AuthorFirstName", "AuthorLastName"};

    public static Boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Book> csvToBooks(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8.toString()));
             CSVParser csvParser = new CSVParser(fileReader,
                 CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Book> books = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for(CSVRecord record : csvRecords) {
                Author author = new Author();
                author.setFirstName(record.get(HEADERS[1]));
                author.setLastName(record.get(HEADERS[2]));
                Book book = new Book();
                book.setTitle(record.get(HEADERS[0]));
                book.setAuthor(author);
                books.add(book);
            }
            return books;
        }catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
