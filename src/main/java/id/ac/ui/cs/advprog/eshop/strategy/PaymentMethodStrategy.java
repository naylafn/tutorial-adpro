package id.ac.ui.cs.advprog.eshop.strategy;

import java.util.Map;

public interface PaymentMethodStrategy {
    boolean validatePayment(Map<String, String> paymentData);
}
