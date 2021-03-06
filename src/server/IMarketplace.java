/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.IMarketCommunicator;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 *
 * @author Alberto Lorente Leal <albll@kth.se>, <a.lorenteleal@gmail.com>
 */
public interface IMarketplace
   extends Remote
{
   public void registerClient(IMarketCommunicator com)
      throws RemoteException;
   
   
   public void unregisterClient(IMarketCommunicator com)
      throws RemoteException;


   public void addItem(String name, float price, String ownerId)
      throws RemoteException;


   public void addWish(String name, float price, String customerId)
      throws RemoteException;


   public boolean buyItem(String name, float price, String ownerId)
      throws RemoteException;
}
