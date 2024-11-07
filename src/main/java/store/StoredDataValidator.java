package store;

import java.util.List;

public class StoredDataValidator {

    static final private String NULL = "null";

    static final private String ERROR_DUPLICATE_PROMOTION_NAME = "[ERROR] 프로모션 이름이 중복될 수 없습니다.";
    static final private String ERROR_NOT_ADD_PRODUCT = "[ERROR] 더 이상 재고를 추가할 수 없습니다.";
    static final private String ERROR_NOT_ADD_PROMOTION = "[ERROR] 더 이상 프로모션을 추가할 수 없습니다.";
    static final private String ERROR_MUST_ADD_PROMOTION = "[ERROR] 프로모션을 추가해야 합니다.";



    StoredData storedData = StoredData.getInstance();

    public void validateDuplicatePromotion(Promotion promotion) {

        String promotionName = promotion.getPromotionName();

        if (storedData.findByPromotionName(promotionName) != null) { // 기존에 있는 프로모션 이름이라면
            throw new IllegalArgumentException(ERROR_DUPLICATE_PROMOTION_NAME);
        }

    }

    public void validateDuplicateProduct(Product product) {

        String productName = product.getProductName();
        String promotionName = product.getPromotionName();

        List<Product> products = storedData.findByProductName(productName);

        validateLength(products); // Todo: 길이가 2 이하여야 함
        validateNull(products, productName); // Todo: 길이가 1이라면 promotionName 하나만 null이 들어가는지 확인해야함

    }

    public void validateLength (List<Product> products) {

        if(products.size() > 2) {
            throw new IllegalArgumentException(ERROR_NOT_ADD_PRODUCT);
        }

    }

    public void validateNull (List<Product> products, String promotionName) {

        if(products.size() == 1) {

            if(!promotionName.equals(NULL) && !products.getFirst().getPromotionName().equals(NULL)) {
                throw new IllegalArgumentException(ERROR_NOT_ADD_PROMOTION);
            }
            if(promotionName.equals(NULL) && products.getFirst().getPromotionName().equals(NULL)) {
                throw new IllegalArgumentException(ERROR_MUST_ADD_PROMOTION);
            }
        }
    }


}
