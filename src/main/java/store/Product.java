package store;



public class Product {

    static final private String NULL = "null";

    String productName;
    int productPrice;
    int productCount;
    Promotion promotion;

    ProductValidator validator = new ProductValidator();

    ///프로모션이 있는 상품일 경우
    Product(String productName, int productPrice, int productCount) {

        validate(productName, productPrice, productCount);

        this.productName = productName;
        this.productCount = productCount;
        this.productPrice = productPrice;

    }

    ///프로모션이 없는 상품일 경우
    Product(String productName, int productPrice, int productCount, Promotion promotion) {

        validate(productName, productPrice, productCount);

        this.productName = productName;
        this.productCount = productCount;
        this.productPrice = productPrice;
        this.promotion = promotion;
    }

    public String getProductName() {
        return this.productName;
    }

    public int getProductCount () {
        return this.productPrice;
    }

    public String getPromotionName() {
        if (this.productName == null) {
            return NULL;
        }
        return this.productName;
    }

    private void validate(String productName, int productPrice, int productCount) {

        validator.validateName(productName);
        validator.validatePrice(productPrice);
        validator.validateCount(productCount);

    }

    public Purchase buy ( int quantity) {

        if(this.productName.equals(NULL)){
            return buyProduct(quantity);
        }

        return buyOnPromotion(quantity);
    }

    /// 프로모션 진행중인 상품 구매
     private Purchase buyOnPromotion(int quantity) {

        //Todo: 예외처리 프로모션이 있는지
        this.productCount -= quantity;
        String promotionName = promotion.getPromotionName();
        int promotionedCount = quantity - promotion.getNotPromotionedProduct(quantity);
        int promotionGetCount = promotion.getPromotionedProduct(quantity);
        int price = getPrice(quantity);
        int discount = getPrice(promotion.getPromotionedProduct(quantity));

        Purchase purchaseData = new Purchase(productName,quantity,promotionedCount,price);
        purchaseData.setPromotion(promotionName, promotionGetCount, discount);

        return purchaseData;
    }

    /// 일반 상품 구매
    private Purchase buyProduct(int quantity) {

        //예외처리: 프로덕트카운트가 양보다 더 많은지
        this.productCount -= quantity;

        return new Purchase(productName,quantity,0,getPrice(quantity));

    }


    private int getPrice(int quantity) {
        return quantity * this.productPrice;
    }


}
