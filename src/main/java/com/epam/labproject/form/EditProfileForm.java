package com.epam.labproject.form;


public class EditProfileForm {
    private String oldPassworld;
    private String newPassworld;
    private String repeatPassword;

    public String getNewPassworld() {
        return newPassworld;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String getOldPassworld() {

        return oldPassworld;
    }

    public void setOldPassworld(String oldPassworld) {
        this.oldPassworld = oldPassworld;
    }

    public void setNewPassworld(String newPassworld) {
        this.newPassworld = newPassworld;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
