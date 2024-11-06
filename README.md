# java-convenience-store-precourse


## 요구사항 정리

* 사용자 입력
  * 사용자로부터 구매할 상품과 수량을 입력받는다.
  * 상황에 맞는 예외처리 후 안내한다.
    * 구매할 상품과 수량 형식이 올바르지 않은 경우: [ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.
    * 존재하지 않는 상품을 입력한 경우: [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.
    * 구매 수량이 재고 수량을 초과한 경우: [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.
    * 기타 잘못된 입력의 경우: [ERROR] 잘못된 입력입니다. 다시 입력해 주세요.
  * 프로모션 적용 상품에 대해 사용자가 해당 수량만큼 가져오지 않았을 때 안내 메시지를 출력하고 사용자로부터 입력받는다.
  * 프로모션 적용 상품에 대해 사용자가 프로모션 적용 상품 수량이 부족할 경우, 안내 메시지를 출력하고 사용자로부터 입력받는다.
  * 멤버십 할인 여부 안내 메세지를 출력하고 사용자로부터 입력받는다.
  * 추가 구매 여부를 출력하고 사용자로부터 입력받는다.

* 출력
  * 시작할 때 편의점 정보와 편의점 보유 상품을 출력한다.
  * 영수증을 출력한다.
    * 구매상품내역
    * 증정상품내역
    * 금액 정보

* 재고 관리
  * resource/products.md, promitions.md를 통해 상품 및 프로모션 정보를 받아온다.
  * 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
  * 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
  * 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.


* 프로모션
  * 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
  * 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.
  * 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.
  * 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
  * 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
  * 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
  * 2+1일 때 나머지가 1일 경우 -> x
      * 2+1일 때 나머지가 2일 경우 -> o
  * 프로모션 재고가 부족할 때
      * 프로모션 콜라 10개, 일반 10개가 있을 때 사용자가 15개를 주문하면
      * 3개는 프로모션 진행, 2개는 프로모션 진행 x
  * 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.



* 멤버십 할인
  * 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
  * 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
  * 멤버십 할인의 최대 한도는 8000원이다.


* 영수증 출력
  * 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.


### 기능 구현 목록

* 파일 읽기 기능 구현
  * 마크다운 파일을 통해 정보를 받아오는 기능을 구현한다.
  * 파일이 없을 경우 IOException을 발생 시킨다.


* 프로모션 기능 구현
  * promotions.md를 통해 프로모션 정보를 받아온다.
  * 프로모션은 이름으로 구분된다.
  * 프로모션 별로 객체로 저장된다.
  * 프로모션 객체 구성
    * 이름
    * 필요한 구매물품 갯수
    * 증정 물품 갯수
    * 프로모션 시작일
    * 프로모션 마감일
    * 활성화 여부
  * 프로모션 활성화 여부는 오늘을 기준으로 프로모션 기간 내에 있으면 활성화된다.
  * 예외처리
    * 프로모션 이름이 비어있으면 안된다.
    * 프로모션 이름은 최대 50자까지 가능하다.
    * 프로모션에서 이름, 필요한 구매물품 갯수, 증정 물품 갯수, 프로모션 시작일, 프로모션 마감일 정보를 받을 수 있어야 함
    * 프로모션 시작일이 프로모션 마감일보다 늦을 수 없음
    * 구매 물품의 범위를 1 이상 100 이하로 한다.
    * 증정 물품의 범위는 1 이상 100 이하로 한다.


* 상품 기능 구현
  * 재고 객체를 관리하는 기능을 구현한다.
  * products.md를 통해 재고 정보를 받아온다.
  * 재고 객체는 이름, 가격, 잔여수량, 프로모션 정보를 포함한다.
  * 재고 객체는 이름과 프로모션 정보로 구분된다.
    * 중복된 이름과 프로모션 정보가 있을 경우, 기존 객체에 수량을 추가한다.
  * 예외처리
    * 이름과 프로모션이 중복되지만, 가격이 다른 경우, IllegalArgumentException을 발생시킨다.
    * 하나의 상품에 2개 이상의 프로모션이 올 수 없다.
    * 물품 가격의 범위를 100원 이상 10000000원 이하로 한다.
    * 물품 수량의 범위는 0 이상 10000 이하로 한다.
    * 재고 이름은 비어있으면 안된다.
    * 재고 이름은 최대 50자까지 가능하다.


* 재고관리 기능 구현
  * 재고와 프로모션 정보를 저장한다.
  * 마크다운파일로부터 재고와 프로모션 정보를 받아온다.
  * 원하는 프로모션 정보를 프로모션 이름을 통해 조회한다.
  * 구매 행위에 따라 재고를 업데이트한다.
  * 모든 재고 정보를 출력한다.


* 구매 기능 구현
  * 구매 기능을 구현한다.
  * 사용자로부터 구매 금액을 입력받는다.
  * 구매할려는 재고 이름이 없으면 없다고 안내한다.
  * 프로모션 제품을 우선적으로 받아온다.
  * 프로모션 제품이 없으면 일반 제품을 받아온다.
  * 일반 제품 제고가 없을 경우 안내한다.
  * 프로모션 적용 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우 안내한다.
  * 프로모션 재고가 부족할 경우 정가에 결제할지에 대해 안내한다.

* 현금 지불 기능 구현
  * 구매할 금액을 출력한다.
  * 멤버십 적용 여부를 받아 적용시킨다.
    * 프로모션 미적용 금액의 30%를 할인받는다. (프로모션 적용 상품을 제외한 금액)
    * 프로모션 적용 후 남은 금액에 대해 멤버십 할일을 적용한다.
    * 할인 금액을 8000원을 넘을 수 없다.

* 영수증 출력기능 구현
  * 구매 정보를 출력한다.
  * 구매 정보
    * 구매 품목
    * 증정 상품
    * 할인 금액
    * 내실 돈
    
