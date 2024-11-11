package store;

import java.util.ArrayList;
import java.util.List;

public class StoredPromotions {

    static final private String ERROR_DUPLICATE_PROMOTION_NAME = "[ERROR] 프로모션 이름이 중복될 수 없습니다.";

    List<Promotion> promotions = new ArrayList<>();

    private static StoredPromotions instance; // 싱글톤 인스턴스
    Parser parser = new Parser();

    static final private String NULL = "null";

    StoredPromotions() {

        FileLoader fileLoader = new FileLoader(); // 파일 입출력 관련 클래스를 가져옴
        List<String> readPromotionFile = fileLoader.loadPromotion();

        setPromotions(readPromotionFile);
    }


    public static synchronized StoredPromotions getInstance() {
        if (instance == null) {
            instance = new StoredPromotions();
        }
        return instance;
    }

    private void setPromotions(List<String> readPromotionFile){

        for(int i=1;i<readPromotionFile.size();i++) {
            String readLine = readPromotionFile.get(i).trim();
            Promotion promotion = parser.lineToPromotion(readLine);
            validateDuplicatePromotion(promotion); // 프로모션 중복 예외처리
            promotions.add(promotion);
        }
    }

    public void validateDuplicatePromotion(Promotion promotion) {

        String promotionName = promotion.getPromotionName();

        if (findByPromotionName(promotionName) != null) { // 기존에 있는 프로모션 이름이라면
            throw new IllegalArgumentException(ERROR_DUPLICATE_PROMOTION_NAME);
        }

    }

    public Promotion findByPromotionName(String promotionName){

        for(Promotion promotion : promotions){
            if(promotion.getPromotionName().equals(promotionName)) {
                return promotion;
            }
        }

        return null;

    }
}
