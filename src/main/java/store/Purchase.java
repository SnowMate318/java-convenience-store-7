package store;

public class Purchase {

    private String productName; //구매품목 이름
    private int quantity; //구매수량
    private int promotionedQuantity; // 프로모션 적용된 구매수량
    private int price; // 구매가격

    private String promotionName; // 프로모션 이름
    private int promotionGet = 0; // 프로모션 증정상품 수
    private int promotionDiscount = 0; // 프로모션 할인금액

    Purchase (String productName, int quantity, int promotionedQuantity, int price) {

        this.promotionName = productName;
        this.quantity = quantity;
        this.promotionedQuantity = promotionedQuantity;
        this.price = price;

    }

    public void setPromotion (String promotionName, int promotionGet, int promotionDiscount) {
        this.promotionName = promotionName;
        this.promotionGet = promotionGet;
        this.promotionDiscount = promotionDiscount;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getPrice () {
        return this.price;
    }

    public int getPromotionGet () {
        return this.promotionGet;
    }


}
