package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getProductId() == null){
            product.setProductId(String.valueOf(UUID.randomUUID()));
        }

        if (product.getProductQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }

        if (product.getProductName() != null) {
            String sanitizedName = product.getProductName().replaceAll("[<>%$]", "");
            product.setProductName(sanitizedName);
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String productId) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public void delete(String productId) {
        Product productToDelete = findById(productId);
        productData.remove(productToDelete);
    }

    public Product edit(String productId, Product newProduct) {
        Product productToEdit = findById(productId);

        if (productToEdit != null) {
            if (newProduct.getProductQuantity() < 0) {
                throw new IllegalArgumentException("Product quantity cannot be negative");
            }
            if (newProduct.getProductName() != null) {
                String sanitizedName = newProduct.getProductName().replaceAll("[<>%$]", "");
                newProduct.setProductName(sanitizedName);
            }
            productToEdit.setProductName(newProduct.getProductName());
            productToEdit.setProductQuantity(newProduct.getProductQuantity());
        }
        return productToEdit;
    }
}