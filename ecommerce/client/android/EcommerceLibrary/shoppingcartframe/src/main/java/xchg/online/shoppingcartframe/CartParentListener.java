package xchg.online.shoppingcartframe;

import xchg.online.ecommerce.shoppingcart.data.ShoppingCart;

/**
 * Created by rsankarx on 07/12/16.
 */

public interface CartParentListener {
    public void shoppingCartInitialized(ShoppingCart cart);
    public void cancelCart();
    public void checkoutCart();
    public void updateShoppingCartItem(int count);
}
