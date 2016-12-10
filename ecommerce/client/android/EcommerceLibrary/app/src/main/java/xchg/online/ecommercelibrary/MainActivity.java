package xchg.online.ecommercelibrary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xchg.online.baseframe.activity.BaseActivity;

import xchg.online.ecommerce.shoppingcart.data.ShoppingCart;
import xchg.online.shoppingcartframe.CartDetails;
import xchg.online.shoppingcartframe.CartParentListener;

public class MainActivity extends BaseActivity implements CartParentListener {

    public CartDetails cartFragment;

    @Override
    protected String getScreenName() {
        return "Main";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (cartFragment == null) cartFragment = CartDetails.newInstance(null, null, null);

        changeView(cartFragment);
    }

    private void changeView(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frag = fragmentManager.findFragmentById(R.id.activity_main);
        if ((frag == null) || (frag.getId() != fragment.getId())) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.activity_main, fragment);
            transaction.commit();
        }
    }

    @Override
    public void shoppingCartInitialized(ShoppingCart cart) {

    }

    @Override
    public void cancelCart() {

    }

    @Override
    public void checkoutCart() {

    }

    @Override
    public void updateShoppingCartItem(int count) {

    }
}
