package org.vg.axon.service.account;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.vg.axon.coreapi.commands.account.CreateAccountCommand;
import org.vg.axon.coreapi.commands.account.CreditMoneyCommand;
import org.vg.axon.coreapi.commands.account.DebitMoneyCommand;
import org.vg.axon.vo.AccountCreateVO;
import org.vg.axon.vo.FundTransferVO;
import org.vg.axon.vo.MoneyCreditVO;
import org.vg.axon.vo.MoneyDebitVO;

@Service
public class AccountCommandServiceImpl {
	private final CommandGateway commandGateway;

    public AccountCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> createAccount(AccountCreateVO accountCreateDTO) {
        return commandGateway.send(new CreateAccountCommand(UUID.randomUUID().toString(), accountCreateDTO.getStartingBalance(), accountCreateDTO.getCurrency()));
    }

    public CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditVO moneyCreditDTO) {
        return commandGateway.sendAndWait(new CreditMoneyCommand(accountNumber, moneyCreditDTO.getCreditAmount(), moneyCreditDTO.getCurrency()), 5, TimeUnit.SECONDS);
    }

    public CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitVO moneyDebitDTO) {
        return commandGateway.send(new DebitMoneyCommand(accountNumber, moneyDebitDTO.getDebitAmount(), moneyDebitDTO.getCurrency()));
    }
    
    public CompletableFuture<String> performFundTransfer(String uuid, FundTransferVO fundTransferVO) {
    	System.out.println("uuid" + uuid);
    	CompletableFuture<String> outcome = commandGateway.send(new CreateAccountCommand(uuid, 0, "MYR"))
    	.thenCompose(result -> commandGateway.send(new CreditMoneyCommand(uuid,fundTransferVO.txnAmount,fundTransferVO.accountTo)))
    	.thenCompose(result -> commandGateway.send(new DebitMoneyCommand(uuid,fundTransferVO.txnAmount,fundTransferVO.accountTo)));
    	
		return outcome;
    }
}
