package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private HashMap<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        this.paymentData = new HashMap<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        products.add(product2);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
    }

    void setUpValidVoucherCode() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    void setUpValidBankTransfer() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "013105");
    }

    @Test
    public void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "Cash", paymentData);
        });
    }

    @Test
    public void testCreatePaymentNullVoucherCode() {
        setUpValidVoucherCode();
        paymentData.put("voucherCode", null);

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "voucherCode", paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", orderStatus);
    }

    @Test
    public void testCreatePaymentVoucherCodeInvalidLength() {
        setUpValidVoucherCode();
        paymentData.put("voucherCode", "ESHOP12345");

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "voucherCode", paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", orderStatus);
    }

    @Test
    public void testCreatePaymentVoucherCodeInvalidStart() {
        setUpValidVoucherCode();
        paymentData.put("voucherCode", "SHOPE1234ABC5678");

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "voucherCode", paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", orderStatus);
    }

    @Test
    public void testCreatePaymentVoucherCodeNotContainEightNumber() {
        setUpValidVoucherCode();
        paymentData.put("voucherCode", "SHOPE1234ABCD567");

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "voucherCode", paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", orderStatus);
    }

    @Test
    public void testCreatePaymentValidVoucherCode() {
        setUpValidVoucherCode();
        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "voucherCode", paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", orderStatus);
    }

    @Test
    public void testCreatePaymentWithEmptyBankName() {
        setUpValidBankTransfer();
        paymentData.put("bankName", ""); // Empty string

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "bankTransfer", paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", orderStatus);
    }

    @Test
    public void testCreatePaymentWithNullBankName() {
        setUpValidBankTransfer();
        paymentData.put("bankName", null);

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "bankTransfer", paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", orderStatus);
    }

    @Test
    public void testCreatePaymentEmptyReferenceCode(){
        setUpValidBankTransfer();
        paymentData.put("referenceCode", "");
        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "bankTransfer", paymentData);

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", orderStatus);
    }

    @Test
    public void testCreatePaymentNullReferenceCode(){
        setUpValidBankTransfer();
        paymentData.put("referenceCode", null);
        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "bankTransfer", paymentData);

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", orderStatus);
    }

    @Test
    public void testCreatePaymentValidBankTransfer(){
        setUpValidBankTransfer();
        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, "voucherCode", paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", orderStatus);
    }
}