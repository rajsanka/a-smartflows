package xchg.online.shoppingcartframe;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xchg.online.ecommerce.shoppingcart.data.CartItemDetail;

/**
 * Created by rsankarx on 07/12/16.
 */

public class CartDetailsAdapter extends RecyclerView.Adapter<CartDetailsAdapter.CartItemViewHolder> implements URLDownloadListener {

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productTitle;
        TextView productDescription;
        TextView productCost;

        CartItemViewHolder(View view) {
            super(view);

            productImage = (ImageView) view.findViewById(R.id.itemImage);
            productTitle = (TextView) view.findViewById(R.id.itemName);
            productDescription = (TextView) view.findViewById(R.id.itemDescription);
            productCost = (TextView) view.findViewById(R.id.itemPrice);
        }
    }

    private Context mContext;
    private List<CartItemDetail> cartItems;

    public CartDetailsAdapter(Context mContext, List<CartItemDetail> objectList) {
        this.mContext = mContext;
        this.cartItems = objectList;
    }

    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_layout, parent, false);

        return new CartItemViewHolder(itemView);
    }

    public void setCartItems(List<CartItemDetail> details) {
        cartItems.clear();
        cartItems.addAll(details);
    }

    @Override
    public void onBindViewHolder(final CartItemViewHolder holder, int position) {
        CartItemDetail object = cartItems.get(position);

        if (object.getItem() != null) {
            List<String> images = object.getItem().getImageURLs();
            if (images == null) {
                images = new ArrayList<>();
                images.add("https://placeholdit.imgix.net/~text?txtsize=9&txt=100%C3%97100&w=100&h=100");
            }

            AsychImageDownload download = new AsychImageDownload(images.get(0), holder.productImage, this, 4);
            download.queue();
            download.startProcessing();

            holder.productTitle.setText(object.getItem().getProductId());
            holder.productDescription.setText(object.getItem().getDescription());
            holder.productCost.setText(object.getItem().getPrice() + "");
        }
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        /*PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.wsobjectmenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new WSMenuItemClickListener());
        popup.show();*/
    }

    /**
     * Click listener for popup menu items
     */
    class WSMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public WSMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int menuid = menuItem.getItemId();
            /*if (menuid == R.id.resolve_alarm) {
                return true;
            } else if (menuid == R.id.resolve_error) {
                return true;
            } else {
            }*/

            return false;
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
