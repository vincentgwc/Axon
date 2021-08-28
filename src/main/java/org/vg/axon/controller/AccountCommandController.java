package org.vg.axon.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vg.axon.service.account.AccountCommandServiceImpl;
import org.vg.axon.vo.AccountCreateVO;
import org.vg.axon.vo.FundTransferVO;
import org.vg.axon.vo.MoneyCreditVO;
import org.vg.axon.vo.MoneyDebitVO;

@RestController
@RequestMapping(value = "/bank-accounts")
//@Api(value = "Account Commands", description = "Account Commands Related Endpoints", tags = "Account Commands")
public class AccountCommandController {

    private final AccountCommandServiceImpl accountCommandService;

    public AccountCommandController(AccountCommandServiceImpl accountCommandService) {
        this.accountCommandService = accountCommandService;
    }

    @PostMapping
    public CompletableFuture<String> createAccount(@RequestBody AccountCreateVO accountCreateDTO){
        return accountCommandService.createAccount(accountCreateDTO);
    }

    @PutMapping(value = "/credits/{accountNumber}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "accountNumber") String accountNumber,
                                                          @RequestBody MoneyCreditVO moneyCreditDTO) throws Exception{
        return accountCommandService.creditMoneyToAccount(accountNumber, moneyCreditDTO);
    }

    @PutMapping(value = "/debits/{accountNumber}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "accountNumber") String accountNumber,
                                                           @RequestBody MoneyDebitVO moneyDebitDTO){
        return accountCommandService.debitMoneyFromAccount(accountNumber, moneyDebitDTO);
    }
    
    @PostMapping(value = "/fundTransfer")
    public CompletableFuture<String> performFundTransfer(@RequestBody FundTransferVO fundTransferVO){ 
    	return accountCommandService.performFundTransfer(UUID.randomUUID().toString(), fundTransferVO);
    }
}