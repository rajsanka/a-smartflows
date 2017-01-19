package xchg.online.ecommerce.shoppingcart.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xchg.online.ecommerce.shoppingcart.AddToCart;
import xchg.online.ecommerce.shoppingcart.CreatePrime;
import xchg.online.ecommerce.shoppingcart.GetCartItems;

/**
 * Created by rsankarx on 08/12/16.
 */

public class ShoppingCart {

    private static final String TAG = ShoppingCart.class.getSimpleName();

    class MyCartAddListener implements AddToCart.AddToCartListener {

        @Override
        public void addedToCart(String s) {
            itemCount++;
            updater.updateCartDetails(itemCount, totalPrice);
        }

        @Override
        public void onError(String s) {

        }
    }

    class MyCreateCartListener implements CreatePrime.CreatePrimeListener {

        @Override
        public void onSuccess(String s) {
            SharedPreferences prefsLoggedInUser = mContext.getSharedPreferences(PREFS_SHOPPINGCART, Context.MODE_PRIVATE);
            prefsLoggedInUser.edit().putString(CART_NAME, cartName).apply();
            cartCreated = true;
        }

        @Override
        public void onError(String s) {

        }
    }

    class MyItemListener implements GetCartItems.MyCartItemListener {

        @Override
        public void setTotalPrice(double tp) {
            totalPrice = tp;
        }

        @Override
        public void setCount(int count) {
            itemCount = count;
        }

        @Override
        public void onEndOfCartItems() {
            updater.updateCartDetails(itemCount, totalPrice);
        }

        @Override
        public void onCartItem(String id, String actual, boolean last) {
            CartItemDetail det = new CartItemDetail(cartName, actual);

            if (last) {
                retriever.retrieveProductDetail(det, updater);
            } else {
                retriever.retrieveProductDetail(det, null);
            }
            items.add(det);
        }

        @Override
        public void onError(String msg) {
            if (msg.contains("Cannot find object")) {
                Log.d(TAG, "Cannot find cart: " + msg + " recreating cart.");
                createNewCart(newCartName);
            } else if (!msg.contains("empty")) {
                retriever.handleError(msg);
            }
        }
    }

    public interface CartItemRetriever {
        public void retrieveProductDetail(CartItemDetail detail, CartDisplayUpdate listener);
        public void handleError(String msg);
    }

    public interface CartDisplayUpdate {
        public void update();
        public void updateCartDetails(int itemCount, double total);
    }

    private static final String PREFS_SHOPPINGCART = "ShoppingCart";
    private static final String CART_NAME = "cartName";

    private Activity mContext;
    private String newCartName;
    private String cartName;
    private int itemCount;
    private double totalPrice;
    private boolean cartCreated;

    private MyCreateCartListener createCartListener;
    private MyCartAddListener addListener;
    private List<CartItemDetail> items;
    private CartItemRetriever retriever;
    private CartDisplayUpdate updater;

    public ShoppingCart(Activity ctx, String cn, CartItemRetriever ret, CartDisplayUpdate upd) {
        mContext = ctx;
        cartName = cn;
        retriever = ret;
        updater = upd;
        SharedPreferences prefsLoggedInUser = mContext.getSharedPreferences(PREFS_SHOPPINGCART, Context.MODE_PRIVATE);
        String storedcn = prefsLoggedInUser.getString(CART_NAME, null);
        addListener = new MyCartAddListener();
        items = new ArrayList<>();
        newCartName = cn;
        Log.d(TAG, "Got stored cart name as: " + storedcn);
        if (storedcn == null) {
            createNewCart(cn);
        } else {
            cartName = storedcn;
            cartCreated = true;
            retrieveCartItems();
        }
    }

    public void createNewCart(String cn) {
        Log.d(TAG, "Creating cart for: " + cn);
        cartName = cn;
        cartCreated = false;
        createCartListener = new MyCreateCartListener();
        CreatePrime create = new CreatePrime(cartName);
        create.postTo(mContext, createCartListener);
    }

    public void addToCart(CartProductDetail prod) {
        AddToCartTask task = new AddToCartTask(prod);
        task.execute();
    }

    public void retrieveCartItems() {
        GetCartItemsTask task = new GetCartItemsTask();
        task.execute();
    }

    public void startNewCart(String cn) {
        Log.d(TAG, "New cart requested. Creating cart for: " + cn);

        SharedPreferences prefsLoggedInUser = mContext.getSharedPreferences(PREFS_SHOPPINGCART, Context.MODE_PRIVATE);
        prefsLoggedInUser.edit().remove(CART_NAME).apply();
        cartCreated = false;
        items = new ArrayList<>();
        itemCount = 0;
        totalPrice = 0;
        cartName = "";
        updater.updateCartDetails(itemCount, totalPrice);
        updater.update();
        createNewCart(cn);
    }

    public List<CartItemDetail> getItems() {
        return items;
    }

    public int getItemCount() { return itemCount; }
    public double getTotalPrice() { return totalPrice; }

    public class AddToCartTask extends AsyncTask<Void, Void, Void> {

        private CartProductDetail product;

        AddToCartTask(CartProductDetail prod) {
            product = prod;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (cartCreated) {
                AddToCart cart = new AddToCart(cartName, product.getProductId(), product.getPrice());
                cart.postTo(mContext, addListener);
            }
            return null;
        }
    }

    public class GetCartItemsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            items.clear();
            GetCartItems get = new GetCartItems(cartName);
            get.postTo(mContext, new MyItemListener());
            return null;
        }
    }

    public String getCartName() { return cartName; }

    public void clearCartSave() {
        SharedPreferences prefsLoggedInUser = mContext.getSharedPreferences(PREFS_SHOPPINGCART, Context.MODE_PRIVATE);
        prefsLoggedInUser.edit().remove(CART_NAME).apply();
    }
}
