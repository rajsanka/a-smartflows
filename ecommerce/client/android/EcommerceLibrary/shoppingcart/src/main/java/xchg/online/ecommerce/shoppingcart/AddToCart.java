package xchg.online.ecommerce.shoppingcart;

import android.app.Activity;
import android.util.Log;

import org.anon.smart.client.SmartEvent;
import org.anon.smart.client.SmartResponseListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rsankarx on 18/11/16.
 */

public class AddToCart extends SmartEvent {
    private static final String TAG = CreatePrime.class.getSimpleName();

    public static interface AddToCartListener {
        public void addedToCart(String cartName);
        public void onError(String msg);
    }

    class AddToCartSmartListener implements SmartResponseListener {

        AddToCartListener listener;

        AddToCartSmartListener(AddToCartListener l) {
            listener = l;
        }

        public void handleResponse(List responses){
            Log.i(TAG, "Created data: " + responses);
            listener.addedToCart(cartName);
        }

        public void handleError(double code, String context){
            Log.i(TAG, "Error creating data: " + code + ":" + context);
            listener.onError(code + ":" + context);
        }

        public void handleNetworkError(String message){
            Log.i(TAG, "Network error creating data: " + message);
            listener.onError(message);
        }
    }

    private String cartName;
    private Object cartItem;
    private double price;

    private static final String FLOW = "ShoppingCartFlow";

    public AddToCart(String cn, Object item, double p) {
        super(FLOW);
        cartName = cn;
        cartItem = item;
        price = p;
    }

    public Map<String, Object> getParams() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("cartItem", cartItem);
        ret.put("price", price);
        return ret;
    }

    public void postTo(Activity activity, AddToCartListener listener) {
        super.postEvent(activity, new AddToCartSmartListener(listener), "Cart", cartName);
    }
}
