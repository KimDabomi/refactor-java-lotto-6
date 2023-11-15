package lotto.view;

import java.text.DecimalFormat;
import java.util.List;
import javax.swing.text.View;
import lotto.model.Lotto;
import lotto.model.enums.Prize;
import lotto.model.WinningLotto;
import lotto.model.enums.ViewMessage;

public class OutputView {
    int totalPrize = 0;

    public void showPurchasedLottos(List<Lotto> lottos) {
        System.out.printf(ViewMessage.SHOW_QUANTITY.getViewMessage(), lottos.size());

        for (Lotto lotto : lottos) {
            System.out.println();
            System.out.printf(ViewMessage.SHOW_LOTTO.getViewMessage(), lotto.getSortedNumbers());
        }
    }
    
    public void showPrizeResult(List<Lotto> lottos, WinningLotto winningLotto, int purchaseAmount) {
        System.out.println(ViewMessage.SHOW_PRIZE.getViewMessage());
        System.out.println(ViewMessage.SHOW_LINE.getViewMessage());

        int[] matchCounts = new int[Prize.values().length];

        totalPrize = getTotalPrize(lottos, winningLotto, matchCounts, totalPrize);
        printFinalResult(matchCounts);

        double profitRate = (totalPrize / (double) purchaseAmount) * 100;
        System.out.printf(ViewMessage.SHOW_RESULT_PERSENT.getViewMessage(), profitRate);
    }

    private void printFinalResult(int[] matchCounts) {
        for (int i = Prize.values().length - 1; i >= 0; i--) {
            Prize prize = Prize.values()[i];
            DecimalFormat decFormat = new DecimalFormat(ViewMessage.PRICE_FORMAT.getViewMessage());
            int prizeMatchCount = prize.getMatchCount();
            String prizeMoney = decFormat.format(prize.getPrizeMoney());
            int prizeCount = matchCounts[prize.ordinal()];
            formatResult(prize, prizeMatchCount, prizeMoney, prizeCount);
        }
    }

    private void formatResult(Prize prize, int prizeMatchCount, String prizeMoney, int prizeCount) {
        if (prize != Prize.NONE && prize != Prize.SECOND) {
            System.out.println();
            System.out.printf(ViewMessage.SHOW_LOTTO_RESULT.getViewMessage(), prizeMatchCount, prizeMoney, prizeCount);
        }
        if (prize == Prize.SECOND) {
            System.out.println();
            System.out.printf(ViewMessage.SHOW_BONUS_LOTTO_RESULT.getViewMessage(), prizeMatchCount, prizeMoney, prizeCount);
        }
    }

    private int getTotalPrize(List<Lotto> lottos, WinningLotto winningLotto, int[] matchCounts, int totalPrize) {
        for (Lotto lotto : lottos) {
            Prize prize = winningLotto.calculatePrize(lotto);
            matchCounts[prize.ordinal()]++;
            totalPrize += prize.getPrizeMoney();
        }
        return totalPrize;
    }
}
