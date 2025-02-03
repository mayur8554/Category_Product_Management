package com.project.CatProduct;

import java.util.Collection;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	
	    @Modifying
	    @Transactional
	    @Query("DELETE FROM Product p WHERE p.category.id = :categoryId")
		static
	    void deleteByCategoryId(@Param("categoryId") Long categoryId) {
			// TODO Auto-generated method stub
			
		}

	    Collection<Product> findByProductId(int id);


		
}