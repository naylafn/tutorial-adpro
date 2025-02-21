package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Biribiri");
        product.setProductQuantity(10);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);

        Product created = productService.create(product);

        assertNotNull(created);
        assertEquals(product.getProductId(), created.getProductId());
        assertEquals(product.getProductName(), created.getProductName());
        assertEquals(product.getProductQuantity(), created.getProductQuantity());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        Product product2 = new Product();
        product2.setProductId("abc-123");
        List<Product> productList = Arrays.asList(product, product2);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product.getProductId(), result.get(0).getProductId());
        assertEquals(product2.getProductId(), result.get(1).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        Product found = productService.findById(product.getProductId());

        assertNotNull(found);
        assertEquals(product.getProductId(), found.getProductId());
        verify(productRepository, times(1)).findById(product.getProductId());
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).delete(product.getProductId());

        productService.delete(product.getProductId());

        verify(productRepository, times(1)).delete(product.getProductId());
    }

    @Test
    void testDelete_NullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.delete(null);
        });
    }

    @Test
    void testDelete_EmptyId() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.delete("   ");
        });
    }

    @Test
    void testEdit() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Sampo Cap Biribiri 2.0");
        updatedProduct.setProductQuantity(20);

        when(productRepository.edit(product.getProductId(), updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.edit(product.getProductId(), updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
        verify(productRepository, times(1)).edit(product.getProductId(), updatedProduct);
    }

    @Test
    void testEdit_NullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.edit(null, product);
        });
    }

    @Test
    void testEdit_EmptyId() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.edit("   ", product);
        });
    }

    @Test
    void testEdit_NullProduct() {
        String productId = product.getProductId();
        assertThrows(IllegalArgumentException.class, () -> {
            productService.edit(productId, null);
        });
    }
}