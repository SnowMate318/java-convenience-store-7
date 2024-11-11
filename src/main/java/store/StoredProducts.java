package store;

import java.util.ArrayList;
import java.util.List;

public class StoredProducts {

    static final private String ERROR_DUPLICATE_PROMOTION_NAME = "[ERROR] 프로모션 이름이 중복될 수 없습니다.";

    List<Product> products = new ArrayList<>();
    StoredProductsValidator validator = new StoredProductsValidator();
    private static StoredProducts instance; // 싱글톤 인스턴스
    Parser parser = new Parser();

    static final private String NULL = "null";

    StoredProducts() {

        FileLoader fileLoader = new FileLoader(); // 파일 입출력 관련 클래스를 가져옴
        List<String> readProductFile = fileLoader.loadProduct();
        setProducts(readProductFile);

    }

    private void setProducts(List<String> readProductFile){

        for(int i=1;i<readProductFile.size();i++) {
            String readLine = readProductFile.get(i).trim();
            Product product = parser.lineToProduct(readLine);
            validateDuplicatePromotion(product); // 재고 + 프로모션 중복 예외처리
            addOrCreateProductCount(product);
            addNotPromotion(product);
        }
    }

    private void addOrCreateProductCount(Product addProduct) {

        for(Product product : products) {
            if(addProduct.equals(product)) {
                product.addProductCount(addProduct.getProductCount());
                return ;
            }
        }
        products.add(addProduct);
    }

    private void addNotPromotion(Product product) {
        if(!product.getProductName().equals(NULL)) {
            Product notPromotionedProduct = new Product(product.getProductName(), product.getProductPrice(), 0);
            addOrCreateProductCount(notPromotionedProduct);
        }
    }


    public void validateDuplicatePromotion(Product product) {

        String productName = product.getProductName();
        String promotionName = product.getPromotionName();
        List<Product> products = findByProductName(productName);

        validator.validateLength(products);
        validator.validateNull(products, promotionName);

    }

    public List<Product> getProducts () {
        return this.products;
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


    /// 프로모션 있는 프로덕트를 찾음
    public Product findByProductOnPromotion (String productName) {
        List<Product> products = findByProductName(productName);

        for(Product product : products) {
            if(!product.getProductName().equals(NULL)) {
                return product;
            }
        }
        return null;
    }

    /// 프로모션 없는 프로덕트를 찾음
    public Product findByProductNotOnPromotion (String productName) {
        List<Product> products = findByProductName(productName);

        for(Product product : products) {
            if(product.getProductName().equals(NULL)) {
                return product;
            }
        }
        return null;
    }

    /// 상품 이름에 대한 재고 구하기
    public int getAllProductCount (String productName) {
        int sum = 0;

        for (Product product : findByProductName(productName)) {
            sum += product.getProductCount();
        }

        return sum;
    }
}
