/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.ecommerce.AddToCart
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-19-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An event to add cart items
 *
 * ************************************************************
 * */

package org.xchg.online.ecommerce;

public class AddToCart implements java.io.Serializable
{
    //Add the data that should be posted into the event here.
    //The posted data will follow the format that is present here.
    private Object cartItem;
    private double price;

    public AddToCart()
    {
    }

    public Object getCartItem()
    {
        return cartItem;
    }

    public double getPrice()
    {
        return price;
    }
}

