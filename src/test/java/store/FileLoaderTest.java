package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("파일 읽기 테스트")
public class FileLoaderTest {

    private static final String ERROR_TEXT = "[ERROR]";

    @Test
    public void 재고_파일_읽기_중_에러_메세지_출력_여부_확인(){

        FileLoader fileLoader = new FileLoader();
        fileLoader.loadProduct();
        String output = getOutput();
        assertThat(output).doesNotContain(ERROR_TEXT);

    }

    @Test
    public void 프로모션_파일_읽기_중_에러_메세지_출력_여부_확인(){

        FileLoader fileLoader = new FileLoader();
        fileLoader.loadPromotion();
        String output = getOutput();
        assertThat(output).doesNotContain(ERROR_TEXT);

    }

    private String getOutput() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        return outputStream.toString();
    }

}

