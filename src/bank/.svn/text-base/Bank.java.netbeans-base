package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial")
public class Bank
   extends UnicastRemoteObject
   implements IBank
{
   private String bankName;
   private Map<String, IAccount> accounts = new HashMap<String, IAccount>();


   public Bank(String bankName)
      throws RemoteException
   {
      super();
      this.bankName = bankName;
   }


   @Override
   public synchronized String[] listAccounts()
   {
      return accounts.keySet().toArray(new String[1]);
   }


   @Override
   public synchronized IAccount newAccount(String name)
      throws RemoteException,
             RejectedException
   {
      Account account = (Account) accounts.get(name);
      if (account != null)
      {
         System.out.println("Account [" + name + "] exists!!!");
         throw new RejectedException("Rejected: Bank: " + bankName
                                     + " Account for: " + name + " already exists: " + account);
      }
      account = new Account(name);
      accounts.put(name, account);
      System.out.println("Bank: " + bankName + " Account: " + account
                         + " has been created for " + name);
      return account;
   }


   @Override
   public synchronized IAccount getAccount(String name)
   {
      return accounts.get(name);
   }


   @Override
   public synchronized boolean deleteAccount(String name)
   {
      if (!hasAccount(name))
      {
         return false;
      }
      accounts.remove(name);
      System.out.println("Bank: " + bankName
                         + " Account for " + name + " has been deleted");
      return true;
   }


   private boolean hasAccount(String name)
   {
      if (accounts.get(name) == null)
      {
         return false;
      }
      else
      {
         return true;
      }
   }
}
