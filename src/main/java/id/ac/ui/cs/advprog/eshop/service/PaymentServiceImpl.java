package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.*;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;

import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentDetails) {
        Payment payment = new Payment("7ff4472c-9f47-4612-9a7c-e4ea361df456", order, method, paymentDetails);
        paymentRepository.save(payment);
        return payment;
    }
    @Override
    public Payment setStatus(Payment payment, String status) {
        Order order = orderRepository.findById(payment.getId());
        if (order != null) {
            if (status.equals("SUCCESS")) {
                order.setStatus(OrderStatus.SUCCESS.getValue());
            } else if (status.equals("REJECTED")) {
                order.setStatus(OrderStatus.FAILED.getValue());
            } else {
                throw new IllegalArgumentException();
            }
            orderRepository.save(order);
            payment.setStatus(status);
            paymentRepository.save(payment);
        } else {
            throw new IllegalArgumentException();
        }
        return payment;
    }
    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }
}