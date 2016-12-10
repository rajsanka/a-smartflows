/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.ecommerce.CartManager
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-19-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A Manager for the cart
 *
 * ************************************************************
 * */

package org.xchg.online.ecommerce;

import java.util.List;

public class CartManager
{
    public void addToCart(AddToCart evt, Cart data)
        throws Exception
    {
        //Add your code to process the event here.
        //data is the data to which the event is posted
        //evt is the event posted.
        
        CartItem item = new CartItem(data.getCartName(), evt.getPrice());
        data.addItem(item, evt.getPrice());
        StatusMessage msg = new StatusMessage("Added item to cart: " + data.getCartName());
    }

    public void removeFromCart(RemoveFromCart evt, CartItem data)
        throws Exception
    {
        //shd be a straight forward change in state of the item
    }

    public void getCartItems(GetCartItems evt, Cart cart, List<CartItem> items)
        throws Exception
    {
        CartDetails det = new CartDetails(cart, items);
    }

    public void getCartItemsService(Cart cart, List<CartItem> items, List objects, List summary)
        throws Exception
    {
        if ((cart == null) || (items == null) || (summary == null) || (objects == null))
            return;

        summary.add(new Double(cart.getTotalItems()));
        summary.add(new Double(cart.getTotalPrice()));

        for (CartItem item : items)
        {
            objects.add(item.getActualItem());
        }
    }

    public void setupMessage(Cart cart, List<CartItem> items)
        throws Exception
    {
        if (cart != null)
        {
            System.out.println("Sending some email " + cart);
            String addToNotification = "Cart: " + cart.getCartName() + "\n";
            addToNotification += "_________________________________________\n";
            addToNotification += "   Item                 Quantity         \n";
            addToNotification += "_________________________________________\n";
            for (int i = 0; (items != null) && (i < items.size()); i++)
            {
                CartItem item = items.get(i);
                addToNotification += " " + item.getActualItem() + "           1           \n";
            }
            addToNotification += "_________________________________________\n";

            System.out.println(addToNotification);
            cart.setupNotification(addToNotification);
        }
    }
}

