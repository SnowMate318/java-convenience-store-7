package store;

import camp.nextstep.edu.missionutils.Console;

import java.util.List;

public class InputView {

    static final private String TEXT_INPUT_PURCHASE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    static final private String TEXT_INPUT_MEMBERSHIP = "멤버십 할인을 받으시겠습니까? (Y/N)";


    Parser parser = new Parser();


    public List<BuyProduct> inputPurchase () {
        System.out.println(TEXT_INPUT_PURCHASE);
        String input = Console.readLine();
        input = input.trim();
        return parser.lineToBuyProducts(input);
    }

    public boolean inputPromotionGet (String productName, int quantity) {
        System.out.println("현재 "+productName+"은(는) "+quantity+"개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        String input = Console.readLine();
        input = input.trim();
        return parser.parseBoolean(input);
    }

    public boolean inputGuideNotPromotion(String productName, int quantity) {
        System.out.println("현재 "+productName+" "+quantity+"개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        String input = Console.readLine();
        input = input.trim();
        return parser.parseBoolean(input);
    }

    public boolean inputMembership() {
        System.out.println(TEXT_INPUT_MEMBERSHIP);
        String input = Console.readLine();
        input = input.trim();
        return parser.parseBoolean(input);
    }

    public boolean inputNextPurchase () {
        System.out.println(TEXT_INPUT_PURCHASE);
        String input = Console.readLine();
        input = input.trim();
        return parser.parseBoolean(input);
    }


}
