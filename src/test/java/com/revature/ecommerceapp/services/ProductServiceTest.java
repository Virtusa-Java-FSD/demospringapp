package com.revature.ecommerceapp.services;


import com.revature.ecommerceapp.dto.ProductDTO;
import com.revature.ecommerceapp.models.Products;
import com.revature.ecommerceapp.repository.ProductRepository;
import com.revature.ecommerceapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository repo;

    @InjectMocks
    private ProductService service;


    @Test
    void testGetProduct(){
        Products pr =  new Products();
        pr.setName("mobile");
        pr.setColor("Black");
        pr.setPrice(12);
        List<Products>  products = new ArrayList<>();
        products.add(pr);
        when(repo.findAll()).thenReturn(products);
        List<Products> res= service.getAllProducts();

        assertEquals("mobile", res.getFirst().getName());

    }

    @Test
    void testUpdate(){
        Products pr = new Products();
        pr.setProductid(1l);
        pr.setName("mobile");
        pr.setColor("Black");
        pr.setPrice(12);

//        ProductDTO pr_dto = new ProductDTO();
//        pr_dto.setName("mobile");
//        pr_dto.setColor("Black");
//        pr_dto.setPrice(12);

        Products pr2 = new Products();
        pr2.setProductid(1l);
        pr2.setName("mobile");
        pr2.setColor("white");
        pr2.setPrice(12);

        when(repo.findById(1l)).thenReturn(Optional.of(pr));
        when(repo.save(pr)).thenReturn(pr2);

        assertEquals("mobile",service.update(pr.getProductid(),pr2).getName());

        verify(repo, times(1)).findById(1l);

    }

}
