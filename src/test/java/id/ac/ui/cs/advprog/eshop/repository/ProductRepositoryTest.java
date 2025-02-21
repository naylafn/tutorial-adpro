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

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    final String id = "ab558e9f-1c39-460e-8860-71af6af63bd6";
    @BeforeEach
    void setUp() {
        // This method is intentionally left empty.
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(id);
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
        product1.setProductId(id);
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
    void testCreateProductWithNullName() {
        Product product = new Product();
        product.setProductName(null);

        Product createdProduct = productRepository.create(product);

        assertNull(createdProduct.getProductName());
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
    void testCreateProductWithName() {
        Product product = new Product();
        product.setProductName("Ini Nama");

        Product createdProduct = productRepository.create(product);

        assertNotNull(createdProduct.getProductName());
        assertEquals("Ini Nama", createdProduct.getProductName());
    }
    @Test
    void testEditProduct() {
        Product newProduct = new Product();
        newProduct.setProductId(id);
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

        String productId = product.getProductId();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> productRepository.edit(productId, updatedProduct));

        assertEquals("Product quantity cannot be negative", exception.getMessage());
    }
    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName("Deleted Product");
        product.setProductQuantity(100);
        productRepository.create(product);
        productRepository.delete(id);

        Iterator <Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testDeleteProduct_ProductNotFound() {
        String nonExistentId = "666";
        productRepository.delete(nonExistentId);

        Iterator<Product> iterator = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        iterator.forEachRemaining(products::add);

        assertEquals(0, products.size());
    }
    @Test
    void testDeleteProduct_EmptyRepository() {
        productRepository.delete(id);
        Iterator<Product> iterator = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        iterator.forEachRemaining(products::add);

        assertEquals(0, products.size());
    }
    @Test
    void testEditProduct_NullProduct() {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName("Original Product");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product editedProduct = productRepository.edit("nonexistent-id", product);
        assertNull(editedProduct);
    }

    @Test
    void testEditProduct_SuccessfulEdit() {
        Product originalProduct = new Product();
        originalProduct.setProductId(id);
        originalProduct.setProductName("Original Product");
        originalProduct.setProductQuantity(100);
        productRepository.create(originalProduct);

        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(200);

        Product editedProduct = productRepository.edit(id, updatedProduct);

        assertNotNull(editedProduct);
        assertEquals("Updated Product", editedProduct.getProductName());
        assertEquals(200, editedProduct.getProductQuantity());
    }

    @Test
    void testEditProduct_SanitizeName() {
        Product originalProduct = new Product();
        originalProduct.setProductId(id);
        originalProduct.setProductName("Original Product");
        originalProduct.setProductQuantity(100);
        productRepository.create(originalProduct);

        Product updatedProduct = new Product();
        updatedProduct.setProductName("Product<with>special$chars%");
        updatedProduct.setProductQuantity(200);

        Product editedProduct = productRepository.edit(id, updatedProduct);

        assertNotNull(editedProduct);
        assertEquals("Productwithspecialchars", editedProduct.getProductName());
    }

    @Test
    void testEditProduct_NullName() {
        Product originalProduct = new Product();
        originalProduct.setProductId(id);
        originalProduct.setProductName("Original Product");
        originalProduct.setProductQuantity(100);
        productRepository.create(originalProduct);

        Product updatedProduct = new Product();
        updatedProduct.setProductName(null);
        updatedProduct.setProductQuantity(200);

        Product editedProduct = productRepository.edit(id, updatedProduct);

        assertNotNull(editedProduct);
        assertNull(editedProduct.getProductName());
        assertEquals(200, editedProduct.getProductQuantity());
    }

}
