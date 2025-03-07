package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.*;

public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("payment is null");
        }

        int i = 0;
        for (Payment savedPayment : paymentData) {
            if(savedPayment.getId().equals(payment.getId())) {
                paymentData.remove(i);
                paymentData.add(i, payment);
                return payment;
            }
            i += 1;
        }
        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String paymentId) {
        for (Payment savedPayment : paymentData) {
            if(savedPayment.getId().equals(paymentId)) {
                return savedPayment;
            }
        }
        return null;
    }

    public List<Payment> getAllPayments() {
        return paymentData;
    }
}
