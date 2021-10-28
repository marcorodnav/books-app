package home.marco.booksapp.controller.dto;

public class BookUploadResponse {

    private String message;

    public BookUploadResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
