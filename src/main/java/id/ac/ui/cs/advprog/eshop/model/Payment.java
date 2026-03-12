package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;

        if (PaymentMethod.VOUCHER_CODE.getValue().equals(method)) {
            this.status = validateVoucherCode();
        } else if (PaymentMethod.BANK_TRANSFER.getValue().equals(method)) {
            this.status = validateBankTransfer();
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
        }
    }

    public void setStatus(String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        }
        this.status = status;
    }

    private String validateVoucherCode() {
        String code = paymentData.get("voucherCode");
        if (code == null) return PaymentStatus.REJECTED.getValue();
        if (code.length() != 16) return PaymentStatus.REJECTED.getValue();
        if (!code.startsWith("ESHOP")) return PaymentStatus.REJECTED.getValue();
        long digitCount = code.chars().filter(Character::isDigit).count();
        if (digitCount != 8) return PaymentStatus.REJECTED.getValue();
        return PaymentStatus.SUCCESS.getValue();
    }

    private String validateBankTransfer() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bankName == null || bankName.isEmpty()) return PaymentStatus.REJECTED.getValue();
        if (referenceCode == null || referenceCode.isEmpty()) return PaymentStatus.REJECTED.getValue();
        return PaymentStatus.SUCCESS.getValue();
    }
}