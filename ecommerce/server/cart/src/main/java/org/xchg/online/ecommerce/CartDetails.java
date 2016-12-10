/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.ecommerce.CartDetails
 * Author:              rsankar
 * Revision:            1.0
 * Date:                19-09-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of details of the cart sent out
 *
 * ************************************************************
 * */

package org.xchg.online.ecommerce;

import java.util.List;
import java.util.ArrayList;

public class CartDetails implements java.io.Serializable
{
    private String cartName;
    private int totalItems;
    private double totalPrice;
    private List<CartItem> items;

    public CartDetails(Cart cart, List<CartItem> lst)
    {
        cartName = cart.getCartName();
        totalItems = cart.getTotalItems();
        totalPrice = cart.getTotalPrice();
        items = new ArrayList<CartItem>();
        for (int i = 0; i < lst.size(); i++)
            items.add(lst.get(i));
    }
}

