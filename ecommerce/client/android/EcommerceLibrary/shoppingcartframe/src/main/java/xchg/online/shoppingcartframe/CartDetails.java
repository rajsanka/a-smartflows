package xchg.online.shoppingcartframe;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import xchg.online.ecommerce.shoppingcart.data.CartItemDetail;
import xchg.online.ecommerce.shoppingcart.data.CartProductDetail;
import xchg.online.ecommerce.shoppingcart.data.ShoppingCart;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartDetails extends Fragment implements ShoppingCart.CartDisplayUpdate {

    private OnFragmentInteractionListener mListener;

    private String cartName;
    private List<CartItemDetail> data;
    private RecyclerView recyclerView;
    private CartDetailsAdapter adapter;
    private Activity mParentActivity;
    private CartParentListener parentListener;
    private ShoppingCart mCart;
    private ShoppingCart.CartItemRetriever retriever;

    private TextView itemCount;
    private TextView itemCost;

    public CartDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment CartDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static CartDetails newInstance(Bundle args, ShoppingCart.CartItemRetriever ret, String name) {
        CartDetails fragment = new CartDetails();
        fragment.setArguments(args);
        fragment.retriever = ret;
        fragment.cartName = name;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cart_details, container, false);

        itemCount = (TextView) rootView.findViewById(R.id.totalItems);
        itemCost = (TextView) rootView.findViewById(R.id.totalPrice);

        Button cancel = (Button) rootView.findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentListener.cancelCart();
            }
        });

        Button placeorder = (Button) rootView.findViewById(R.id.placeorderbutton);
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentListener.checkoutCart();
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.cart_details);

        data = new ArrayList<>();
        adapter = new CartDetailsAdapter(mParentActivity, data);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mParentActivity, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //retrieveCartItems();

        return rootView;
    }

    public void retrieveCartItems() {
        mCart.retrieveCartItems();
        /*List<String> urls = new ArrayList<>();
        urls.add("https://placeholdit.imgix.net/~text?txtsize=9&txt=100%C3%97100&w=100&h=100");
        CartProductDetail prod = new CartProductDetail("Saree1", "Some bengal saree", 10000.00, urls);
        for (int i = 0; i < 5; i++) {
            CartItemDetail det = new CartItemDetail("test", prod);
            data.add(det);
        }*/
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        }

        if ((activity instanceof Activity) && (activity instanceof CartParentListener)) {
            mParentActivity = (Activity) activity;
            parentListener = (CartParentListener)activity;
        }else {
            throw new ClassCastException(activity.toString()
                    + " must implement LoginParentActivity");
        }

        mCart = new ShoppingCart(mParentActivity, cartName, retriever, this);
        parentListener.shoppingCartInitialized(mCart);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void update() {
        if (itemCount != null) itemCount.setText(mCart.getItemCount() + "");
        if (itemCost != null)  itemCost.setText(mCart.getTotalPrice() + "");
        parentListener.updateShoppingCartItem(mCart.getItemCount());
        adapter.setCartItems(mCart.getItems());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateCartDetails(int cnt, double total) {
        if (itemCount != null) itemCount.setText(cnt + "");
        if (itemCost != null)  itemCost.setText(total + "");
        parentListener.updateShoppingCartItem(cnt);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        public GridSpacingItemDecoration() {

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(1, 1, 1, 1);
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public ShoppingCart getShoppingCart() { return mCart; }

}
