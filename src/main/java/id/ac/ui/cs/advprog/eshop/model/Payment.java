package id.ac.ui.cs.advprog.eshop.model;

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

        if ("VOUCHER_CODE".equals(method)) {
            this.status = validateVoucherCode();
        } else if ("BANK_TRANSFER".equals(method)) {
            this.status = validateBankTransfer();
        } else {
            this.status = "REJECTED";
        }
    }

    public void setStatus(String status) {
        if (!status.equals("SUCCESS") && !status.equals("REJECTED")) {
            throw new IllegalArgumentException();
        }
        this.status = status;
    }

    private String validateVoucherCode() {
        String code = paymentData.get("voucherCode");
        if (code == null) return "REJECTED";
        if (code.length() != 16) return "REJECTED";
        if (!code.startsWith("ESHOP")) return "REJECTED";
        long digitCount = code.chars().filter(Character::isDigit).count();
        if (digitCount != 8) return "REJECTED";
        return "SUCCESS";
    }

    private String validateBankTransfer() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bankName == null || bankName.isEmpty()) return "REJECTED";
        if (referenceCode == null || referenceCode.isEmpty()) return "REJECTED";
        return "SUCCESS";
    }
}