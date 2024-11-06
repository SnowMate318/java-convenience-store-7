package store;

import java.util.Date;

public class PromotionValidator {

    static final private String ERROR_NOT_MATCH_ARRAY_LENGTH = "[ERROR] 올바르지 않은 정보 수입니다.";
    static final private String ERROR_NOT_BLANK_PROMOTION_NAME = "[ERROR] 프로모션 이름은 있어야합니다.";
    static final private String ERROR_WRONG_DATE = "[ERROR] 시작일이 마감일보다 앞설 수 없습니다.";

    /// 배열 길이 예외처리
    public void validateArrayLength(String[] processedDetails, int length){

        if(processedDetails.length != length){
            throw new IllegalArgumentException(ERROR_NOT_MATCH_ARRAY_LENGTH);
        }

    }

    /// 프로모션 이름 예외처리
    public void validatePromotionName (String promotionName) {
        if(promotionName.isEmpty()){
            throw new IllegalArgumentException(ERROR_NOT_BLANK_PROMOTION_NAME);
        }
    }

    /// 날짜 예외처리
    public void validateDate(Date startDate, Date endDate){

        if(startDate.compareTo(endDate) > 0) {
            throw new IllegalArgumentException(ERROR_WRONG_DATE);
        }

    }


}
