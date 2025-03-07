package id.ac.ui.cs.advprog.eshop.strategy;

import java.util.Map;

public class BankTransferPayment implements PaymentMethodStrategy {
    @Override
    public boolean validatePayment(Map<String, String> paymentData) {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        return bankName != null && !bankName.isEmpty() && referenceCode != null && !referenceCode.isEmpty();
    }
}
