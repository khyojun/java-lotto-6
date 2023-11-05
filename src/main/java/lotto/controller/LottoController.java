package lotto.controller;

import java.util.List;
import java.util.stream.Collectors;
import lotto.Lotto;
import lotto.service.InputMoneyService;
import lotto.service.InputWinnerNumberService;
import lotto.util.LottoGenerator;
import lotto.util.Validator;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {


    private final InputView inputView;
    private final OutputView outputView;
    private final Validator validator;




    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.validator = new Validator();
    }

    public void start() {
        Long money = inputMoney(new InputMoneyService());

        buyLottoTicket(money/1000);



        List<Integer> lottoWinNumbers = inputWinNumbers(new InputWinnerNumberService());


        /*
            금액에 따른 로또 번호 뽑기!

         */

        /*
        당첨 번호 입
         */

    }

    private void buyLottoTicket(long count) {
        LottoGenerator lottoGenerator = LottoGenerator.getInstance();
        for(int i=0; i<count; i++){
            outputView.printTicket(lottoGenerator.generateNumberList());
        }

    }


    private Long inputMoney(InputMoneyService inputMoneyService) {
        try {
            outputView.printBeforeInputMoney();
            inputMoneyService.getRightMoneyProcess(validator, inputView.inputMoney());
            return Long.parseLong(inputView.inputMoney());
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return inputMoney(inputMoneyService);
        }
    }

    private List<Integer> inputWinNumbers(InputWinnerNumberService inputWinnerNumberService) {
        inputWinnerNumberService.init();
        try {
            inputWinnerNumberService.checkRightWinnerNumbers(validator,
                inputView.inputWinnerNumbers());
            return inputWinnerNumberService.convertedWinnerNumbers();
        }catch (IllegalArgumentException e){
            outputView.printErrorMessage(e.getMessage());
            return inputWinNumbers(inputWinnerNumberService);
        }
    }

}
