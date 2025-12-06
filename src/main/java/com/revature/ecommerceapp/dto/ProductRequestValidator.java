package com.revature.ecommerceapp.dto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
            ProductDTO dto = (ProductDTO) target;

            if(dto.getName() == null || dto.getName().isBlank()){
                errors.rejectValue("name", "400", "name cannot be empty in request");
            }

            if(dto.getPrice() < 0){
                errors.rejectValue("price", "400", "price cannot be negative");
            }
        }
}
