package store;

public enum PromotionIndex {

    PROMOTION_NAME(0),
    BUY_PRODUCT_COUNT(1),
    GET_PRODUCT_COUNT(2),
    START_DATE(3),
    END_DATE(4);

    private final int index;

    PromotionIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
