package com.revature.ecommerceapp.controllerv2;


import com.revature.ecommerceapp.dto.ProductDTO;
import com.revature.ecommerceapp.models.Products;
import com.revature.ecommerceapp.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v2/products")
public class ProductControllerV2 {
    private final ProductRepository repo;

    public  ProductControllerV2(ProductRepository repo){
        this.repo = repo;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("product", new ProductDTO());
        return "create-product";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") ProductDTO product, Model model){
        Products pr = new Products();
        pr.setName(product.getName());
        pr.setColor(product.getColor());
        pr.setPrice(product.getPrice());
        repo.save(pr);

        model.addAttribute("message","Product Created successfully");
        return "success";

    }
}
