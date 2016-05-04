/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiotconnector;

/**
 *
 * @author ubuntu
 */
public interface ILogCreator {
    public boolean addMessageListener(IMessageListener listener);
    public boolean removeMessageListener(IMessageListener listener);
}
