package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParserTest {

    ParserValidator parserValidator = new ParserValidator();
    Parser parser = new Parser();

    @Nested
    @DisplayName("프로덕트 예외처리 테스트")
    class ProductValidationTest {

        @Test
        void 재고_정보는_네개의_정보가_쉼표로_구분되어야_한다() {

            String[] threeInfos = "콜라,1000,10".split(",");
            String[] fiveInfos = "사이다,1000,7,null,wrong".split(",");


            assertThatThrownBy(() -> parserValidator.validateProductArrayLength(threeInfos))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> parserValidator.validateProductArrayLength(fiveInfos))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상품_재고는_숫자로_되어있어야_한다() {

            String wrongCount = "콜라,1000,root,null";

            assertThatThrownBy(() -> parser.lineToProduct(wrongCount))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 상품_가격은_숫자로_되어있어야_한다() {

            String wrongprice = "콜라,root,10,null";

            assertThatThrownBy(() -> parser.lineToProduct(wrongprice))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Nested
    @DisplayName("프로모션 예외처리 테스트")
    class PromotionValidationTest {

        @Test
        void 프로모션_정보는_다섯_개의_정보가_쉼표로_구분되어야_한다() {

            String[] fourInfos = "MD추천상품,1,1,2024-01-01".split(",");
            String[] sixInfos = " MD추천상품,1,1,2024-11-01,2024-12-31,wrong".split(",");

            assertThatThrownBy(() -> parserValidator.validatePromotionArrayLength(fourInfos))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> parserValidator.validatePromotionArrayLength(sixInfos))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 구매물품_갯수는_숫자로_되어있어야_한다() {

            String wrongBuy = "MD추천상품,root,1,2024-01-01,2024-12-31";

            assertThatThrownBy(() -> parser.lineToPromotion(wrongBuy))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 증정물품_갯수는_숫자로_되어있어야_한다() {

            String wrongGet = "MD추천상품,1,root,2024-01-01,2024-12-31";

            assertThatThrownBy(() -> parser.lineToPromotion(wrongGet))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 프로모션_시작일은_날짜로_되어있어야_한다() {

            String wrongStartDate = "MD추천상품,1,1,root,2024-12-31";

            assertThatThrownBy(() -> parser.lineToPromotion(wrongStartDate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 프로모션_마감일은_날짜로_되어있어야_한다() {

            String wrongEndDate = "MD추천상품,1,1,2024-01-01,root";

            assertThatThrownBy(() -> parser.lineToPromotion(wrongEndDate))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
