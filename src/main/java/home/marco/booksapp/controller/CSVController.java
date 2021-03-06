package home.marco.booksapp.controller;

import home.marco.booksapp.controller.dto.BookUploadResponse;
import home.marco.booksapp.helper.CSVHelper;
import home.marco.booksapp.service.CSVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/csv")
public class CSVController {

    private Logger logger = LoggerFactory.getLogger(CSVController.class);

    private final CSVService csvService;

    public CSVController(CSVService csvService) {
        this.csvService = csvService;
    }

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes) {
        logger.info("Received request to load books from CSV file");
        if(CSVHelper.hasCSVFormat(file)) {
            try {
                csvService.save(file);
                redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");

                return "redirect:/";

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error","Could not upload the file: " + file.getOriginalFilename() + "!");
                return "redirect:/";
            }
        } else {
            logger.error("File provided does not have CSV Format");
        }
        redirectAttributes.addFlashAttribute("message","Please upload a csv file!");
        return "redirect:/";
    }
}
