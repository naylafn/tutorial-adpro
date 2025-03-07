package id.ac.ui.cs.advprog.eshop.model;

import enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enums.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private Map<String, String> paymentData;
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

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
    }

    void setUpValidVoucherCode() {
        paymentData.clear();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    void setUpValidBankTransfer() {
        paymentData.clear();
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

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), orderStatus);
    }

    @Test
    public void testCreatePaymentVoucherCodeInvalidLength() {
        setUpValidVoucherCode();
        paymentData.put("voucherCode", "ESHOP12345");

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), orderStatus);
    }

    @Test
    public void testCreatePaymentVoucherCodeInvalidStart() {
        setUpValidVoucherCode();
        paymentData.put("voucherCode", "SHOPE1234ABC5678");

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), orderStatus);
    }

    @Test
    public void testCreatePaymentVoucherCodeNotContainEightNumber() {
        setUpValidVoucherCode();
        paymentData.put("voucherCode", "SHOPE1234ABCD567");

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), orderStatus);
    }

    @Test
    public void testCreatePaymentValidVoucherCode() {
        setUpValidVoucherCode();
        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), orderStatus);
    }

    @Test
    public void testCreatePaymentWithEmptyBankName() {
        setUpValidBankTransfer();
        paymentData.put("bankName", "");

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), orderStatus);
    }

    @Test
    public void testCreatePaymentWithNullBankName() {
        setUpValidBankTransfer();
        paymentData.put("bankName", null);

        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), orderStatus);
    }

    @Test
    public void testCreatePaymentEmptyReferenceCode(){
        setUpValidBankTransfer();
        paymentData.put("referenceCode", "");
        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), orderStatus);
    }

    @Test
    public void testCreatePaymentNullReferenceCode(){
        setUpValidBankTransfer();
        paymentData.put("referenceCode", null);
        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), orderStatus);
    }

    @Test
    public void testCreatePaymentValidBankTransfer(){
        setUpValidBankTransfer();
        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        String orderStatus = payment.getOrder().getStatus();

        assertSame(payment.getOrder(), order);
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), orderStatus);
    }
}