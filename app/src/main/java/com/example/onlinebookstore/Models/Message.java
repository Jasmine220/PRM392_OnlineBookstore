package com.example.onlinebookstore.Models;

import java.util.Date;

public class Message {
    private int messageId;
    private Long customerId;
    private String messageContent;
    private Date messageDatetime;
    private int sellerId;

    // Các phương thức getter và setter ở đây

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getMessageDatetime() {
        return messageDatetime;
    }

    public void setMessageDatetime(Date messageDatetime) {
        this.messageDatetime = messageDatetime;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}
