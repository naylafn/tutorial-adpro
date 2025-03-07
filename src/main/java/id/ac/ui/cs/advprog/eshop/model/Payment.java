package id.ac.ui.cs.advprog.eshop.model;

import enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class Payment {
    String id;
    Order order;
    String method;
    Map<String, String> paymentData;
    String status;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = new HashMap<>(paymentData);
        this.status = PaymentStatus.PENDING.getValue();

        if(PaymentMethod.contains(method)) {
            validateMethod(method);
        } else {
            throw new IllegalArgumentException("Invalid method: " + method);
        }
    }

    public void validateMethod(String method){
        boolean isValid = false;

        switch (PaymentMethod.valueOf(method)) {
            case PaymentMethod.VOUCHER_CODE:
                isValid = validateVoucherCode();
                break;

            case PaymentMethod.BANK_TRANSFER:
                isValid = validateBankTransfer();
                break;

            default:
                break;
        }

        if(isValid){
            this.status = PaymentStatus.SUCCESS.getValue();
            this.order.setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
            this.order.setStatus(OrderStatus.FAILED.getValue());
        }
    }

    public boolean validateVoucherCode(){
        String voucherCode = paymentData.get("voucherCode");
        if(voucherCode == null || voucherCode.isEmpty()){
            return false;
        }

        if(voucherCode.length() != 16){
            return false;
        } else if (!voucherCode.startsWith("ESHOP")){
            return false;
        } else {
            int counter = 0;
            for(int i = 0; i < voucherCode.length(); i++){
                if(Character.isDigit(voucherCode.charAt(i))){
                    counter++;
                }
            }
            return counter == 8;
        }
    }

    public boolean validateBankTransfer(){
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        return bankName != null && referenceCode != null && !bankName.isEmpty() && !referenceCode.isEmpty();
    }

}
