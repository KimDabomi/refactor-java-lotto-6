package lotto.model.exception;

import static lotto.model.enums.MagicVariable.LOTTO_PRICE;
import static lotto.model.enums.ErrorMessage.PRICE_ERROR_MESSAGE;
import static lotto.model.enums.ErrorMessage.PRICE_TYPE_ERROR_MESSAGE;
import static lotto.model.enums.ErrorMessage.PRICE_NEGATIVE_ERROR_MESSAGE;

public class LottoProcessException extends IllegalArgumentException {
    private static final String NUMBER_TYPE = "[+-]?\\d*(\\.\\d+)?";
    private LottoProcessException(String message) {
        super(message);
    }

    public static void checkLottoPriceRangeException(int money) {
        if (isLottoPriceRange(money)) {
            throw new LottoProcessException(PRICE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    public static void checkLottoPriceTypeException(String userMoney) {
        if (isLottoPriceType(userMoney)) {
            throw new LottoProcessException(PRICE_TYPE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    public static void checkLottoPriceNegativeException(String userMoney) {
        if (isLottoPriceNegative(userMoney)) {
            throw new LottoProcessException(PRICE_NEGATIVE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    private static boolean isLottoPriceRange(int money) {
        return money % LOTTO_PRICE.getNumber() != 0;
    }

    private static boolean isLottoPriceType(String userMoney) {
        return !userMoney.matches(NUMBER_TYPE);
    }

    private static boolean isLottoPriceNegative(String userMoney) {
        return userMoney.contains("-");
    }
}
