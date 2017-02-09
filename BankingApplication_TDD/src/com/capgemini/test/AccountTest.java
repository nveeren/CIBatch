package com.capgemini.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

public class AccountTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}
	
	
	/*
	 * create account
	 * 1.when the amount is less than 500 system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */

	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
    public void whenTheAmountIsLessThanFiveHundredSystemShouldThrowException() throws InsufficientInitialAmountException
    {
		accountService.createAccount(101, 400);
    }
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account,accountService.createAccount(101, 5000));
	}
	
	/*
	 * Deposit Amount
	 * 1.when the account number is invalid ,Invalid account number message should be shown.
	 * 2.when the valid info is passed amount should be credited successfully
	 */
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
    public void whenTheAccountNotFoundShouldThrowException() throws InvalidAccountNumberException
    {
		accountService.depositAmount(101, 400);
    }
	
	@Test
	public void whenTheValidInfoIsPassedamountShouldBeCreditedSuccessfully() throws InvalidAccountNumberException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.searchAccount(101)).thenReturn(account);
		assertEquals(account.getAmount(),accountService.depositAmount(101, 5000));
	}
	
	/*
	 * Withdraw Amount
	 * 1.when the account number is invalid ,Invalid account number message should be shown.
	 * 2.when the withdrawal amount is greater than account balance then insufficient balance exception message should be thrown
	 * 3.When the valid information is passed then amount should be debited successfully.
	 */
	/*@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
    public void whenTheAccounBalanceIsLessThanWithdrawAmountThenInsufficientFundsExceptionShouldBeThrown() throws InsufficientBalanceException
    {
		
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		//accountService.withdrawAmount(101, 400);
        if(account.getAmount()<5000){
        	throw InsufficientBalanceException;
        }
    }*/
	
	@Test
	public void whenTheValidInfoIsPassedamountShouldBeDebitedSuccessfully() throws InsufficientInitialAmountException, InvalidAccountNumberException, InsufficientBalanceException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
			
		when(accountRepository.searchAccount(101)).thenReturn(account);
		assertEquals(account.getAmount(),accountService.withdrawAmount(101, 5000));
	}
	
	
	
	
}
