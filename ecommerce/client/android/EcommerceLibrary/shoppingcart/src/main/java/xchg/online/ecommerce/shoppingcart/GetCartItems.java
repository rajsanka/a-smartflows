package xchg.online.ecommerce.shoppingcart;

import android.app.Activity;

import org.anon.smart.client.SmartEvent;
import org.anon.smart.client.SmartResponseListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rsankarx on 18/11/16.
 */

public class GetCartItems extends SmartEvent {

    public interface MyCartItemListener {
        public void onCartItem(String id, String actual, boolean last);
        public void onEndOfCartItems();
        public void onError(String msg);
        public void setCount(int count);
        public void setTotalPrice(double totalPrice);
    }

    public class CartItemSmartListener implements SmartResponseListener {

        MyCartItemListener listener;

        CartItemSmartListener(MyCartItemListener l) {
            listener = l;
        }

        private void callItemListener(Object item, boolean last) {
            Map mitem = (Map) item;
            String id = mitem.get("itemID").toString();
            String actualitem = mitem.get("actualItem").toString();
            listener.onCartItem(id, actualitem, last);
        }

        @Override
        public void handleResponse(List list) {
            Map response = (Map) list.get(0);
            Double total = (Double) response.get("totalItems");
            listener.setCount(total.intValue());
            Double totalPrice = (Double) response.get("totalPrice");
            if (totalPrice != null) listener.setTotalPrice(totalPrice);
            List items = (List) response.get("items");
            for (int i = 0; i < (items.size() - 1); i++) {
                Object item = items.get(i);
                callItemListener(item, false);
            }

            if (items.size() > 0) {
                Object item = items.get(items.size() - 1);
                callItemListener(item, true);
            }

            listener.onEndOfCartItems();
        }

        @Override
        public void handleError(double v, String s) {
            listener.onError(v + ":" + s);
        }

        @Override
        public void handleNetworkError(String s) {
            listener.onError(s);
        }
    }

    private static final String TAG = GetCartItems.class.getSimpleName();

    private static final String FLOW = "ShoppingCartFlow";

    private String cartName;

    public GetCartItems(String cn) {
        super(FLOW);
        cartName = cn;
    }
    @Override
    protected Map<String, Object> getParams() {
        Map<String, Object> parms = new HashMap<>();
        return parms;
    }

    public void postTo(Activity activity, MyCartItemListener listener) {
        super.postEvent(activity, new CartItemSmartListener(listener), "Cart", cartName);
    }
}
