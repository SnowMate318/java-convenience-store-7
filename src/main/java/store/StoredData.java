package store;

import java.util.ArrayList;
import java.util.List;

public class StoredData {

    List<Promotion> promotions = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    StoredDataValidator validator = new StoredDataValidator();
    private static StoredData instance; // 싱글톤 인스턴스
    Parser parser = new Parser();

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
            Promotion promotion = parser.lineToPromotion(readLine);
            validator.validateDuplicatePromotion(promotion); // 프로모션 중복 예외처리
            promotions.add(promotion);
        }
    }




    private void setProducts(List<String> readProductFile){

        for(String readLine : readProductFile) {
            readLine = readLine.trim();
            Product product = parser.lineToProduct(readLine);
            validator.validateDuplicateProduct(product); // 재고 + 프로모션 중복 예외처리
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

        List<Product> findProducts = new ArrayList<>();

        for(Product product : products) {
            if(product.getProductName().equals(productName)) {
                findProducts.add(product);
            }
        }
        return findProducts;
    }

    public Product findByProductNameAndPromotionName(String productName, String promotionName) {

        List<Product> products = findByProductName(productName);

        for(Product product : products) {
            if(product.getProductName().equals(promotionName)) {
                return product;
            }
        }
        return null;
    }



}
