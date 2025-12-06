package com.revature.ecommerceapp.service;

import com.revature.ecommerceapp.dto.ProductDTO;
import com.revature.ecommerceapp.models.Products;
import com.revature.ecommerceapp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

//    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repo;

    @Autowired
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Products addProduct(ProductDTO productDTO){
        Products pr = new Products();
        pr.setColor(productDTO.getColor());
        return repo.save(pr);
    }

    public List<Products> getAllProducts(){
        return repo.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Products update(Long id, Products p) {
        //repo.
        //repo.
        //repo.
        Products pr = getProductById(id);//savepoint
        if(pr != null){
            //
            pr.setName(p.getName());
            pr.setColor(p.getColor());
            pr.setPrice(p.getPrice());
            if(pr.getPrice() < 0){
//                throw new Exception();
            }
            //repo
            return repo.save(pr);
        }
        log.info("No product found");
       return null;

    }
    //Propagation
    //REQUIRED
    //REQURIES_NEW
    // NESTED = savepoint
    // SUPPORTS
    // NOT SUPPORTED
    //NEVER

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Products getProductById(Long id){
        try {
            // repo.
            // repo.

            return repo.findById(id).get();

        } catch (Exception e) {
           return null;
        }


    }

    public boolean delete(Long id){
        if( getProductById(id) != null){
            repo.deleteById(id);
            return true;
        }
        return false;

    }

//    public Page<Products> getByCategory(String cat, int pg, int count){
//        PageRequest page = PageRequest.of(pg,count,
//                Sort.by("price").descending());
//
//        return repo.findByCategory(cat, page);
//
//    }


}


//customer
//order - connection
//product