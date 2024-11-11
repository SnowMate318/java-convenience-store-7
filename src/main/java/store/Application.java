package store;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        // TODO: 프로그램 구현
        StoredPromotions storedPromotions = StoredPromotions.getInstance();
        StoredProducts storedProducts = new StoredProducts();
        boolean repeat = true;
        while(repeat) {
            repeat = run(storedProducts);
        }
    }

    static boolean run(StoredProducts storedProducts) {

        try {
            OutputView outputView = new OutputView(storedProducts);
            InputView inputView = new InputView();
            Store store = new Store("w편의점", storedProducts);
            outputView.showStore(store.getStoreName());
            List<BuyProduct> buyProducts = inputView.inputPurchase();
            store.setIsMembership(inputView.inputMembership());
            boolean repeat = inputView.inputNextPurchase();
            store.purchase(buyProducts);
            outputView.printReceipt(store.getReceipt());
            return repeat; // 다른 것을 구매할지 물어봄
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}
