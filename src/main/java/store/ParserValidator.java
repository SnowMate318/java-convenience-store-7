package store;

public class ParserValidator {

    static final private int LENGTH_PRODUCT_ARRAY = 4;
    static final private int LENGTH_PROMOTION_ARRAY = 5;

    static final private String ERROR_NOT_MATCH_ARRAY_LENGTH = "[ERROR] 올바르지 않은 배열 길이입니다.";
    static final private String ERROR_NULL_PROMOTION = "[ERROR] 해당 프로모션은 없는 프로모션입니다.";


    public void validateProductArrayLength(String[] processedDetail){

        validateArrayLength(processedDetail, LENGTH_PRODUCT_ARRAY);

    }

    public void validatePromotionArrayLength(String[] processedDetail){

        validateArrayLength(processedDetail, LENGTH_PROMOTION_ARRAY);

    }

    /// 배열 길이 예외처리
    private void validateArrayLength(String[] processedDetails, int length){

        if(processedDetails.length != length){
            throw new IllegalArgumentException(ERROR_NOT_MATCH_ARRAY_LENGTH);
        }

    }

    public void validateNullPromotion(Promotion promotion){

        if(promotion == null) {
            throw new IllegalStateException(ERROR_NULL_PROMOTION);
        }

    }

}
