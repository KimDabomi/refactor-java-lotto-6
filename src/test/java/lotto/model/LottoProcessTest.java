package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import lotto.model.enums.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static lotto.model.enums.MagicVariable.LOTTO_PRICE;

class LottoProcessTest {
    private static Stream<Arguments> provideLottoTestData() {
        return Stream.of(
            Arguments.of("3000", new LottoProcess())
        );
    }

    private static Stream<Arguments> provideLottoPurchaseTestData() {
        return Stream.of(
                Arguments.of("3500", ErrorMessage.PRICE_ERROR_MESSAGE, new LottoProcess()),
                Arguments.of("-3000", ErrorMessage.PRICE_NEGATIVE_ERROR_MESSAGE, new LottoProcess()),
                Arguments.of("a3000", ErrorMessage.PRICE_TYPE_ERROR_MESSAGE, new LottoProcess())
        );
    }

    @ParameterizedTest
    @MethodSource("provideLottoTestData")
    @DisplayName("랜덤 번호 생성 확인")
    void 랜덤_번호_생성_확인(String purchaseMoney, LottoProcess lottoProcess) {
        Assertions.assertNotNull(lottoProcess.purchaseLotto(purchaseMoney));
    }

    @ParameterizedTest
    @MethodSource("provideLottoTestData")
    @DisplayName("로또 구매 금액에 맞는 개수인지 확인")
    void 로또_구매금액에_맞는_갯수인지_확인(String purchaseMoney, LottoProcess lottoProcess) {
        assertThat(lottoProcess.purchaseLotto(purchaseMoney).size()).isEqualTo(Integer.parseInt(purchaseMoney) / LOTTO_PRICE.getNumber());
    }

    @ParameterizedTest(name = "구매 금액: {0}, 예상결과: {1}")
    @MethodSource("provideLottoPurchaseTestData")
    @DisplayName("구매 금액별 예외 확인")
    void testLottoPurchaseMoneyException(String money, ErrorMessage errorMessage, LottoProcess lottoProcess) {
        assertThatThrownBy(() -> lottoProcess.purchaseLotto(money))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining(errorMessage.getErrorMessage());
    }
}