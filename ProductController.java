package com.project.CatProduct;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	@Autowired
	ProductRepository repo;
	@Autowired
	CategoryRepository repo1;
	@PostMapping("/products")
	public void addProduct(@RequestBody Product product)
	{
		Category c = repo1.findByCategoryName(product.getCategory().getCategoryName());
		if (c == null) {
			Category newCategory = new Category(product.getCategory().getCategoryName());
			repo1.save(newCategory);
			c = newCategory;
		}
		product.setCategory(c);
		repo.save(product);
		System.out.println("Product saved with categoryId: " + c.getCategoryId());
	}
//	@GetMapping("/products/all")
//	public List<Product> getAllProduct()
//	{
//		return repo.findAll();
//	}

	@GetMapping("/products")
	public Page<Product> getAllProducts(
	        @RequestParam(defaultValue="0") int page,
	        @RequestParam(required=false) int lenth) {
	    
		int pageSize = 0;
		if(pageSize<=0)
		{
			pageSize=5;
		}
	    Pageable pageable = PageRequest.of(page, pageSize);
	    return repo.findAll(pageable);
	}
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable int id)
	{
		return repo.findByProductId(id).stream().findFirst().orElse(null);
	}

	@PutMapping("/products/{id}")
	public Product updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) 
	{
		Category c = repo1.findByCategoryName(updatedProduct.getCategory().getCategoryName());
		if (c == null) {
			Category newCategory = new Category(updatedProduct.getCategory().getCategoryName());
			repo1.save(newCategory); 
			c = newCategory;
		}
		System.out.println(updatedProduct.getCategory().getCategoryName());
		Product oldProduct = repo.findByProductId(id).stream().findFirst().orElse(null);

		if (oldProduct == null) {
			throw new RuntimeException("Product with ID " + id + " not found");
		}
		oldProduct.setProductName(updatedProduct.getProductName());
		oldProduct.setPrice(updatedProduct.getPrice());
		oldProduct.setCategory(c);
		return repo.save(oldProduct);
	}
	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable int id) {
		repo.deleteById(id);
		System.out.println("deleted");
	}
}
