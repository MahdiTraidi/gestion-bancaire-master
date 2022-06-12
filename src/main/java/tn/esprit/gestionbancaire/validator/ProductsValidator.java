package tn.esprit.gestionbancaire.validator;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.gestionbancaire.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ProductsValidator {

    public static List<String> validate(Product product, List<String> nullableAttributs) {
        List<String> errors = new ArrayList<>();
        Class<?> aClass = product.getClass();
        if (product == null) {
            errors.add(aClass.getName() + " cannot be null");
        }else {
            do {
                Arrays.asList(aClass.getDeclaredFields()).forEach(field -> {
                    try {
                        field.setAccessible(true);
                        Object o = field.get(product);
                        if (!nullableAttributs.contains(field.getName()) && o == null) {
                            errors.add("the attribute " + field.getName() + " cannot be null");
                        }
                    } catch (IllegalAccessException e) {
                        log.error("Error while fetching the " + field.getName() +
                                " of the instance " + product, e);
                    }
                });
            }while ((aClass = aClass.getSuperclass()) != null);
        }
        return errors;
    }
}
