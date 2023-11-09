package com.billlog.publicweatherapi.global.custom.exception;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import java.util.Collection;

/**
 * @valid가 Collection은 검증해주지 못하기 때문에 Custom 하여 사용
 * customCollectionValidator.validate(dto, bindingResult);
 *  if (bindingResult.hasErrors()) {
 *      throw new BindException(bindingResult);
 *  }
 */
@Component
public class CustomCollectionValidator implements Validator {
    private SpringValidatorAdapter validator;
    public CustomCollectionValidator() {
        this.validator = new SpringValidatorAdapter(
                Validation.buildDefaultValidatorFactory().getValidator()
        );
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Collection collection = (Collection) target;
        for (Object object : collection) {
            validator.validate(object, errors);
        }
    }
}