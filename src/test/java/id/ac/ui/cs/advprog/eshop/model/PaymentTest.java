package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        this.products.add(product1);

        this.order = new Order("order-123", this.products, 1708560000L, "Safira Sudrajat");
    }

    // ===================== VOUCHER CODE TESTS =====================

    @Test
    void testPaymentVoucherCodeValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("pay-001", PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        assertEquals("pay-001", payment.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testPaymentVoucherCodeInvalidNotStartWithESHOP() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ABCDE1234ABC5678");

        Payment payment = new Payment("pay-002", PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentVoucherCodeInvalidNot16Chars() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234");

        Payment payment = new Payment("pay-003", PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentVoucherCodeInvalidNotEnoughNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");

        Payment payment = new Payment("pay-004", PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentVoucherCodeNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", null);

        Payment payment = new Payment("pay-005", PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentVoucherCodeKeyMissing() {
        Map<String, String> paymentData = new HashMap<>();

        Payment payment = new Payment("pay-006", PaymentMethod.VOUCHER_CODE.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    // ===================== BANK TRANSFER TESTS =====================

    @Test
    void testPaymentBankTransferValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");

        Payment payment = new Payment("pay-101", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentBankTransferEmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF123456");

        Payment payment = new Payment("pay-102", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentBankTransferNullBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "REF123456");

        Payment payment = new Payment("pay-103", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentBankTransferEmptyReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("pay-104", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentBankTransferNullReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", null);

        Payment payment = new Payment("pay-105", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentBankTransferMissingBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("referenceCode", "REF123456");

        Payment payment = new Payment("pay-106", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentBankTransferMissingReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");

        Payment payment = new Payment("pay-107", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentBankTransferBothEmpty() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("pay-108", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    // ===================== GENERAL TESTS =====================

    @Test
    void testSetStatusToRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");

        Payment payment = new Payment("pay-201", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");

        Payment payment = new Payment("pay-202", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}
