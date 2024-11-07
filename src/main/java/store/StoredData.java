package store;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StoredData {

    List<Promotion> promotions = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    private static StoredData instance; // 싱글톤 인스턴스

    StoredData() {

        FileLoader fileLoader = new FileLoader(); // 파일 입출력 관련 클래스를 가져옴
        List<String> readPromotionFile = fileLoader.loadPromotion();
        List<String> readProductFile = fileLoader.loadPromotion();

        setPromotions(readPromotionFile);
        setProducts(readProductFile);

    }


    public static synchronized StoredData getInstance() {
        if (instance == null) {
            instance = new StoredData();
        }
        return instance;
    }

    private void setPromotions(List<String> readPromotionFile){

        for(String readLine : readPromotionFile) {
            readLine = readLine.trim();
            Promotion promotion = new Promotion(readLine);
            //Todo: 프로모션 중복 예외처리
            promotions.add(promotion);
        }
    }

    private void setProducts(List<String> readProductFile){

        for(String readLine : readProductFile) {
            readLine = readLine.trim();
            Product product = new Product(readLine);
            //Todo: 이미 프로모션이 적용된 상품인지 확인
            //Todo: 기존 프로덕트 없으면 추가
            //Todo: 기존 프로덕트가 하나면 해당 프로덕트의 프로모션 여부 확인 (null -> promotion만 가능), (promotion -> null만 가능)
            //Todo: 기존 프로덕트가 두개면 추가 x
            products.add(product);
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

    public List<Product> findByProductName(String productName) {

        return products.stream()
                .filter(product -> product.getProductName().equalsIgnoreCase(productName))
                .sorted(Comparator.comparing(product -> product.getPromotion() == null))
                .collect(Collectors.toList()); // 이름에 해당하는 물품을 찾고, promotion이 널이 아닌것을 우선으로 찾음
    }

}
