package lotto.model;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import lotto.model.enums.ErrorMessage;
import lotto.model.enums.Prize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WinningLottoTest {
    private WinningLotto winningLottoTest;

    private static Stream<Arguments> providePrizeTestData() {
        return Stream.of(
            Arguments.of(List.of(1, 2, 3, 4, 5, 6), Prize.FIRST),
            Arguments.of(List.of(1, 2, 3, 4, 5, 7), Prize.SECOND),
            Arguments.of(List.of(1, 2, 3, 4, 5, 8), Prize.THIRD),
            Arguments.of(List.of(1, 2, 3, 4, 8, 9), Prize.FOURTH),
            Arguments.of(List.of(1, 2, 3, 8, 9, 10), Prize.FIFTH),
            Arguments.of(List.of(1, 2, 8, 9, 10, 11), Prize.NONE),
            Arguments.of(List.of(1, 12, 8, 9, 10, 11), Prize.NONE),
            Arguments.of(List.of(13, 12, 8, 9, 10, 11), Prize.NONE)
        );
    }

    private static Stream<Arguments> provideBonusNumberTestData() {
        return Stream.of(
            Arguments.of("6", ErrorMessage.BONUS_NUMBER_DUPLICATE_ERROR_MESSAGE),
            Arguments.of("-6", ErrorMessage.BONUS_NUMBER_NEGATIVE_ERROR_MESSAGE),
            Arguments.of("46", ErrorMessage.BONUS_NUMBER_RANGE_ERROR_MESSAGE),
            Arguments.of("abc", ErrorMessage.BONUS_NUMBER_TYPE_ERROR_MESSAGE)
        );
    }

    @BeforeEach
    void setUp() {
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        String bonusNumber = "7";
        winningLottoTest = new WinningLotto(winningLotto, bonusNumber);
    }

    @ParameterizedTest(name = "로또 번호: {0}, 예상결과: {1}")
    @MethodSource("providePrizeTestData")
    @DisplayName("로또 결과 확인")
    void testPrize(List<Integer> numbers, Prize expectedPrize) {
        Lotto userLotto = new Lotto(numbers);
        Prize prize = winningLottoTest.calculatePrize(userLotto);
        assertThat(prize).isEqualTo(expectedPrize);
    }

    @Test
    @DisplayName("보너스 번호 입력 확인")
    void 보너스_번호_입력_확인() {
        List<Integer> userLotto = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            userLotto.add(i);
        }
        String userBonusNumber = "7";
        int bonusNumber = winningLottoTest.checkValidBonusNumber(userBonusNumber, userLotto);
        assertThat(bonusNumber).isEqualTo(7);
    }

    @ParameterizedTest(name = "보너스 번호: {0}, 예상결과: {1}")
    @MethodSource("provideBonusNumberTestData")
    @DisplayName("보너스 번호 예외 확인")
    void testBonusNumberException(String userBonusNumber, ErrorMessage errorMessage) {
        List<Integer> winningLotto = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            winningLotto.add(i);
        }
        assertThatThrownBy(() -> winningLottoTest.checkValidBonusNumber(userBonusNumber, winningLotto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(errorMessage.getErrorMessage());
    }
}