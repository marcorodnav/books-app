package home.marco.booksapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface CSVService {

    void save(MultipartFile file);
}
