package com.example.onlinebookstore.Request;

import java.util.List;

public class OrderRequest {
    private int paymentMethodId;
    private int customerId;
    private int shippingMethodId;
    private String address;

    private List<OrderDetailsRequest> detailDTOList;

    public OrderRequest(int paymentMethodId, int customerId, int shippingMethodId, String address, List<OrderDetailsRequest> detailDTOList) {
        this.paymentMethodId = paymentMethodId;
        this.customerId = customerId;
        this.shippingMethodId = shippingMethodId;
        this.address = address;
        this.detailDTOList = detailDTOList;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getShippingMethodId() {
        return shippingMethodId;
    }

    public void setShippingMethodId(int shippingMethodId) {
        this.shippingMethodId = shippingMethodId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDetailsRequest> getDetailDTOList() {
        return detailDTOList;
    }

    public void setDetailDTOList(List<OrderDetailsRequest> detailDTOList) {
        this.detailDTOList = detailDTOList;
    }
}
