package store;

public class StoreValidator {

    static final private String ERROR_INPUT_PRODUCT_NOT_FOUND = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    static final private String ERROR_INPUT_TOO_MANY_PRODUCT_COUNT = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";


    private final StoredProducts storedProducts;

    StoreValidator(StoredProducts storedProducts) {
        this.storedProducts = storedProducts;
    }

    /// 존재하지 않는 상품일경우 예외처리
    public void validateProductExist(String productName) {

        if (storedProducts.findByProductName(productName).isEmpty()) {
            throw new IllegalArgumentException(ERROR_INPUT_PRODUCT_NOT_FOUND);
        }

    }

    /// 너무 많은 상풀을 구매할경우 예외처리
    public void validateBuyProductCount(String productName, int quantity) {

        int sum = storedProducts.getAllProductCount(productName);
        if (sum < quantity) {
            throw new IllegalArgumentException(ERROR_INPUT_TOO_MANY_PRODUCT_COUNT);
        }
    }



}
