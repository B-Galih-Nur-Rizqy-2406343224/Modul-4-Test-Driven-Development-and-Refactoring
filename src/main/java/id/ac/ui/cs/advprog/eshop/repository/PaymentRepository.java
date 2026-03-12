package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        for (int i = 0; i < paymentData.size(); i++) {
            if (paymentData.get(i).getId().equals(payment.getId())) {
                paymentData.set(i, payment);
                return payment;
            }
        }
        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        for (Payment payment : paymentData) {
            if (payment.getId().equals(id)) {
                return payment;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        return paymentData;
    }
}