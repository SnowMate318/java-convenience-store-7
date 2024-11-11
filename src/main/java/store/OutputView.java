package store;


import java.text.DecimalFormat;
import java.util.List;

public class OutputView {

    private StoredProducts storedProducts;

    static final private String NULL = "null";
    static final private String TOTAL_PRICE = "총구매액";
    static final private String PROMOTION_DISCOUNT = "행사할인";
    static final private String MEMBERSHIP_DISCOUNT = "멤버십할인";
    static final private String FINAL_PRICE = "내실 돈";
    static final private String OUT_OF_STOCK = "재고 없음";

    DecimalFormat formatter = new DecimalFormat("###,###");

    OutputView (StoredProducts storedProducts) {
        this.storedProducts = storedProducts;
    }

    public void showStore (String storeName) {

        System.out.println("안녕하세요. "+storeName+"입니다.");
        System.out.println("현재 보유하고 있는 상품입니다. ");
        System.out.println();

        showProducts();
    }

    private void showProducts () {

        List<Product> products = storedProducts.getProducts();
        for(Product product : products) {
            System.out.println(getProductInfo(product)); // 편의점 재고 정보를 출력
        }

    }

    private String getProductInfo (Product product) {

        String productInfo =  "- " + product.getProductName() + " "
                + formatter.format(product.getProductPrice()) + "원 ";

        if(product.getProductCount() != 0) {
            productInfo += product.getProductCount() + "개 ";
        }

        if(product.getProductCount() == 0) {
            productInfo += OUT_OF_STOCK;
        }

        if(!product.getPromotionName().equals(NULL)) {
            productInfo += product.getPromotionName();
        }
        return productInfo;
    }

    public void printReceipt (Receipt receipt) {

        printDivider(receipt.getStoreName());
        printPurchasedProducts(receipt.getPurchasedProducts());
        printPromotionedProducts(receipt.getPurchasedProductsOnPromotion());
        printDivider();
        printPrices(receipt);
    }

    ///영수증 디바이더 출력
    private void printDivider() {
        System.out.println("==============================");
    }

    ///영수증 디바이더 출력
    private void printDivider(String banner) {
        System.out.println("==========="+banner+"=============");
    }

    ///구매한 상품 풀력
    private void printPurchasedProducts(List<BuyProduct> buyProducts) {
        printSchema();
        for(BuyProduct buyProduct : buyProducts) {
            printProduct(buyProduct);
        }
    }

    private void printPromotionedProducts(List<BuyProduct> buyProducts) {
        printDivider("증\t정");
        for(BuyProduct buyProduct : buyProducts) {
            printProduct(buyProduct);
        }
    }

    private void printProduct(BuyProduct buyProduct) {

        String price = "";
        if(buyProduct.getPrice() != null && buyProduct.getPrice()>0) {
            price = formatter.format(buyProduct.getPrice());
        }
        System.out.println(buyProduct.getProductName()+"\t\t"+buyProduct.getQuantity()+"\t"+price);
    }

    private void printSchema(){
        System.out.println("상품명\t\t수량\t금액\n");
    }

    private void printPrices (Receipt receipt) {

        printTotalPrice(receipt.getTotalCount(), receipt.getTotalPrice());
        printDiscount(receipt.getPromotionDiscount(),PROMOTION_DISCOUNT);
        printDiscount(receipt.getMembershipDiscount(),MEMBERSHIP_DISCOUNT);
        printFinalPrice(receipt.getFinalPrice());

    }

    private void printTotalPrice(int count, int price) {

        System.out.println(TOTAL_PRICE+"\t\t"+count+"\t"+formatter.format(price));

    }

    private void printDiscount(int price, String description) {

        System.out.println(description+"\t\t\t-"+formatter.format(price));

    }

    private void printFinalPrice(int price) {

        System.out.println(FINAL_PRICE+"\t\t\t"+formatter.format(price));

    }

}
