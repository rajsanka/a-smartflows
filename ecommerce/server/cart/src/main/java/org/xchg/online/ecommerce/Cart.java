/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.ecommerce.Cart
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-19-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A cart filled with items
 *
 * ************************************************************
 * */

package org.xchg.online.ecommerce;

public class Cart implements java.io.Serializable
{
    //This is an automatically generated key for referencing
    //your object. If you change this, be sure to change the
    //corresponding .soa file also.
    
    private String cartName;

    //Add your data fields here. This will be stored and
    //retrieved back when this data is created and updated.
    //Creating a member as transient will not save the data.
    
    private int totalItems;

    private double totalPrice;

    private transient String notificationString;


    public Cart()
    {
        totalItems = 0;
    }

    public String getCartName() { return cartName; }
    public int getTotalItems() { return totalItems; }
    public double getTotalPrice() { return totalPrice; }

    public void addItem(CartItem item, double price)
    {
        totalItems++;
        totalPrice += price;
    }

    public void setupNotification(String str) { notificationString = str; }
    public String toString() { return notificationString; }
}

