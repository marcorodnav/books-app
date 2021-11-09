package home.marco.booksapp.controller;

import home.marco.booksapp.model.Author;
import home.marco.booksapp.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/authors")
public class AuthorController {

    Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/showNewAuthorForm")
    public String showNewAuthorForm(Model model, @RequestParam(value = "redirectPage", defaultValue = "", required = false) String redirectPage,
                                    @RequestParam(value = "bookId", required = false) Long bookId) {
        logger.info("Showing new author form");
        Author author = new Author();
        model.addAttribute("author", author);
        model.addAttribute("redirectPage", redirectPage);
        if (bookId != null) {
            model.addAttribute("bookId", bookId);
        }

        return "new_author";
    }

    @PostMapping(value = "/saveAuthor")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author,
                             BindingResult result, @RequestParam(name = "redirectPage", defaultValue = "") String redirectPage,
                             @RequestParam(name="bookId", required = false) Long bookId) {
        logger.info("Saving new author");
        if (result.hasErrors()) {
            return "new_author";
        }
        authorService.saveAuthor(author);
        String redirectView = buildRedirectViewPath(redirectPage, bookId);
        logger.info("Author saved successfully\n Redirecting to: "+ redirectView);
        return redirectView;
    }

    private String buildRedirectViewPath(String redirectPage, Long bookId) {
        String redirectPath = "redirect:/";
        if (redirectPage != null && !redirectPage.isEmpty()) {
            if (redirectPage.equals("showNewBookForm")) {
                return redirectPath + redirectPage;
            }

            return redirectPath + redirectPage + "/" + bookId;
        }
        return redirectPath;
    }
}
