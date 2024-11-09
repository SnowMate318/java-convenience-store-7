package store;

public class BuyProduct {

    private String productName;
    private int quantity;

    BuyProduct(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName () {
        return this.productName;
    }

    public int getQuantity () {
        return this.quantity;
    }

}
