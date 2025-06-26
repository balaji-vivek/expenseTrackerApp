package com.expensetracker.expenseTrackerApp.Controller;

import com.expensetracker.expenseTrackerApp.model.Category;
import com.expensetracker.expenseTrackerApp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Category>createCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }
}
