package store;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private final String storeName;
    private final List<Purchase> purchases;
    int totalCount;
    private int totalPrice = 0;
    private int promotionDiscount = 0;
    private int membershipDiscount = 0;
    private  int finalPrice = 0;

    static final private double MEMBERSHIP_RATE = 0.3;
    static final private int MAX_MEMBERSHIP_DISCOUNT = 8000;

    Receipt(String storeName, List<Purchase> purchases, boolean isMembership) {

        this.storeName = storeName;
        this.purchases = purchases;
        setPrices(isMembership);
    }

    String getStoreName () {
        return this.storeName;
    }

    /// 구매한 상품 리스트
    public List<BuyProduct> getPurchasedProducts() {

        List<BuyProduct> purchasedProducts = new ArrayList<>();
        List<String> purchasedProductNames = getPurchasedProductNames();

        for (String productName : purchasedProductNames) {
            purchasedProducts.add(new BuyProduct(productName, getProductCount(productName), getProductPrice(productName)));
        }
        return purchasedProducts;
    }

    ///프로모션 증정상품 리스트
    public List<BuyProduct> getPurchasedProductsOnPromotion() {

        List<BuyProduct> purchasedProductsOnPromotion = new ArrayList<>();
        for (Purchase purchase : purchases) {
            if (purchase.getPromotionGet() != 0) {
                purchasedProductsOnPromotion.add(new BuyProduct(purchase.getPromotionName(), purchase.getPromotionGet()));
            }
        }
        return purchasedProductsOnPromotion;
    }

    private void setPrices (boolean isMembership) {

        setTotalCount();
        setTotalPrice();
        if(isMembership) {
            setMembershipDiscount(totalPrice);
        }
        setPromotionDiscount();
        setFinalPrice();
    }

    private List<String> getPurchasedProductNames() {
        List<String> purchasedProductNames = new ArrayList<>();
        String priviousPurchasedProductName = "";

        for (Purchase purchase : purchases) {
            if (!priviousPurchasedProductName.equals(purchase.getProductName())) {
                purchasedProductNames.add(purchase.getProductName());
            }
            priviousPurchasedProductName = purchase.getProductName();
        }
        return purchasedProductNames;
    }

    private int getProductCount(String productName) {
        int count = 0;
        for (Purchase purchase : purchases) {
            if (purchase.getProductName().equals(productName)) {
                count += purchase.getQuantity();
            }
        }
        return count;
    }

    private int getProductPrice(String productName) {
        int price = 0;
        for (Purchase purchase : purchases) {
            if (purchase.getProductName().equals(productName)) {
                price += purchase.getPrice();
            }
        }
        return price;
    }

    private void setTotalPrice() {
        int price = 0;
        for (Purchase purchase : purchases) {
            price += purchase.getPrice();
        }
        this.totalPrice = price;
    }


    private void setTotalCount() {
        int count = 0;
        for (Purchase purchase : purchases) {
            count += purchase.getQuantity();
        }
        this.totalCount = count;
    }

    private void setPromotionDiscount() {

        int price = 0;

        for (Purchase purchase : purchases) {
            price += purchase.getPromotionDiscount();
        }

        this.promotionDiscount = price;
    }

    private void setMembershipDiscount (int totalPrice) {

        int price = (int) (totalPrice * MEMBERSHIP_RATE);

        price = Math.min(price, MAX_MEMBERSHIP_DISCOUNT);

        this.membershipDiscount = price;
    }

    public int getTotalCount () {
        return this.totalCount;
    }

    public int getTotalPrice () {
        return this.totalPrice;
    }

    public int getPromotionDiscount () {
        return this.promotionDiscount;
    }

    public int getMembershipDiscount () {
        return this.membershipDiscount;
    }

    public int getFinalPrice () {
        return this.finalPrice;
    }

    private void setFinalPrice () {
        finalPrice = totalPrice - promotionDiscount - membershipDiscount;
    }

}
