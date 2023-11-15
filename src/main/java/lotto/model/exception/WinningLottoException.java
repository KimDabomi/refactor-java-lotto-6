package lotto.model.exception;

import static lotto.model.enums.MagicVariable.NUMBERS_MAX_RANGE;
import static lotto.model.enums.MagicVariable.NUMBERS_MIN_RANGE;
import static lotto.model.enums.ErrorMessage.BONUS_NUMBER_TYPE_ERROR_MESSAGE;
import static lotto.model.enums.ErrorMessage.BONUS_NUMBER_NEGATIVE_ERROR_MESSAGE;
import static lotto.model.enums.ErrorMessage.BONUS_NUMBER_RANGE_ERROR_MESSAGE;
import static lotto.model.enums.ErrorMessage.BONUS_NUMBER_DUPLICATE_ERROR_MESSAGE;

import java.util.List;

public class WinningLottoException extends IllegalArgumentException{
    private static final String NUMBER_TYPE = "[+-]?\\d*(\\.\\d+)?";

    public WinningLottoException(String message) {
        super(message);
    }

    public static void checkBonusNumberTypeException(String userBonusNumber) {
        if (isBonusNumberType(userBonusNumber)) {
            throw new WinningLottoException(BONUS_NUMBER_TYPE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    public static void checkBonusNumberIntegerExceptions(List<Integer> winningLotto, int bonusNumber) {
        checkBonusNumberNegativeException(bonusNumber);
        checkBonusNumberRangeException(bonusNumber);
        checkBonusNumberDuplicationException(winningLotto, bonusNumber);
    }

    private static void checkBonusNumberNegativeException(int userBonusNumber) {
        if (isBonusNumberNegative(userBonusNumber)) {
            throw new WinningLottoException(BONUS_NUMBER_NEGATIVE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    private static void checkBonusNumberRangeException(int bonusNumber) {
        if (isBonusNumberRange(bonusNumber)) {
            throw new WinningLottoException(BONUS_NUMBER_RANGE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    private static void checkBonusNumberDuplicationException(List<Integer> winningLotto, int bonusNumber) {
        if (winningLotto.contains(bonusNumber)) {
            throw new WinningLottoException(BONUS_NUMBER_DUPLICATE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    private static boolean isBonusNumberType(String userBonusNumber) {
        return !userBonusNumber.matches(NUMBER_TYPE);
    }

    private static boolean isBonusNumberNegative(int userBonusNumber) {
        return userBonusNumber <= NUMBERS_MIN_RANGE.getNumber();
    }

    private static boolean isBonusNumberRange(int bonusNumber) {
        return bonusNumber < NUMBERS_MIN_RANGE.getNumber() || bonusNumber > NUMBERS_MAX_RANGE.getNumber();
    }
}
