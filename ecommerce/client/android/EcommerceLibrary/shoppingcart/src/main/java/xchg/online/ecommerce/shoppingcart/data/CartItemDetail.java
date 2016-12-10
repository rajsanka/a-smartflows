package xchg.online.ecommerce.shoppingcart.data;

/**
 * Created by rsankarx on 07/12/16.
 */

public class CartItemDetail implements java.io.Serializable {
    private String cartName;
    private String actual;
    private CartProductDetail item;

    public CartItemDetail(String nm, CartProductDetail det) {
        cartName = nm;
        item = det;
        actual = det.getProductId();
    }

    public CartItemDetail(String nm, String prod) {
        actual = prod;
    }

    public void setProductDetail(CartProductDetail det) {
        item = det;
    }

    public String getCartName() { return cartName; }
    public CartProductDetail getItem() { return item; }
    public String getActual() { return actual; }
}
