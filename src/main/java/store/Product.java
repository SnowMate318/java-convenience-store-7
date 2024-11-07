package store;

public class Product {

    static final private int INDEX_PRODUCT_NAME = 0;
    static final private int INDEX_PRICE = 1;
    static final private int INDEX_COUNT = 2;
    static final private int INDEX_PROMOTION = 3;

    static final private int PRODUCT_ARRAY_LENGTH = 4;

    static final private String NULL_PROMOTION = "null";

    static final private String ERROR_NOT_PARSE_PRICE = "[ERROR] 가격 정보는 숫자로 되어있습니다.";
    static final private String ERROR_NOT_PARSE_COUNT = "[ERROR] 물품 갯수 정보는 숫자로 되어있습니다.";


    private final ProductValidator validator = new ProductValidator();
    private final Parser parser = new Parser();

    String productName;
    int productPrice;
    int productCount;
    Promotion promotion;

    Product(String productDetails) {
        productDetails = productDetails.trim();
        String[] productInfo = productDetails.split(",");
        validator.validateArrayLength(productInfo,PRODUCT_ARRAY_LENGTH);
        //Todo: 프로덕트 예외처리 (프로덕트이름과 프로모션이름이 일치하는게 없는지 확인)
        setProducts(productInfo);

    }

    private void setProducts(String[] productsInfo) {

        setProductName(productsInfo); // 판매물품 이름 설정
        setProductPrice(productsInfo); // 판매물품 가격 설정
        setProductCount(productsInfo); // 판매물품 재고 설정
        setPromotion(productsInfo); // 프로모션 설정

    }

    public String getProductName() {
        return this.productName;
    }

    /// 싱픔이름 설정
    private void setProductName(String[] productsInfo) {
        this.productName = productsInfo[INDEX_PRODUCT_NAME].trim();
        validator.validateNameLength(this.productName); // 이름 예외처리
    }

    /// 상품가격 설정
    private void setProductPrice(String[] productsInfo) {
        this.productPrice = parser.parseInt(productsInfo[INDEX_PRICE].trim(), ERROR_NOT_PARSE_PRICE);
        validator.validatePrice(this.productPrice);
    }

    ///상품갯수 설정
    private void setProductCount(String[] productsInfo) {
        this.productCount = parser.parseInt(productsInfo[INDEX_COUNT].trim(), ERROR_NOT_PARSE_COUNT);
        validator.validateCount(this.productCount);
    }

    public Promotion getPromotion (){
        return this.promotion;
    }

    ///프로모션 여부 설정
    private void setPromotion(String[] productsInfo) {

        StoredData storedData = StoredData.getInstance(); // 저장된 데이터가 있는 객체를 불러옴
        String promotionName = productsInfo[INDEX_PROMOTION].trim();

        if (promotionName.equals(NULL_PROMOTION)) {
            return;
        }
        this.promotion = storedData.findByPromotionName(promotionName);
    }


}
