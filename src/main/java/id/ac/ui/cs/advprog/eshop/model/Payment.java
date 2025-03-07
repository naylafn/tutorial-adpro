package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.strategy.BankTransferPayment;
import id.ac.ui.cs.advprog.eshop.strategy.PaymentMethodStrategy;
import id.ac.ui.cs.advprog.eshop.strategy.VoucherCodePayment;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class Payment {
    String id;
    Order order;
    String method;
    Map<String, String> paymentData;
    String status;
    private PaymentMethodStrategy paymentStrategy;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = new HashMap<>(paymentData);
        this.status = PaymentStatus.PENDING.getValue();

        if (PaymentMethod.contains(method)) {
            validateMethod(method);
        } else {
            throw new IllegalArgumentException("Invalid method: " + method);
        }
    }

    public void validateMethod(String method) {
        switch (PaymentMethod.valueOf(method)) {
            case PaymentMethod.VOUCHER_CODE:
                this.paymentStrategy = new VoucherCodePayment();
                break;

            case PaymentMethod.BANK_TRANSFER:
                this.paymentStrategy = new BankTransferPayment();
                break;

            default:
                break;
        }

        if (paymentStrategy.validatePayment(paymentData)) {
            this.status = PaymentStatus.SUCCESS.getValue();
            this.order.setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
            this.order.setStatus(OrderStatus.FAILED.getValue());
        }
    }
}