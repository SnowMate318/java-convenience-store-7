package store;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    private static final String PRODUCTS_PATH = "src/main/resources/products.md";
    private static final String PROMOTIONS_PATH = "src/main/resources/promotions.md";
    private static final String ERROR_MESSAGE = "[ERROR] 파일 로드 중 오류가 발생했습니다. ";

    public List<String> loadProduct () {
        return  loadFile(PRODUCTS_PATH);
    }

    public List<String> loadPromotion () {
        return  loadFile(PROMOTIONS_PATH);
    }

    private List<String> loadFile (String filePath) {
        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE+e.getMessage());
        }

        return lines;
    }

}
