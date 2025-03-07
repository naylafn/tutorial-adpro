package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.*;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Ensures mocks are properly initialized
public class PaymentServiceTest {
    @InjectMocks
    PaymentServiceImpl paymentService; // Use PaymentServiceImpl, not PaymentService interface

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    List<Payment> payments;
    List<Product> products;
    Order order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        payments = new ArrayList<>();
        products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");

        Map<String, String> voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> bankPaymentData = new HashMap<>();
        bankPaymentData.put("bankName", "Mandiri");
        bankPaymentData.put("referenceCode", "013105");

        Payment payment1 = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.VOUCHER_CODE.getValue(), voucherPaymentData);
        Payment payment2 = new Payment("3f8b1d12-6e4c-4d9b-8f4e-0a9c1e7d5f3b", order, PaymentMethod.BANK_TRANSFER.getValue(), bankPaymentData);
        payments.add(payment1);
        payments.add(payment2);
    }

    @Test
    void testAddPayment() {
        Payment payment = payments.get(0);
        Payment result = paymentService.addPayment(order, PaymentMethod.VOUCHER_CODE.getValue(), Map.of("voucherCode", "ESHOP1234ABC5678"));

        assertNotNull(result);
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(payment.getMethod(), result.getMethod());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(0);
        when(orderRepository.findById(payment.getId())).thenReturn(order);
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());
        verify(paymentRepository).save(payment);
        verify(orderRepository).save(order);
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(0);
        when(orderRepository.findById(payment.getId())).thenReturn(order);
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
        verify(paymentRepository).save(payment);
        verify(orderRepository).save(order);
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = payments.get(0);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "MEOW");
        });

        verify(orderRepository, times(0)).save(any(Order.class));
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testGetPayment() {
        Payment payment = payments.get(0);
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment result = paymentService.getPayment(payment.getId());

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
        verify(paymentRepository).findById(payment.getId());
    }

    @Test
    void testGetPaymentIfNotFound() {
        doReturn(null).when(paymentRepository).findById("zczc");
        assertNull(paymentService.getPayment("zczc"));
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.getAllPayments()).thenReturn(Collections.emptyList());
        assertTrue(paymentService.getAllPayments().isEmpty());
        verify(paymentRepository).getAllPayments();
    }
}
