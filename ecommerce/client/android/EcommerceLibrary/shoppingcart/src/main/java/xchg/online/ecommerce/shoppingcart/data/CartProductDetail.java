package xchg.online.ecommerce.shoppingcart.data;

import java.util.List;

/**
 * Created by rsankarx on 07/12/16.
 */

public class CartProductDetail implements java.io.Serializable {
    private String productId;
    private String category;
    private String description;
    private double price;
    private List<String> imageURLs;

    public CartProductDetail(String id, String desc, double p, List<String> urls) {
        productId = id;
        description = desc;
        price = p;
        imageURLs = urls;
    }

    public String getProductId() { return productId; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public List<String> getImageURLs() { return imageURLs; }

}
