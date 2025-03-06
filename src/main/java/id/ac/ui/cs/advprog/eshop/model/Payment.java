package id.ac.ui.cs.advprog.eshop.model;

import enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class Payment {
    String id;
    Order order;
    String method;
    Map<String, String> paymentData;
    String status;
    String[] methodList = {"voucherCode", "bankTransfer"};

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = new HashMap<>(paymentData);
        this.status = "PENDING";

        if(Arrays.stream(methodList).noneMatch(item -> item.equals(method))) {
            throw new IllegalArgumentException("Invalid method");
        } else {
            this.method = method;
            validateMethod(method);
        }
    }

    public void validateMethod(String method){
        boolean isValid = false;

        if(method.equals("voucherCode")){
             isValid = validateVoucherCode();
        } else if(method.equals("bankTransfer")){
            isValid = validateBankTransfer();
        }

        if(isValid){
            this.status = "SUCCESS";
            this.order.setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            this.status = "REJECTED";
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
