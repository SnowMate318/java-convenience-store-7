package store;

import java.util.ArrayList;
import java.util.List;

public class Store {

    static final private String NULL = "null";

    private final StoredProducts storedProducts;
    private final StoredPromotions storedPromotions = StoredPromotions.getInstance();
    private final StoreValidator validator;
    List<Purchase> purchases = new ArrayList<>();
    InputView inputView = new InputView();
    String storeName = "";

    private boolean isMembership = false;
    private int price = 0;

    Store(String storeName, StoredProducts storedProducts) {
        this.storeName = storeName;
        this.storedProducts = storedProducts;
        this.validator = new StoreValidator(storedProducts);
    }

    public String getStoreName() {
        return storeName;
    }

    public void setIsMembership(boolean result) {
        this.isMembership = result;
    }

    public void purchase(List<BuyProduct> buyProducts) {

        for (BuyProduct buyProduct : buyProducts) {
            purchaseProduct(buyProduct.getProductName(), buyProduct.getQuantity());
        }

    }

    public void purchaseProduct(String productName, int quantity) {

        validator.validateProductExist(productName); // 구매물품에 대한 예외처리
        validator.validateBuyProductCount(productName, quantity); // 구매수량에 대한 예외처리
        Product productOnPromotion = storedProducts.findByProductOnPromotion(productName);

        if (checkNotPromotion(productOnPromotion, quantity)) {
            int remains = purchaseOnPromotion(productOnPromotion, quantity);  // 프로모션 중인 상품을 구매

            purchaseOnCommon(productName, remains);
        }
    }

    private boolean checkNotPromotion(Product productOnPromotion, int quantity) {

        int remains = quantity - productOnPromotion.getProductCount();

        if (remains > 0) {
            return inputView.inputGuideNotPromotion(productOnPromotion.getProductName(), remains);
        }

        return true;
    }

    private int purchaseOnPromotion(Product productOnPromotion, int quantity) {

        int buyQuantity = 0;

        if (productOnPromotion != null) {
            Promotion promotion = getPromotion(productOnPromotion);
            quantity = checkGetPromotionProduct(productOnPromotion, promotion, quantity); // 추가로 받을 수 있는 프로모션 상품이 있으면 더해줌
            buyQuantity = Math.min(productOnPromotion.getProductCount(), quantity); // 상품 갯수와 사야 할 상품의 갯수를 비교하여 그 중 적은 수 만큼 구매
            purchase(buyQuantity, productOnPromotion);
        }
        return getRemains(quantity, buyQuantity);
    }

    private void purchaseOnCommon(String productName, int quantity) {

        if (quantity > 0) {

            Product product = storedProducts.findByProductNotOnPromotion(productName);
            if (product != null) {
                Purchase result = product.buy(quantity);
                purchases.add(result);
            }
        }
    }

    private int getRemains(int quantity, int buyQuantity) {

        return quantity - buyQuantity;
    }

    private void purchase(int buyQuantity, Product productOnPromotion) {

        Purchase result = productOnPromotion.buy(buyQuantity);
        price += result.getPrice();
        purchases.add(result);

    }


    private Promotion getPromotion(Product product) {

        String promotionName = product.getPromotionName();
        return storedPromotions.findByPromotionName(promotionName);

    }

    private int checkGetPromotionProduct(Product productOnPromotion, Promotion promotion, int quantity) {

        if (promotion != null && promotion.checkPromotion(productOnPromotion.getProductCount())) { // 프로모션을 제공받을 수 있는 상태면
            int promotionGet = promotion.checkGetMoreProduct(quantity, productOnPromotion.getProductCount());

            if (promotionGet != 0) {

                if (inputView.inputPromotionGet(productOnPromotion.getProductName(), promotionGet)) { // 더 받을 수 있는 물품 안내: Y를 입력하면 증정물품 받음
                    quantity += promotion.checkGetMoreProduct(quantity, productOnPromotion.getProductCount());
                }
            }
        }
        return quantity;
    }

    public Receipt getReceipt() {
        return new Receipt(storeName, purchases, isMembership);
    }

}
