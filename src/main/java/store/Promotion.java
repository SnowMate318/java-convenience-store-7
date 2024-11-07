package store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Promotion {


    static final private int INDEX_PROMOTION_NAME = 0;
    static final private int INDEX_BUY = 1;
    static final private int INDEX_GET = 2;
    static final private int INDEX_START_DATE = 3;
    static final private int INDEX_END_DATE = 4;

    static final private int ARRAY_LENGTH = 5;


    static final private String ERROR_NOT_PARSE_PRODUCT_COUNT = "[ERROR] 물품 갯수 정보는 숫자로 되어있어야 합니다.";
    static final private String ERROR_NOT_PARSE_DATE = "[ERROR] 파일로부터 날짜 정보를 가져오지 못했습니다.";

    private String promotionName; // 프로모션 이름
    private int productCountBuy; // 프로모션이 적용되는 구매물품 수
    private int productCountGet; // 프로모션으로 얻을 수 있는 물품 수
    private Date startDate; // 프로모션이 시작되는 날짜
    private Date endDate; // 프로모션이 끝나는 날짜
    private boolean isActivate;

    private final PromotionValidator validator = new PromotionValidator();
    private final Parser parser = new Parser();

    /// 문자열 형태로 되어있는 프로모션 정보를 처리한 후 저장
    Promotion(String promotionDetails) {

        promotionDetails = promotionDetails.trim();
        String[] promotionsInfo = promotionDetails.split(",");
        validator.validateArrayLength(promotionsInfo, ARRAY_LENGTH);
        setPromotions(promotionsInfo);
        validator.validateDate(startDate, endDate);

    }

    private void setPromotions(String[] promotionsInfo) {

        setPromotionName(promotionsInfo);
        setBuy(promotionsInfo);
        setGet(promotionsInfo);
        setStartDate(promotionsInfo);
        setEndDate(promotionsInfo);
        setIsActivate(checkActivate(startDate, endDate));
    }

    public String getPromotionName() {
        return this.promotionName;
    }

    /// 프로모션 이름 저장
    private void setPromotionName(String[] promotionsInfo) {

        this.promotionName = promotionsInfo[INDEX_PROMOTION_NAME].trim();
        validator.validateNameLength(this.promotionName); // 프로모션 이름 예외처리
    }

    public int getBuyProductCount() {
        return this.productCountBuy;
    }

    /// 물품 구매개수 저장
    private void setBuy(String[] promotionsInfo) {
        this.productCountBuy = parser.parseInt(promotionsInfo[INDEX_BUY].trim(), ERROR_NOT_PARSE_PRODUCT_COUNT);
        validator.validateBuy(this.productCountBuy);
    }

    public int getGetProductCount() {
        return this.productCountGet;
    }

    /// 물품 증정 정보 저장
    private void setGet(String[] promotionsInfo) {
        this.productCountGet = parser.parseInt(promotionsInfo[INDEX_GET].trim(), ERROR_NOT_PARSE_PRODUCT_COUNT);
        validator.validateGet(this.productCountGet);
    }

    /// 프로모션 시작일 저장
    private void setStartDate(String[] promotionsInfo) {
        this.startDate = parser.parseDate(promotionsInfo[INDEX_START_DATE].trim(), ERROR_NOT_PARSE_DATE);
    }

    /// 프로모션 마감일 저장
    private void setEndDate(String[] promotionsInfo) {
        this.endDate = parser.parseDate(promotionsInfo[INDEX_END_DATE].trim(), ERROR_NOT_PARSE_DATE);
    }

    /// 프로모션 활성화 여부 저장
    /// 다른 사유로 활성화 여부가 바뀔 수 있으므로 public 제어자로 지정
    public void setIsActivate(boolean isActivate) {
        this.isActivate = isActivate;
    }

    public boolean getIsActivate() {
        return this.isActivate;
    }


    /// 오늘이 프로모션 기간에 해당하는지 여부
    public boolean checkActivate(Date startDate, Date endDate) {

        Date today = new Date();
        return startDate.compareTo(today) <= 0 && endDate.compareTo(today) >= 0;

    }


}
