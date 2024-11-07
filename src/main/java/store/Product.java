package store;

public class Product {

    String productName;
    int productPrice;
    int productCount;
    Promotion promotion;

    ProductValidator validator = new ProductValidator();

    ///프로모션이 있는 상품일 경우
    Product(String productName, int productPrice, int productCount) {

        validate(productName,productPrice,productCount);

        this.productName = productName;
        this.productCount = productCount;
        this.productPrice = productPrice;

    }

    ///프로모션이 없는 상품일 경우
    Product(String productName, int productPrice, int productCount, Promotion promotion) {

        validate(productName,productPrice,productCount);

        this.productName = productName;
        this.productCount = productCount;
        this.productPrice = productPrice;
        this.promotion = promotion;
    }

    private void validate(String productName, int productPrice, int productCount) {

        validator.validateName(productName);
        validator.validatePrice(productPrice);
        validator.validateCount(productCount);

    }
}
