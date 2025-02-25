package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService service;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);

        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("createProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        when(service.create(product)).thenReturn(product);

        String viewName = productController.createProductPost(product, model);

        verify(service).create(product);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testProductListPage() {
        List<Product> products = Arrays.asList(product);
        when(service.findAll()).thenReturn(products);

        String viewName = productController.productListPage(model);

        verify(service).findAll();
        verify(model).addAttribute("products", products);
        assertEquals("productList", viewName);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(service).delete(product.getProductId());

        String viewName = productController.deleteProduct(product.getProductId(), model);

        verify(service).delete(product.getProductId());
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testEditProductPage() {
        when(service.findById(product.getProductId())).thenReturn(product);

        String viewName = productController.editProductPage(product.getProductId(), model);

        verify(service).findById(product.getProductId());
        verify(model).addAttribute("product", product);
        assertEquals("editProduct", viewName);
    }

    @Test
    void testEditProductPost() {
        when(service.edit(product.getProductId(), product)).thenReturn(product);

        String viewName = productController.editProductPost(product.getProductId(), product, model);

        verify(service).edit(product.getProductId(), product);
        assertEquals("redirect:/product/list", viewName);
    }
}