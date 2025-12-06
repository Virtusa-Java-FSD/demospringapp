package com.revature.ecommerceapp.repository;

import com.revature.ecommerceapp.models.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
//    int countByName(String name);
//
//    List<Products> findByPriceGreaterThan(double price);
//
//    //Property expressions   findByNameAndPrice
//
//    List<Products> findByNameAndPriceOrderByPriceAsc(String name, double price);
//
//    boolean existsByName(String name);
//
//    List<Products> findByCategoryAndPriceBetween(String category, double min, double max);
//
//    Page<Products> findByCategory(String category, Pageable p);
//
//    @Query(
//            """
//                    SELECT p FROM Product p WHERE p.price >(
//                    SELECT AVG(price) FROM products p GROUP BY p.category)
//                    """
//    )
//    List<Products> findProductsAboveCategoryAverage();
//
//    @Query(value = "SELECT * FROM product WHERE category= :category ORDER BY price DESC LIMIT :limit OFSET :offset",
//    nativeQuery = true)
//    List<Products> getByCategoryPaged(@Param("category") String category, @Param("limit") int limit, @Param("offset") int offset);


}
