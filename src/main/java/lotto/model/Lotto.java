package lotto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lotto.model.exception.LottoException;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        LottoException.checkNumbersExceptions(numbers);
        this.numbers = numbers;
    }

    public List<Integer> getNumbers() {
        return new ArrayList<>(numbers);
    }



    public boolean hasBonusNumber(String userBonusNumber) {
        int bonusNumber = Integer.valueOf(userBonusNumber);
        return numbers.contains(bonusNumber);
    }

    public int countMatchNumbers(Lotto winningLotto) {
        return (int) numbers.stream().filter((Predicate<? super Integer>) winningLotto.numbers::contains).count();
    }

    public String getSortedNumbers() {
        List<Integer> sortedNumbers = sortNumbers();
        return joinNumbers(sortedNumbers);
    }

    private List<Integer> sortNumbers() {
        return numbers.stream().sorted().collect(Collectors.toList());
    }

    private String joinNumbers(List<Integer> sortedNumbers) {
        return sortedNumbers.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }
}
