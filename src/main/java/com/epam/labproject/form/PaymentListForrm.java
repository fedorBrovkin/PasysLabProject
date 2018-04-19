package com.epam.labproject.form;

public class PaymentListForrm {
    String data;
    String sourceCard;
    String targetCard;
    String userLogin;

    public PaymentListForrm(String data, String sourceCard, String targetCard, String userLogin) {
        this.data = data;
        this.sourceCard = sourceCard;
        this.targetCard = targetCard;
        this.userLogin = userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setTargetCard(String targetCard) {

        this.targetCard = targetCard;
    }

    public void setSourceCard(String sourceCard) {

        this.sourceCard = sourceCard;
    }

    public void setData(String data) {

        this.data = data;
    }

    public String getSourceCard() {
        return sourceCard;
    }

    public String getData() {
        return data;
    }

    public String getTargetCard() {
        return targetCard;
    }

    public String getUserLogin() {
        return userLogin;
    }
}
