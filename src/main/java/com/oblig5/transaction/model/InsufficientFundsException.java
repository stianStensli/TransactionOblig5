package com.oblig5.transaction.model;

/***
 * Exception called when a user tries to make a transaction without a sufficient amount of funds in their wallet.
 *
 * Todo: Make this class obsolete as this is slower then an early if check, or returning null.
 */
public class InsufficientFundsException extends Exception
{
    private String message;

    public InsufficientFundsException(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}

