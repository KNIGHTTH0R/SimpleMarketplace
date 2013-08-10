/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import bank.IAccount;
import bank.IBank;
import server.IMarketplace;
import java.rmi.Naming;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.DefaultListModel;


/**
 *
 * @author alberto
 */
public class MarketCommunicator
   extends UnicastRemoteObject
   implements IMarketCommunicator
{
   private String idNumber;
   private IBank myBank;
   private IMarketplace marketplace;
   private MarketClient client;


   MarketCommunicator(MarketClient client, String idNumber, String bank, int money)
      throws RemoteException
   {
      this.client = client;
      this.idNumber = idNumber;


      try
      {
         myBank = (IBank) Naming.lookup(bank);

         marketplace = (IMarketplace) Naming.lookup("rmi://localhost/marketplace");
         marketplace.registerClient(this);
      }
      catch (Exception e)
      {
         System.err.println("The runtime failed: " + e.getMessage());
         System.exit(0);
      }
      //Create bank account with money;
      try
      {
         myBank.newAccount(idNumber);
      }
      catch (Exception re)
      {
         unregister();
         System.err.println("Error creating account.");
         System.exit(0);
      }

      try
      {
         myBank.getAccount(idNumber).deposit(money);
      }
      catch (Exception re)
      {
         unregister();
         System.err.println("Error loading the money.");
         System.exit(0);
      }


      client.updateAvailableMoney(money);
   }


   /**
    * Method to call the server that we are selling a new item 
    * in the marketplace
    * @param itemName
    * @param itemPrice 
    */
   void sellItem(String itemName, float itemPrice)
   {
      try
      {
         marketplace.addItem(itemName, itemPrice, idNumber);
      }
      catch (RemoteException e)
      {
         System.err.println(e.getMessage());
      }
   }


   /**
    * Method to add a new wish item
    * @param itemName
    * @param itemPrice 
    */
   void addWish(String itemName, float itemPrice)
   {
      try
      {
         marketplace.addWish(itemName, itemPrice, idNumber);
      }
      catch (RemoteException e)
      {
         System.err.println(e.getMessage());
      }

   }


   /**
    * Method to buy a item from the marketplace
    * @param itemName
    * @param itemPrice
    * @param itemOwner 
    */
   void buyItem(String itemName, float itemPrice)
   {
      try
      {
         boolean ok = marketplace.buyItem(itemName, itemPrice, idNumber);
         if (!ok)
            client.showNotification("It was not possible to make the purchase.");
      }
      catch (RemoteException e)
      {
         System.err.println(e.getMessage());
      }

   }


   /**
    * Method for the marketplace to notify us we need to update the money as
    * the transaction has finished
    * @throws RemoteException 
    */
   @Override
   public synchronized void updateAvailableMoney()
      throws RemoteException
   {
      float newmoney =
            ((IAccount) myBank.getAccount(String.valueOf(idNumber))).getBalance();
      client.updateAvailableMoney(newmoney);
   }


   /**
    * Method for the marketplace to notify us an update of the wishlist
    * @throws RemoteException 
    */
   @Override
   public synchronized void updateWishList(ArrayList<String> wishNames)
      throws RemoteException
   {
       DefaultListModel list = new DefaultListModel();
       //System.out.println("Wishes received:");
       for (String i : wishNames)
       {
          list.addElement(i);
          //System.out.println("  " + i);
       }
       
      client.updateWishList(list);
   }


   /**
    * Method for the marketplace to notify us of an update in the market list
    * @throws RemoteException 
    */
   @Override
   public synchronized void updateMarketList(ArrayList<String> itemNames)
      throws RemoteException
   {
       DefaultListModel list = new DefaultListModel();
       //System.out.println("Items received:");
       for (String i : itemNames)
       {
          list.addElement(i);
          //System.out.println("  " + i);
       }
       
      client.updateMarketPlace(list);
   }


   @Override
   public synchronized String getName()
      throws RemoteException
   {
      return idNumber;
   }
   
   
   @Override
   public synchronized void notifyOfPurchase(String itemName, float itemPrice)
      throws RemoteException
   {
      client.showNotification("You have acquired a " + itemName + " for " + 
                              itemPrice + " SEK.");
      updateAvailableMoney();
   }
   
   
   @Override
   public synchronized void notifyOfSale(String itemName, float itemPrice)
      throws RemoteException
   {
      client.showNotification("You have sold a " + itemName + " for " + 
                              itemPrice + " SEK.");
      updateAvailableMoney();
   }


   void unregister()
   {
      unregisterFromBank();
      unregisterFromMarketplace();
   }
   
   void unregisterFromBank()
   {
      try
      {
         myBank.deleteAccount(idNumber);
      }
      catch (RemoteException ex)
      {
         System.err.println("Error unregistering customer from bank.");
      }
   }
   
   void unregisterFromMarketplace()
   {
      try
      {
         marketplace.unregisterClient(this);
      }
      catch (RemoteException ex)
      {
         System.err.println("Error unregistering customer from marketplace.");
      }
   }
}
