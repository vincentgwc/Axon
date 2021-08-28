package org.vg.axon.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vg.axon.service.account.AccountQueryServiceImpl;

@RestController
@RequestMapping(value = "/bank-accounts")
//@Api(value = "Account Queries", description = "Account Query Events Endpoint", tags = "Account Queries")
public class AccountQueryController {

    private final AccountQueryServiceImpl accountQueryService;

    public AccountQueryController(AccountQueryServiceImpl accountQueryService) {
        this.accountQueryService = accountQueryService;
    }

    @GetMapping("/{accountNumber}/events")
    public List<Object> listEventsForAccount(@PathVariable(value = "accountNumber") String accountNumber){
        return accountQueryService.listEventsForAccount(accountNumber);
    }
}
