package store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Parser {

    static final private String ERROR_NOT_PARSE_PRICE = "[ERROR] 가격 정보는 숫자로 되어있습니다.";
    static final private String ERROR_NOT_PARSE_COUNT = "[ERROR] 물품 갯수 정보는 숫자로 되어있습니다.";

    static final private String ERROR_NOT_PARSE_PRODUCT_COUNT = "[ERROR] 물품 갯수 정보는 숫자로 되어있어야 합니다.";
    static final private String ERROR_NOT_PARSE_DATE = "[ERROR] 파일로부터 날짜 정보를 가져오지 못했습니다.";

    static final private String ERROR_INPUT_INVALID_FORM = "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";

    static final private String YES = "Y";
    static final private String NO = "N";

    ParserValidator validator = new ParserValidator();

    static final private String NULL = "null";

    public Product lineToProduct(String line) {

        String[] productInfo = line.split(",");
        String productName = productInfo[ProductIndex.NAME.getIndex()];
        int price = parseInt(productInfo[ProductIndex.PRICE.getIndex()], ERROR_NOT_PARSE_PRICE);
        int count = parseInt(productInfo[ProductIndex.COUNT.getIndex()], ERROR_NOT_PARSE_COUNT);
        String promotionName = productInfo[ProductIndex.PROMOTION.getIndex()];

        return getProduct(productName, price, count, promotionName);
    }

    private Product getProduct(String productName, int price, int count, String promotionName) {

        if (promotionName.equals(NULL)) {
            return new Product(productName, price, count);
        }

        Promotion promotion = findPromotion(promotionName);
        validator.validateNullPromotion(promotion);

        return new Product(productName, price, count, promotion);
    }

    private Promotion findPromotion(String promotionName) {

        StoredData storedData = StoredData.getInstance();
        return storedData.findByPromotionName(promotionName);

    }

    public Promotion lineToPromotion(String line) {

        String[] promotionInfo = line.split(",");
        String promotionName = promotionInfo[PromotionIndex.PROMOTION_NAME.getIndex()];
        int productCountBuy = parseInt(promotionInfo[PromotionIndex.BUY_PRODUCT_COUNT.getIndex()], ERROR_NOT_PARSE_PRODUCT_COUNT);
        int productCountGet = parseInt(promotionInfo[PromotionIndex.GET_PRODUCT_COUNT.getIndex()], ERROR_NOT_PARSE_PRODUCT_COUNT);
        Date startDate = parseDate(promotionInfo[PromotionIndex.START_DATE.getIndex()], ERROR_NOT_PARSE_DATE);
        Date endDate = parseDate(promotionInfo[PromotionIndex.END_DATE.getIndex()], ERROR_NOT_PARSE_DATE);

        return new Promotion(promotionName, productCountBuy, productCountGet, startDate, endDate);

    }

    public List<BuyProduct> lineToBuyProducts(String line) {

        String[] promotionsInfo = line.split(",");

        for(String promotionInfo : promotionsInfo) {
            promotionInfo = promotionInfo.trim();
            promotionInfo = promotionInfo.substring(1,promotionInfo.length()-1);

        }
        return getBuyProduct(promotionsInfo);
    }

    private List<BuyProduct> getBuyProduct (String[] promotionsInfo) {

        List<BuyProduct> buyProducts = new ArrayList<>();

        for (String promotionInfo : promotionsInfo) {
            String[] parseData = promotionInfo.split("-");
            validator.validateBuyProductParseData(parseData);
            buyProducts.add(new BuyProduct(parseData[0], parseInt(parseData[1], ERROR_INPUT_INVALID_FORM)));

        }

        return buyProducts;
    }

    ///문자열을 숫자로 변환, 관련 예외처리
    public int parseInt(String intString, String errorMessage) {
        try {
            intString = intString.trim();
            Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }

        return Integer.parseInt(intString);
    }

    /// 문자열을 날짜로 분리
    public Date parseDate(String dateString, String errorMessage) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateString = dateString.trim();
            return formatter.parse(dateString);

        } catch (ParseException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public boolean parseBoolean(String answerInput) {

        validator.validateAnswer(answerInput);
        return answerInput.equals(YES);

    }

}
