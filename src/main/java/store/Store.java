package store;

import java.util.ArrayList;
import java.util.List;

public class Store {

    StoredData storedData = StoredData.getInstance();
    List<Purchase> purchases = new ArrayList<>();

    boolean isMembership = false;
    int membershipDiscount = 0;

    public void buy(String productName, int quantity) {

        //Todo: 예외처리 구매할 양이 많으면 안됨
        //Todo: 프로모션 상품을 우선적으로 구매
        int buyCount = 0;

        Product productOnPromotion = storedData.findByProductOnPromotion(productName);

        if(productOnPromotion != null){

            String promotionName = productOnPromotion.getPromotionName();
            Promotion promotion = storedData.findByPromotionName(promotionName);

            quantity = checkGetPromotionProduct(productOnPromotion, promotion, quantity);

            Purchase result = productOnPromotion.buyProductOnPromotion(quantity);
            purchases.add(result);
            buyCount = result.getQuantity();

        }

        int remains = quantity - buyCount;

        if(remains > 0) {

            Product product = storedData.findByProductNotOnPromotion(productName);
            Purchase result = product.buyProduct(remains);
            purchases.add(result);

        }

    }

    private int checkGetPromotionProduct (Product productOnPromotion, Promotion promotion, int quantity) {

        if(promotion.checkPromotion(quantity)) { // 프로모션을 제공받을 수 있는 상태면

            if(promotion.checkGetMoreProduct(quantity, productOnPromotion.getProductCount()) != 0){
                if(true) { // Todo: Y를 입력하면 증정물품 받음
                    quantity += promotion.checkGetMoreProduct(quantity, productOnPromotion.getProductCount());
                }
            }
        }
        return quantity;
    }

}
