package store;

import java.util.Date;

public class ProductValidator {

    static final private int PRICE_MIN = 100;
    static final private int PRICE_MAX = 10000000;
    static final private int COUNT_MIN = 0;
    static final private int COUNT_MAX = 10000;
    static final private int PRODUCT_NAME_LANGTH_MAX = 50;

    static final private String ERROR_PRICE_IS_TO_LOW = "[ERROR] 100원 이상의 가격을 입력해야 합니다.";
    static final private String ERROR_PRICE_IS_TO_HIGH = "[ERROR] 10000000원 이하의 가격을 입력해야 합니다.";
    static final private String ERROR_COUNT_IS_TO_LOW = "[ERROR] 0개 이상의 물품갯수를 입력해야 합니다.";
    static final private String ERROR_COUNT_IS_TO_HIGH = "[ERROR] 10000갸 이하의 물품갯수를 입력해야 합니다.";

    static final private String ERROR_NOT_MATCH_ARRAY_LENGTH = "[ERROR] 올바르지 않은 배열 길이입니다.";
    static final private String ERROR_NOT_BLANK_NAME = "[ERROR] 상품 이름은 빈 칸이 될 수 없습니다.";
    static final private String ERROR_NAME_TOO_LONG = "[ERROR] 범위에서 벗어난 상품 이름 길이입니다.";

    /// 프로모션 이름 예외처리
    public void validateName (String promotionName) {

        if(promotionName.isEmpty()){
            throw new IllegalArgumentException(ERROR_NOT_BLANK_NAME);
        }

        if(promotionName.length() > PRODUCT_NAME_LANGTH_MAX){
            throw new IllegalArgumentException(ERROR_NAME_TOO_LONG);
        }
    }


    /// 가격이 범위 안에 들어왔는지 확인
    public void validatePrice(int currentPrice){

        validateInRangeInteger
                (currentPrice,PRICE_MIN,PRICE_MAX,ERROR_PRICE_IS_TO_LOW,ERROR_PRICE_IS_TO_HIGH);
    }

    /// 상품갯수가 범위 안에 들어왔는지 확인
    public void validateCount(int currentCount){

        validateInRangeInteger
                (currentCount,COUNT_MIN,COUNT_MAX,ERROR_COUNT_IS_TO_LOW,ERROR_COUNT_IS_TO_HIGH);
    }

    private void validateInRangeInteger(int current, int minRange, int maxRange, String errorMin, String errorMax) {

        if(current < minRange){ // 현재 값이 최소 범위보다 작으면 예외처리
            throw new IllegalArgumentException(errorMin);
        }
        if(current < maxRange){ // 현재 값이 최대 범위보다 크면 예외처리
            throw new IllegalArgumentException(errorMax);
        }
    }
}
