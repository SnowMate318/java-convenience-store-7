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

            assertThatThrownBy(()->productValidator.validateName(blankProductName))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 프로덕트_이름이_50자를_넘으면_안된다() {

            String tooLongProductName = "morethan50morethan50morethan50morethan50morethan51m";

            assertThatThrownBy(()->productValidator.validateName(tooLongProductName))
                    .isInstanceOf(IllegalArgumentException.class);
        }



        @Test
        void 상품_가격은_100원_이상이여야_한다() {

            int price = 99;

            assertThatThrownBy(() -> productValidator.validatePrice(price))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상품_가격은_10000000원_이하여야_한다() {

            int price = 10000001;

            assertThatThrownBy(() -> productValidator.validatePrice(price))
                    .isInstanceOf(IllegalArgumentException.class);
        }



        @Test
        void 상품_재고는_0개_이상이여야_한다() {

            int count = -1;

            assertThatThrownBy(() -> productValidator.validateCount(count))
                    .isInstanceOf(IllegalArgumentException.class);

        }



        @Test
        void 상품_재고는_10000개를_넘으면_안된다() {

            int count = 10001;

            assertThatThrownBy(() -> productValidator.validateCount(count))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
