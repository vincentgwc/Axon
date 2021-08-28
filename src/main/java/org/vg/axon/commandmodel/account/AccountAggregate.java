package org.vg.axon.commandmodel.account;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.vg.axon.coreapi.commands.account.CreateAccountCommand;
import org.vg.axon.coreapi.commands.account.CreditMoneyCommand;
import org.vg.axon.coreapi.commands.account.DebitMoneyCommand;
import org.vg.axon.coreapi.commands.account.FundTransferCommand;
import org.vg.axon.coreapi.commands.account.GenericAccountRollbackCommand;
import org.vg.axon.coreapi.event.account.AccountActivatedEvent;
import org.vg.axon.coreapi.event.account.AccountCreatedEvent;
import org.vg.axon.coreapi.event.account.AccountHeldEvent;
import org.vg.axon.coreapi.event.account.FundTransferEvent;
import org.vg.axon.coreapi.event.account.MoneyCreditedEvent;
import org.vg.axon.coreapi.event.account.MoneyDebitedEvent;
import org.vg.axon.coreapi.exceptions.account.CreateAccountException;
import org.vg.axon.coreapi.exceptions.account.CreditMoneyException;
import org.vg.axon.coreapi.exceptions.account.DebitMoneyException;
import org.vg.axon.coreapi.queries.account.Status;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String id;

    private double accountBalance;

    private String currency;

    private String status;

    public AccountAggregate() {
    }
    
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand){
        AggregateLifecycle.apply(new AccountCreatedEvent(createAccountCommand.id, createAccountCommand.accountBalance, createAccountCommand.currency));
    }
    
    @EventSourcingHandler
    protected void on(AccountCreatedEvent accountCreatedEvent){
        this.id = accountCreatedEvent.id;
        this.accountBalance = accountCreatedEvent.accountBalance;
        this.currency = accountCreatedEvent.currency;
        this.status = String.valueOf(Status.CREATED);
        AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
    }
    
    @CommandHandler
    protected void on(CreditMoneyCommand creditMoneyCommand){
        AggregateLifecycle.apply(new MoneyCreditedEvent(creditMoneyCommand.id, creditMoneyCommand.creditAmount, creditMoneyCommand.currency));
    }
    
    @EventSourcingHandler
    protected void on(MoneyCreditedEvent moneyCreditedEvent){

        if (this.accountBalance < 0 & (this.accountBalance + moneyCreditedEvent.creditAmount) >= 0){
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
        }

        this.accountBalance += moneyCreditedEvent.creditAmount;
    }
    
    @CommandHandler
    protected void on(DebitMoneyCommand debitMoneyCommand){
    	
    	if(debitMoneyCommand.debitAmount <= 0 ) {
    		throw new DebitMoneyException(debitMoneyCommand.id);
    	}
    	
        AggregateLifecycle.apply(new MoneyDebitedEvent(debitMoneyCommand.id, debitMoneyCommand.debitAmount, debitMoneyCommand.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvent moneyDebitedEvent){

        if (this.accountBalance >= 0 & (this.accountBalance - moneyDebitedEvent.debitAmount) < 0){
            AggregateLifecycle.apply(new AccountHeldEvent(this.id, Status.HOLD));
        }

        this.accountBalance -= moneyDebitedEvent.debitAmount;

    }
    
    @EventSourcingHandler
    protected void on(AccountHeldEvent accountHeldEvent){
        this.status = String.valueOf(accountHeldEvent.status);
    }
    
    @CommandHandler
    protected void on(FundTransferCommand fundTransferCommand) {
    	AggregateLifecycle.apply(new FundTransferEvent(fundTransferCommand.id,fundTransferCommand.fromAcc, fundTransferCommand.toAcc, fundTransferCommand.txnAmount, fundTransferCommand.currency));
    }
    
    @EventSourcingHandler
    protected void on(FundTransferEvent fundTransferEvent){
    	this.id = fundTransferEvent.id;
    	AggregateLifecycle.apply(new MoneyCreditedEvent(fundTransferEvent.fromAcc, fundTransferEvent.txnAmount, fundTransferEvent.currency));
    	AggregateLifecycle.apply(new MoneyDebitedEvent(fundTransferEvent.toAcc, fundTransferEvent.txnAmount, fundTransferEvent.currency));
    }
    
    @ExceptionHandler
    protected void on(CreateAccountException ex) {
    	System.out.println("CreateAccountException Rollback");
    }
 
    @ExceptionHandler
    protected void on(CreditMoneyException ex) {
    	System.out.println("CreateMoneyException Rollback");
    }
    
    @ExceptionHandler
    protected void on(DebitMoneyException ex) {
    	System.out.println("DebitMoneyException Rollback");
    }
    
    @CommandHandler
    protected void on(GenericAccountRollbackCommand cmd) {
    	System.out.println("GenericAccountRollbackCommand : " + cmd.errorMsg);
    }
}
