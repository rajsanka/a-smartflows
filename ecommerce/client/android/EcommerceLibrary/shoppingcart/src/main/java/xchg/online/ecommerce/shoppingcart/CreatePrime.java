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

public class CreatePrime extends SmartEvent {
    private static final String TAG = CreatePrime.class.getSimpleName();

    public static interface CreatePrimeListener {
        public void onSuccess(String cartName);
        public void onError(String msg);
    }

    class CreatePrimeSmartListener implements SmartResponseListener {

        CreatePrimeListener listener;

        CreatePrimeSmartListener(CreatePrimeListener l) {
            listener = l;
        }

        public void handleResponse(List responses){
            Log.i(TAG, "Created data: " + responses);
            listener.onSuccess(cartName);
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

    private static final String FLOW = "ShoppingCartFlow";

    public CreatePrime(String cn) {
        super(FLOW);
        cartName = cn;
    }

    public Map<String, Object> getParams() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("create", "Cart");

        Map<String, String> data = new HashMap<>();
        data.put("cartName", cartName);

        ret.put("data", data);
        return ret;
    }

    public void postTo(Activity activity, CreatePrimeListener listener) {
        super.postEvent(activity, new CreatePrimeSmartListener(listener), "FlowAdmin", FLOW);
    }
}
