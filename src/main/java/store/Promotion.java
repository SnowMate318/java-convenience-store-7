package store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Promotion {

    static final private int ARRAY_LENGTH = 5;

    static final private int INDEX_PROMOTION_NAME = 0;
    static final private int INDEX_BUY = 1;
    static final private int INDEX_GET = 2;
    static final private int INDEX_START_DATE = 3;
    static final private int INDEX_END_DATE = 4;

    static final private String ERROR_NOT_MATCH_ARRAY_LENGTH = "[ERROR] 올바르지 않은 정보 수입니다.";
    static final private String ERROR_NOT_PARSE_BUY = "[ERROR] 물품 갯수 정보는 숫자로 되어있어야 합니다.";
    static final private String ERROR_NOT_PARSE_DATE = "[ERROR] 파일로부터 날짜 정보를 가져오지 못했습니다.";

    private String promotionName; // 프로모션 이름
    private int buy; // 프로모션이 적용되는 구매물품 수
    private int get; // 프로모션으로 얻을 수 있는 물품 수
    private Date startDate; // 프로모션이 시작되는 날짜
    private Date endDate; // 프로모션이 끝나는 날짜
    private boolean isActivate;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /// 문자열 형태로 되어있는 프로모션 정보를 처리한 후 저장
    Promotion(String details){
        details = details.trim();
        String[] promitionInfos = details.split(",");
        validateArrayLength(promitionInfos);
        setPromotions(promitionInfos);
    }

    ///
    private void setPromotions(String[] promitionInfos) {

        setPromotionName(promitionInfos);
        setBuy(promitionInfos);
        setGet(promitionInfos);
        setStartDate(promitionInfos);
        setEndDate(promitionInfos);
        setIsActivate(checkActivate(startDate,endDate));
    }

    private void setPromotionName (String[] promitionInfos) {

        this.promotionName = promitionInfos[INDEX_PROMOTION_NAME].trim();
        validatePromotionName(this.promotionName);
    }

    public void validatePromotionName (String promotionName) {
        if(promotionName.isEmpty()){
            throw new IllegalArgumentException("[ERROR]프로모션 이름은 있어야합니다.");
        }
    }

    private void setBuy (String[] promitionInfos) {
        this.buy = parseInt(promitionInfos[INDEX_BUY].trim());
    }

    private void setGet (String[] promitionInfos) {
        this.get = parseInt(promitionInfos[INDEX_GET].trim());
    }


    ///문자열을 숫자로 변환, 관련 예외처리
    private int parseInt(String intString) {
        //Todo: 예외처리 분리
        try {
            intString = intString.trim();
            Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_NOT_PARSE_BUY);
        }

        return Integer.parseInt(intString);
    }

    /// 프로모션 시작일 저장
    private void setStartDate (String[] promitionInfos) {
        this.startDate = parseDate(promitionInfos[INDEX_START_DATE].trim());
    }

    /// 프로모션 마감일 저장
    private void setEndDate (String[] promitionInfos) {
        this.endDate = parseDate(promitionInfos[INDEX_END_DATE].trim());
    }

    /// 프로모션 활성화 여부 저장
    public void setIsActivate (boolean isActivate) {
        this.isActivate = isActivate;
    }

    /// 문자열을 날짜로 분리
    private Date parseDate(String dateString) {
        //Todo: 예외처리 분리
        try {
            dateString = dateString.trim();
            return formatter.parse(dateString);

        } catch (ParseException e) {
            throw new IllegalArgumentException(ERROR_NOT_PARSE_DATE);
        }
    }

    /// 오늘이 프로모션 기간에 해당하는지 여부
    public boolean checkActivate (Date startDate, Date endDate) {

        Date today = new Date();
        return startDate.compareTo(today) <= 0 && endDate.compareTo(today) >= 0;

    }


    private void validateArrayLength(String[] processedDetails){
        //Todo: 예외처리 기능 구현


    }


}
