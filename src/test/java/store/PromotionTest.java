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

        @Test
        void 프로모션_이름이_비어있으면_안된다() {

            String noPromotionName = "";

            assertThatThrownBy(()->promotionValidator.validateNameLength(noPromotionName))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 프로모션_이름이_50자를_넘지_않아야_한다() {

            String tooLongPromotionName = "morethan50morethan50morethan50morethan50morethan51m";

            assertThatThrownBy(()->promotionValidator.validateNameLength(tooLongPromotionName))
                    .isInstanceOf(IllegalArgumentException.class);
        }



        @Test
        void 구매물품_갯수는_최소_1개_이상이여야_한다() {

            int buyProductCount = 0;

            assertThatThrownBy(() -> promotionValidator.validateBuy(buyProductCount))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 구매물품_갯수는_최대_100개_이하여야_한다() {

            int buyProductCount = 101;

            assertThatThrownBy(() -> promotionValidator.validateBuy(buyProductCount))
                    .isInstanceOf(IllegalArgumentException.class);

        }



        @Test
        void 증정물품_갯수는_최소_1개_이상이여야_한다() {

            int getProductCount = 0;

            assertThatThrownBy(() -> promotionValidator.validateGet(getProductCount))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 증정물품_갯수는_최대_100개_이하여야_한다() {

            int getProductCount = 101;

            assertThatThrownBy(() -> promotionValidator.validateGet(getProductCount))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @Test
        void 프로모션_시작일이_프로모션_마감일보다_늦을_수_없다() {

            Date startDate = parseDate("2024-12-31");// 시작 일자
            Date endDate = parseDate("2024-01-01"); // 마감 일자를 시작 일자보다 빠르게 설정

            assertThatThrownBy(() -> promotionValidator.validateDate(startDate,endDate))
                    .isInstanceOf(IllegalArgumentException.class);

        }


    }

    @Test
    void 오늘을_기준으로_프로젝트_시작일과_마감일_사이에_있어야_활성화된다() {

        Date startDate = parseDate("2024-01-01");
        Date endDate = parseDate("2024-01-02"); // 현재 날짜보다 더 빠르게 설정
        Promotion promotion = new Promotion("반짝프로모션",1,1,startDate,endDate);

        assertThat(promotion.getIsActivate()).isEqualTo(false);

        startDate = parseDate("2024-01-01");
        endDate = parseDate("2025-12-31"); // 현재 날짜보다 더 늦게 설정
        Promotion newPromotion = new Promotion("반짝프로모션",1,1,startDate,endDate);

        assertThat(promotion.getIsActivate()).isEqualTo(false);
        assertThat(newPromotion.getIsActivate()).isEqualTo(true);
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
