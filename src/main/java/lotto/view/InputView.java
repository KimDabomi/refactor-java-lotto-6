package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import lotto.model.Lotto;
import lotto.model.enums.ViewMessage;

public class InputView {
    public InputView() {
    }

    public String getPurchaseAmount() {
        System.out.println(ViewMessage.ASK_AMOUNT.getViewMessage());
        return Console.readLine();
    }

    public Lotto getWinningNumbers() {
        System.out.println(ViewMessage.ASK_NUMBERS.getViewMessage());
        String[] userNumbers = Console.readLine().split(",");
        List<Integer> winningNumbers = new ArrayList<>();

        for (String userNumber : userNumbers) {
            winningNumbers.add(Integer.parseInt(userNumber));
        }

        return new Lotto(winningNumbers);
    }

    public String getBonusNumber() {
        System.out.println(ViewMessage.ASK_BONUS_NUMBER.getViewMessage());
        return Console.readLine();
    }
}
