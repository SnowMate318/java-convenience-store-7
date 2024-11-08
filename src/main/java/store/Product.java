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

    /// 프로모션 진행중인 상품 구매
    public Purchase buyProductOnPromotion(int quantity) {

        //Todo: 예외처리 프로모션이 있는지

        if(getNotPromotionCount(quantity) > 0) {
            //Todo: 사용자 안내
        }

        Purchase purchaseData = buyProduct(quantity);
        purchaseData.setPromotion(
                promotion.getPromotionName(), promotion.getPromotionedProduct(quantity), getPrice(promotion.getPromotionedProduct(quantity)));

        return purchaseData;
    }

    /// 일반 상품 구매
    public Purchase buyProduct(int quantity) {

        int buyQuantity = Math.min(this.productCount, quantity);
        this.productCount -= buyQuantity;

        return new Purchase(productName,quantity,getPrice(buyQuantity));

    }


    private int getNotPromotionCount (int quantity) {

        int buyQuantity = Math.min(this.productCount, quantity);

        return promotion.getNotPromotionedProduct(buyQuantity) + (quantity - buyQuantity);

    }


    private int getPrice(int quantity) {
        return quantity * this.productPrice;
    }


}
