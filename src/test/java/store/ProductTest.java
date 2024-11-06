package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("프로덕트 테스트")
public class ProductTest {

    @Nested
    @DisplayName("예외처리 테스트")
    class ValidationTest {

        ProductValidator productValidator = new ProductValidator();


        static final private String ERROR_TEXT = "[ERROR]";

        @Test
        void 프로덕트_이름이_비어있으면_안된다() {

            String blankProductName = "";

            assertThatThrownBy(()->productValidator.validateNameLength(blankProductName))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 프로덕트_이름이_50자를_넘으면_안된다() {

            String tooLongProductName = "morethan50morethan50morethan50morethan50morethan51m";

            assertThatThrownBy(()->productValidator.validateNameLength(tooLongProductName))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 네개의_정보가_쉼표로_구분되어야_한다() {
            String[] fourInfos = "콜라,1000,10".split(",");
            String[] sixInfos = "사이다,1000,7,null,wrong".split(",");

            int arrayLength = 4;

            assertThatThrownBy(() -> productValidator.validateArrayLength(fourInfos,arrayLength))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> productValidator.validateArrayLength(sixInfos,arrayLength))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상품_가격은_숫자로_되어있어야_한다() {

            String wrongprice = "콜라,root,10,null";

            assertThatThrownBy(() -> new Product(wrongprice))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상품_가격은_100원_이상이여야_한다() {

            String priceUnder100 = "콜라,99,10,null";

            assertThatThrownBy(() -> new Product(priceUnder100))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상품_가격은_10000000원_이하여야_한다() {

            String priceOver10000000 = "콜라,10000001,10,null";

            assertThatThrownBy(() -> new Product(priceOver10000000))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상품_재고는_숫자로_되어있어야_한다() {

            String wrongCount = "콜라,1000,root,null";

            assertThatThrownBy(() -> new Product(wrongCount))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 상품_재고는_0개_이상이여야_한다() {

            String countUnder0 = "콜라,1000,-1,null";

            assertThatThrownBy(() -> new Product(countUnder0))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 상품_재고는_10000개를_넘으면_안된다() {

            String countOver10000 = "콜라,10001,-1,null";

            assertThatThrownBy(() -> new Product(countOver10000))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
