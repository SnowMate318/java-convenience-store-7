package store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Promotion {

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
        validate(details);
        String[] promitionInfos = details.split(",");
        setPromotions(promitionInfos);
    }

    ///
    private void setPromotions(String[] promitionInfos) {

        this.promotionName = promitionInfos[0].trim();
        this.buy = parseInt(promitionInfos[1]);
        this.get = parseInt(promitionInfos[2]);
        this.startDate = parseDate(promitionInfos[3]);
        this.endDate = parseDate(promitionInfos[4]);
        this.isActivate = checkActivate(startDate,endDate);
    }

    private int parseInt(String intString) {
        //Todo: 예외처리 분리
        try {
            intString = intString.trim();
            return Integer.parseInt(intString);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_NOT_PARSE_BUY);
        }
    }

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
    public Boolean checkActivate (Date startDate, Date endDate) {

        Date today = new Date();
        return startDate.compareTo(today) <= 0 && endDate.compareTo(today) >= 0;


    }

    private void validate(String details){
        //Todo: 예외처리 기능 구현
    }


}
