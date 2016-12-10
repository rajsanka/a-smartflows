/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.ecommerce.CartItem
 * Author:              rsankar
 * Revision:            1.0
 * Date:                19-09-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An item that will be stored in a cart
 *
 * ************************************************************
 * */

package org.xchg.online.ecommerce;

import java.util.UUID;

public class CartItem implements java.io.Serializable
{
    private UUID itemID;
    private String cartName;
    private Object actualItem;
    private double price;

    public CartItem(String cart, double p)
    {
        cartName = cart;
        itemID = UUID.randomUUID();
        price = p;
    }

    public String getCartItem() { return cartName; }
    public UUID getItemID() { return itemID; }
    public Object getActualItem() { return actualItem; }
    public double getPrice() { return price; }
}

