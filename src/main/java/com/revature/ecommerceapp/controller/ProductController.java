package com.revature.ecommerceapp.controller;

import com.revature.ecommerceapp.dto.ProductDTO;
import com.revature.ecommerceapp.dto.ProductRequestValidator;
import com.revature.ecommerceapp.models.ErrorResponse;
import com.revature.ecommerceapp.models.Products;
import com.revature.ecommerceapp.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("")
@RestController
@RequestMapping("/api/products")
@Tag(name = "Products API", description = "Operations on products")
public class ProductController {

    private final ProductService service;
    private final ProductRequestValidator validator;

    @Autowired
    public ProductController(ProductService service, ProductRequestValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.addValidators(validator);
    }

    @Operation(
            summary = "Create Products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product Created Successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Products.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<Products> create(@RequestBody ProductDTO pr){
        Products product = service.addProduct(pr);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Operation(summary = "get products")

    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts(@CookieValue("UserCookie") String cookie){
//        System.out.println(name + " " + cookie);
        List<Products> products = service.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @Operation(summary = "Updateproducts")
    @PutMapping("/{id}")
    public ResponseEntity<?> update( @PathVariable Long id, @Validated @RequestBody Products pr){
        Products product = service.update( id,pr);
        if(product == null){
            ErrorResponse res = new ErrorResponse("Product doesnt exist", "Nullpointer");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No product found");

        }
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Operation(summary = "delete products")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(service.delete(id)){
            return ResponseEntity.status(HttpStatus.OK).body("DELETED SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ErrorResponse("Product Doesn't exist", "Nullpointer"));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullpointer(NullPointerException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}
