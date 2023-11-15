package lotto.model.enums;

public enum ViewMessage {
    ASK_AMOUNT("구입금액을 입력해 주세요."),
    ASK_NUMBERS("당첨숫자를 입력해 주세요."),
    ASK_BONUS_NUMBER("보너스 번호를 입력해 주세요."),
    SHOW_QUANTITY("%d개를 구매했습니다.\n"),
    SHOW_LOTTO("[%s]"),
    SHOW_PRIZE("\n당첨 통계"),
    SHOW_LINE("---"),
    SHOW_RESULT_PERSENT("총 수익률은 %.1f%%입니다.\n"),
    SHOW_LOTTO_RESULT("%d개 일치 (%s원) - %d개"),
    SHOW_BONUS_LOTTO_RESULT("%d개 일치, 보너스 볼 일치 (%s원) - %d개"),
    PRICE_FORMAT("###,###");

    private final String viewMessage;

    ViewMessage(String viewMessage) {
        this.viewMessage = viewMessage;
    }

    public String getViewMessage() {
        return viewMessage;
    }
}
