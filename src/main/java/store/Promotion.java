package store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Promotion {


    static final private int ARRAY_LENGTH = 5;


    private String promotionName; // 프로모션 이름
    private int productCountBuy; // 프로모션이 적용되는 구매물품 수
    private int productCountGet; // 프로모션으로 얻을 수 있는 물품 수
    private Date startDate; // 프로모션이 시작되는 날짜
    private Date endDate; // 프로모션이 끝나는 날짜
    private boolean isActivate;

    private final PromotionValidator validator = new PromotionValidator();
    private final Parser parser = new Parser();

    /// 문자열 형태로 되어있는 프로모션 정보를 처리한 후 저장
    Promotion(String promotionName, int productCountBuy, int productCountGet, Date startDate, Date endDate) {

        validate(promotionName, productCountBuy, productCountGet, startDate, endDate);
        this.promotionName = promotionName;
        this.productCountBuy = productCountBuy;
        this.productCountGet = productCountGet;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActivate = checkActivate();
    }

    private void validate(String promotionName, int productCountBuy, int productCountGet, Date startDate, Date endDate) {

        validator.validateNameLength(promotionName);
        validator.validateBuy(productCountBuy);
        validator.validateGet(productCountGet);
        validator.validateDate(startDate,endDate);

    }

    public String getPromotionName() {
        return this.promotionName;
    }

    public boolean getIsActivate () {
        return this.isActivate;
    }

    private boolean checkActivate() {
        Date today = new Date();
        return startDate.compareTo(today) <= 0 && endDate.compareTo(today) >= 0;
    }


}
