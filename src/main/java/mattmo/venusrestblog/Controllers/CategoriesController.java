package mattmo.venusrestblog.Controllers;

import lombok.AllArgsConstructor;
import mattmo.venusrestblog.data.Category;
import mattmo.venusrestblog.data.Post;
import mattmo.venusrestblog.repository.CategoriesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/categories", produces = "application/json")
public class CategoriesController {
    private CategoriesRepository categoriesRepository;

    @GetMapping("")
    private List<Category> fetchAllCategories() {
        return categoriesRepository.findAll();
    }

    @GetMapping("/search")
    private Category fetchCategoryByCategoryName(@RequestParam String categoryName) {
        Category cat = categoriesRepository.findByName(categoryName);
        if(cat == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category " + categoryName + " not found");
        }
        return cat;
    }
}