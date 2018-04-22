package com.epam.labproject.form;


import com.epam.labproject.entity.UnblockRequest;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class UnblockRequestForm {

    private String username;
    private String time;
    private int accNumber;
    private String currentCondition;

    public UnblockRequestForm() {
    }

    public UnblockRequestForm(String username, String time, int accNumber) {
        this.username = username;
        this.time = time;
        this.accNumber = accNumber;
        this.currentCondition =
                "Time: " + time.substring(0, time.length() - 3) + " User: " + username + " Account: "
                        + accNumber;
    }

    public static List<UnblockRequestForm> getFormList(List<UnblockRequest> requests) {
        requests.sort(Comparator.comparing(UnblockRequest::getTime));
        List<UnblockRequestForm> formList = new LinkedList<>();
        for (UnblockRequest request : requests) {
            String username = request.getUser().getLogin();
            String time = request.getTime().toString();
            int account = request.getAccount().getNumber();
            formList.add(new UnblockRequestForm(username, time, account));
        }
        return formList;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public String getStatus() {
        return currentCondition;
    }

    public void setStatus(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAccNumber() {

        return accNumber;
    }

    public void setAccNumber(int account) {
        this.accNumber = account;
    }
}
