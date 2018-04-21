package com.epam.labproject.exception;

public class PasysException extends Exception {


  public static final String USER_ALREADY_EXIST = "userAlreadyExist";
  public static final String USER_NOT_FOUND = "userNotFound";
  public static final String NO_SUCH_ROLE = "noSuchRole";
  public static final String CREDIT_CARD_NOT_CHOSEN = "creditCardNotChosen";
  public static final String SOURCE_ACCOUNT_IS_BLOCKED = "sourceAccountIsBlocked";
  public static final String SOURCE_CREDIT_CARD_IS_OUT_DATE = "sourceCreditCardIsOutDate";
  public static final String TARGET_CARD_NOT_CHOSEN = "targetCardNotChosen";
  public static final String TARGET_ACCOUNT_IS_BLOCKED = "targetAccountIsBlocked";
  public static final String TARGET_CARD_IS_OUT_DATE = "targetCardIsOutDate";
  public static final String NOT_ENOUGHT_MONEY_ON_ACCOUNT = "notEnoughMoneyOnAccount";

  private String message;
  private String err = "?error=";

  public PasysException(String message) {
    this.message = message;
  }

  public PasysException() {
    message = "This is default Exception message";
  }

  @Override
  public String getMessage() {
    return this.err + this.message;
  }
}
