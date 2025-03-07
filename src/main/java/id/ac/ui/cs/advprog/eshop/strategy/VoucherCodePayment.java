package id.ac.ui.cs.advprog.eshop.strategy;

import java.util.Map;

public class VoucherCodePayment implements PaymentMethodStrategy {
    @Override
    public boolean validatePayment(Map<String, String> paymentData) {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.isEmpty()) {
            return false;
        }
        if (voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int counter = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                counter++;
            }
        }
        return counter == 8;
    }
}
