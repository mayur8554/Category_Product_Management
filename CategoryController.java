package com.project.CatProduct;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @Autowired
    CategoryRepository repo2;

    // Add a new category
    @PostMapping("/category")
    public void addCategory(@RequestBody Category category) {
    	//Category c = new Category(category);
        repo2.save(category);
    }
    
   //  Get all categories without paginationw
//    @GetMapping("/categories/all")
//    public List<Category> getAllCategories() {
//        return repo2.findAll();
//    }

    // Get categories with pagination	
    @GetMapping("/category")
    public Page<Category> getCategories(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) 
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category>Categories= repo2.findAll(pageable) ;
            return Categories;
    }

    // Get category by ID
    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return repo2.findById(id)
                .map(category -> ResponseEntity.ok().body(category))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> updateCategoryId(@PathVariable Long id, @RequestBody String newName) {
        return repo2.findById(id)
                .map(category -> {
                    category.setCategoryName(newName);
                    repo2.save(category);
                    return ResponseEntity.ok().body(category);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteByCategoryId(@PathVariable Long id) {
        if (!repo2.existsById(id)) {
            return ResponseEntity.notFound().build(); // If category does not exist, return 404
        }

        ProductRepository.deleteByCategoryId(id); // Delete all related products
        repo2.deleteById(id); // Delete the category
        return ResponseEntity.noContent().build(); // Return 204 No Content (Successful Deletion)
    }

    
  
}
