package store;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private String storeName;
    private List<Purchase> purchases;
    private int price;
    private int membershipDiscount;

    Receipt(String storeName, List<Purchase> purchases, int price, int membershipDiscount) {
        this.storeName = storeName;
        this.purchases = purchases;
        this.price = price;
        this.membershipDiscount = membershipDiscount;
    }

    private List<Purchase> getPurchasesOnPromotion () {

        List<Purchase> purchasesOnPromotion = new ArrayList<>();
        for(Purchase purchase : purchases) {
            if(purchase.getPromotionGet() != 0) {
                purchasesOnPromotion.add(purchase);
            }
        }
        return  purchasesOnPromotion;
    }

    public void printReceipt () {

    }


}
