package store;

public enum ProductIndex {
    NAME(0),
    PRICE(1),
    COUNT(2),
    PROMOTION(3);

    private final int index;

    // 생성자를 통해 인덱스를 설정
    ProductIndex(int index) {
        this.index = index;
    }

    // 인덱스를 반환하는 메서드
    public int getIndex() {
        return index;
    }
}
