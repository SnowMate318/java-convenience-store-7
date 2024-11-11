package store;

import java.util.List;

public class StoredProductsValidator {

    static final private String NULL = "null";

    static final private String ERROR_NOT_ADD_PRODUCT = "[ERROR] 더 이상 재고를 추가할 수 없습니다.";
    static final private String ERROR_NOT_ADD_PROMOTION = "[ERROR] 더 이상 프로모션을 추가할 수 없습니다.";
    static final private String ERROR_MUST_ADD_PROMOTION = "[ERROR] 프로모션을 추가해야 합니다.";






    public void validateLength (List<Product> products) {

        if(products.size() > 2) {
            throw new IllegalArgumentException(ERROR_NOT_ADD_PRODUCT);
        }

    }

    public void validateNull (List<Product> products, String promotionName) {

        if(products.size() == 1) {
            // 2개 이상의 프로모션을 만들려고 할 때
            if(!promotionName.equals(NULL) && !products.getFirst().getPromotionName().equals(NULL)) {

                throw new IllegalArgumentException(ERROR_NOT_ADD_PROMOTION);
            }
            //프로모션이 없는 재고를 2번 입력받을 때
            if(promotionName.equals(NULL) && products.getFirst().getPromotionName().equals(NULL)) {
                throw new IllegalArgumentException(ERROR_MUST_ADD_PROMOTION);
            }
        }
    }


}
