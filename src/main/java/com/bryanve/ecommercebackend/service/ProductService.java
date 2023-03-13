package com.bryanve.ecommercebackend.service;

import com.bryanve.ecommercebackend.dao.ProductDAO;
import com.bryanve.ecommercebackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDAO productDAO;

    @Autowired
    public ProductService(@Qualifier("productDAO") ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Product createProduct(Product product) {
        return productDAO.createProduct(product);
    }

    public List<Product> getProducts() {
        return productDAO.getProducts();
    }

}
