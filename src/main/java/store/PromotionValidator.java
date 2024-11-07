package store;

import java.util.Date;

public class PromotionValidator {

    static final private int BUY_MIN = 1;
    static final private int BUY_MAX = 100;
    static final private int GET_MIN = 1;
    static final private int GET_MAX = 100;

    static final private String ERROR_BUY_IS_TO_LOW = "[ERROR] 1개 이상의 구매 물품 갯수를 입력해야 합니다.";
    static final private String ERROR_BUY_IS_TO_HIGH = "[ERROR] 100개 이하의 구매 물품 갯수를 입력해야 합니다.";
    static final private String ERROR_GET_IS_TO_LOW = "[ERROR] 1개 이상의 증정 물품 갯수를 입력해야 합니다.";
    static final private String ERROR_GET_IS_TO_HIGH = "[ERROR] 100개 이하의 증정 물품 갯수를 입력해야 합니다.";


    static final private int PROMOTION_NAME_LENGTH_MAX = 50;

    static final private String ERROR_NOT_BLANK_NAME = "[ERROR] 프로모션 이름은 빈 칸이 될 수 없습니다.";
    static final private String ERROR_NAME_TOO_LONG = "[ERROR] 범위에서 벗어난 프로모션 이름 길이입니다.";
    static final private String ERROR_WRONG_DATE = "[ERROR] 시작일이 마감일보다 앞설 수 없습니다.";

    /// 프로모션 이름 예외처리
    public void validateNameLength (String promotionName) {

        if(promotionName.isEmpty()){
            throw new IllegalArgumentException(ERROR_NOT_BLANK_NAME);
        }

        if(promotionName.length() > PROMOTION_NAME_LENGTH_MAX){
            throw new IllegalArgumentException(ERROR_NAME_TOO_LONG);
        }

    }

    /// 날짜 예외처리
    public void validateDate(Date startDate, Date endDate){

        if(startDate.compareTo(endDate) > 0) {
            throw new IllegalArgumentException(ERROR_WRONG_DATE);
        }

    }

    public void validateBuy(int currentBuy) {
        validateInRangeInteger(currentBuy, BUY_MIN, BUY_MAX, ERROR_BUY_IS_TO_LOW, ERROR_BUY_IS_TO_HIGH);
    }

    public void validateGet(int currentGet) {
        validateInRangeInteger(currentGet, GET_MIN, GET_MAX, ERROR_GET_IS_TO_LOW, ERROR_GET_IS_TO_HIGH);
    }

    private void validateInRangeInteger(int current, int minRange, int maxRange, String errorMin, String errorMax) {

        if(current < minRange){
            throw new IllegalArgumentException(errorMin);
        }
        if(current < maxRange){
            throw new IllegalArgumentException(errorMax);
        }

    }
}
