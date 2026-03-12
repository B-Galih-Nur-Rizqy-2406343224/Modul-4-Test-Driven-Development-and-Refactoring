package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Map<String, String> voucherData;
    private Map<String, String> bankTransferData;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        bankTransferData = new HashMap<>();
        bankTransferData.put("bankName", "BCA");
        bankTransferData.put("referenceCode", "REF123456");
    }

    @Test
    void testSavePayment() {
        Payment payment = new Payment("pay-001", PaymentMethod.VOUCHER_CODE.getValue(), voucherData);
        Payment saved = paymentRepository.save(payment);

        assertEquals(payment.getId(), saved.getId());
        assertEquals(payment.getMethod(), saved.getMethod());
        assertEquals(payment.getStatus(), saved.getStatus());
    }

    @Test
    void testSavePaymentUpdate() {
        Payment payment = new Payment("pay-001", PaymentMethod.VOUCHER_CODE.getValue(), voucherData);
        paymentRepository.save(payment);

        Payment updated = new Payment("pay-001", PaymentMethod.BANK_TRANSFER.getValue(), bankTransferData);
        Payment saved = paymentRepository.save(updated);

        assertEquals(updated.getMethod(), saved.getMethod());
        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    void testFindByIdFound() {
        Payment payment = new Payment("pay-001", PaymentMethod.VOUCHER_CODE.getValue(), voucherData);
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById("pay-001");
        assertNotNull(found);
        assertEquals("pay-001", found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Payment found = paymentRepository.findById("nonexistent");
        assertNull(found);
    }

    @Test
    void testFindAll() {
        Payment p1 = new Payment("pay-001", PaymentMethod.VOUCHER_CODE.getValue(), voucherData);
        Payment p2 = new Payment("pay-002", PaymentMethod.BANK_TRANSFER.getValue(), bankTransferData);
        paymentRepository.save(p1);
        paymentRepository.save(p2);

        List<Payment> all = paymentRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testFindAllEmpty() {
        List<Payment> all = paymentRepository.findAll();
        assertTrue(all.isEmpty());
    }
}