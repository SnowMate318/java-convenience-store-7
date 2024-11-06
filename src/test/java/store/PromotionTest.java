package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("프로모션 테스트")
public class PromotionTest {

    static final private String ERROR_NOT_PARSE_DATE = "[테스트 오류] 날짜 정보를 가져오지 못했습니다.";


    @Nested
    @DisplayName("예외처리 테스트")
    class ValidationTest {

        PromotionValidator promotionValidator = new PromotionValidator();


        static final private String ERROR_TEXT = "[ERROR]";

        @Test
        void 프로모션_이름이_비어있으면_안된다() {

            String noPromotionName = "";

            assertThatThrownBy(()->promotionValidator.validatePromotionName(noPromotionName))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 다섯_개의_정보가_쉼표로_구분되어야_한다() {

            String[] fourInfos = "MD추천상품,1,1,2024-01-01".split(",");
            String[] sixInfos = " ,1,1,2024-11-01,2024-12-31,wrong".split(",");
            int arrayLength = 5;


            assertThatThrownBy(() -> promotionValidator.validateArrayLength(fourInfos,arrayLength))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> promotionValidator.validateArrayLength(sixInfos,arrayLength))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 구매물품_갯수는_숫자로_되어있어야_한다() {

            String wrongBuy = "MD추천상품,root,1,2024-01-01,2024-12-31";

            assertThatThrownBy(() -> new Promotion(wrongBuy))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 증정물품_갯수는_숫자로_되어있어야_한다() {

            String wrongGet = "MD추천상품,1,root,2024-01-01,2024-12-31";

            assertThatThrownBy(() -> new Promotion(wrongGet))
                    .isInstanceOf(IllegalArgumentException.class);

        }


        @Test
        void 프로모션_시작일은_날짜로_되어있어야_한다() {

            String wrongStartDate = "MD추천상품,1,1,root,2024-12-31";
            String wrongEndDate = "MD추천상품,1,1,2024-01-01,root";

            assertThatThrownBy(() -> new Promotion(wrongStartDate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 프로모션_마감일은_날짜로_되어있어야_한다() {

            String wrongEndDate = "MD추천상품,1,1,2024-01-01,root";

            assertThatThrownBy(() -> new Promotion(wrongEndDate))
                    .isInstanceOf(IllegalArgumentException.class);
        }


        @Test
        void 프로모션_시작일이_프로모션_마감일보다_늦을_수_없다() {

            Date startDate = parseDate("2024-12-31");// 시작 일자
            Date endDate = parseDate("2024-01-01"); // 마감 일자를 시작 일자보다 빠르게 설정

            assertThatThrownBy(() -> promotionValidator.validateDate(startDate,endDate))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        /// 문자열을 날짜로 분리
        private Date parseDate(String dateString) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dateString = dateString.trim();
                return formatter.parse(dateString);

            } catch (ParseException e) {
                throw new IllegalArgumentException(ERROR_NOT_PARSE_DATE);
            }
        }
    }

    @Test
    void 오늘을_기준으로_프로젝트_시작일과_마감일_사이에_있어야_활성화된다() {

        String wrongStartDate = "MD추천상품,1,1,2024-01-01,2024-12-31";

    }



}
