package lotto.model;

import java.util.stream.Stream;
import lotto.model.enums.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    public static Stream<Arguments> provideLottoNumbersTestData() {
        return Stream.of(
            Arguments.of(List.of(1,2,3,4,5,6,7), ErrorMessage.NUMBERS_SIZE_ERROR_MESSAGE),
            Arguments.of(List.of(1,2,3,4,5,5), ErrorMessage.NUMBERS_DUPLICATION_ERROR_MESSAGE),
            Arguments.of(List.of(1,2,3,4,5,46), ErrorMessage.NUMBERS_RANGE_ERROR_MESSAGE)
        );
    }

    @ParameterizedTest(name = "로또 번호: {0}, 예상결과: {1}")
    @MethodSource("provideLottoNumbersTestData")
    @DisplayName("로또 번호 예외 확인")
    void testLottoNumbersException(List<Integer> numbers, ErrorMessage errorMessage) {
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining(errorMessage.getErrorMessage());
    }

    @DisplayName("로또 번호 정렬 확인")
    @Test
    void 로또_번호_정렬_확인() {
        Lotto lottoNumbers = new Lotto(List.of(2, 6, 5, 3, 8, 7));
        assertThat(lottoNumbers.getSortedNumbers()).isEqualTo("2, 3, 5, 6, 7, 8");
    }

    @DisplayName("보너스 번호 포함 여부 확인")
    @Test
    void 보너스_번호_포함_확인() {
        Lotto lottoNumbers = new Lotto(List.of(2, 6, 5, 3, 8, 7));
        String bonusNumbers = "7";
        assertThat(lottoNumbers.hasBonusNumber(bonusNumbers)).isEqualTo(true);
    }
}