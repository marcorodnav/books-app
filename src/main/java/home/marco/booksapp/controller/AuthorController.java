package home.marco.booksapp.controller;

import home.marco.booksapp.model.Author;
import home.marco.booksapp.service.AuthorService;
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

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/showNewAuthorForm")
    public String showNewAuthorForm(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);

        return "new_author";
    }

    @PostMapping(value = "/saveAuthor")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author,
                             BindingResult result, @RequestParam(name = "redirectPage", defaultValue = "") String redirectPage) {
        if (result.hasErrors()) {
            return "new_author";
        }
        authorService.saveAuthor(author);
        return "redirect:" + (redirectPage.isEmpty() ? "/" : redirectPage);
    }
}
