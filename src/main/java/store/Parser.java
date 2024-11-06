package store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parser {
    ///문자열을 숫자로 변환, 관련 예외처리
    public int parseInt(String intString, String errorMessage) {
        //Todo: 예외처리 분리
        try {
            intString = intString.trim();
            Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }

        return Integer.parseInt(intString);
    }

    /// 문자열을 날짜로 분리
    public Date parseDate(String dateString, String errorMessage) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateString = dateString.trim();
            return formatter.parse(dateString);

        } catch (ParseException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

}
