package com.bryanve.ecommercebackend.dao;

import com.bryanve.ecommercebackend.model.Product;

import java.util.List;

public interface ProductDAO {

    Product createProduct(Product product);

    List<Product> getProducts();

    boolean deleteProductByID(int id);
}
