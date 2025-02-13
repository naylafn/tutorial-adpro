package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    public void testEditProduct() {
        Product newProduct = new Product();
        newProduct.setProductId("1");
        newProduct.setProductName("Updated Product");
        newProduct.setProductQuantity(20);

        when(productRepository.edit("1", newProduct)).thenReturn(newProduct);

        Product editedProduct = productService.edit("1", newProduct);

        assertEquals("Updated Product", editedProduct.getProductName());
        assertEquals(20, editedProduct.getProductQuantity());
        verify(productRepository, times(1)).edit("1", newProduct);
    }

    @Test
    public void testEditProduct_ProductNotFound() {
        Product newProduct = new Product();
        newProduct.setProductId("999");
        newProduct.setProductName("Updated Product");
        newProduct.setProductQuantity(20);

        when(productRepository.edit("999", newProduct)).thenReturn(null);

        Product editedProduct = productService.edit("999", newProduct);

        assertNull(editedProduct);
        verify(productRepository, times(1)).edit("999", newProduct);
    }

    @Test
    public void testEditProduct_InvalidParameters() {
        Product newProduct = new Product();
        newProduct.setProductId(null);
        newProduct.setProductName("Updated Product");
        newProduct.setProductQuantity(20);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.edit(null, newProduct);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            productService.edit("1", null);
        });
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productRepository).delete("1");
        productService.delete("1");
        verify(productRepository, times(1)).delete("1");
    }

    @Test
    public void testDeleteProduct_ProductNotFound() {
        doThrow(new IllegalArgumentException("Product not found")).when(productRepository).delete("999");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.delete("999");
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).delete("999");
    }

    @Test
    public void testDeleteProduct_InvalidProductId() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.delete(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            productService.delete("");
        });
    }
}