package lotto.controller;

import java.util.List;
import java.util.function.Supplier;
import lotto.model.Lotto;
import lotto.model.LottoProcess;
import lotto.model.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {
    private final OutputView outputView;
    private final InputView inputView;
    private final LottoProcess lottoProcess;


    public LottoController(OutputView outputView, InputView inputView, LottoProcess lottoProcess) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.lottoProcess = lottoProcess;
    }

    public void run() {
        int purchaseAmount = getValidPurchaseAmount();
        List<Lotto> purchaseLotto = getValidPurchaseLotto(String.valueOf(purchaseAmount));
        WinningLotto winningLotto = getValidWinningLotto();

        outputView.showPrizeResult(purchaseLotto, winningLotto, purchaseAmount);
    }

    private int getValidPurchaseAmount() {
        return executeWithRetry(() -> {
            String userPurchaseAmount = inputView.getPurchaseAmount();
            return lottoProcess.purchaseAmount(userPurchaseAmount);
        });
    }

    private List<Lotto> getValidPurchaseLotto(String userPurchaseAmount) {
        return executeWithRetry(() -> {
            int purchaseAmount = Integer.parseInt(userPurchaseAmount);
            List<Lotto> purchaseLotto = lottoProcess.purchaseLotto(String.valueOf(purchaseAmount));
            outputView.showPurchasedLottos(purchaseLotto);
            return purchaseLotto;
        });
    }

    private WinningLotto getValidWinningLotto() {
        return executeWithRetry(() -> {
            Lotto winningNumbers = inputView.getWinningNumbers();
            String bonusNumber = getValidBonusNumber(winningNumbers.getNumbers());
            return new WinningLotto(winningNumbers, bonusNumber);
        });
    }

    private String getValidBonusNumber(List<Integer> winningNumbers) {
        return executeWithRetry(() -> {
            String userBonusNumber = String.valueOf(inputView.getBonusNumber());
            new WinningLotto(new Lotto(winningNumbers), userBonusNumber);
            return userBonusNumber;
        });
    }

    private <T> T executeWithRetry(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
