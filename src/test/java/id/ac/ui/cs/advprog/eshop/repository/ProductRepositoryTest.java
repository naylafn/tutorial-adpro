package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;
    
    final String ID = "ab558e9f-1c39-460e-8860-71af6af63bd6";
    @BeforeEach
    void setUp() {
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(ID);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }
    
    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(ID);
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateProduct_NegativeQuantity() {
        Product product = new Product();
        product.setProductName("Sampo Cap Jay");
        product.setProductQuantity(-1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productRepository.create(product);
        });

        assertEquals("Product quantity cannot be negative", exception.getMessage());
    }
    @Test
    public void testEditProduct() {
        Product newProduct = new Product();
        newProduct.setProductId(ID);
        newProduct.setProductName("Updated Product");
        newProduct.setProductQuantity(100);
        Product savedProduct = productRepository.create(newProduct);

        assertEquals(savedProduct, newProduct);
    }
    @Test
    void testEditProduct_NegativeQuantity() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductName("Sampo Cap Jay");
        updatedProduct.setProductQuantity(-1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productRepository.edit(product.getProductId(), updatedProduct);
        });

        assertEquals("Product quantity cannot be negative", exception.getMessage());
    }
    @Test
    public void testDeleteProduct() {
        Product product = new Product();
        product.setProductId(ID);
        product.setProductName("Deleted Product");
        product.setProductQuantity(100);
        productRepository.create(product);
        productRepository.delete(ID);

        Iterator <Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    public void testDeleteProduct_ProductNotFound() {
        String nonExistentId = "666";
        productRepository.delete(nonExistentId);

        Iterator<Product> iterator = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        iterator.forEachRemaining(products::add);

        assertEquals(0, products.size());
    }
    @Test
    public void testDeleteProduct_EmptyRepository() {
        productRepository.delete(ID);
        Iterator<Product> iterator = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        iterator.forEachRemaining(products::add);

        assertEquals(0, products.size());
    }

}
