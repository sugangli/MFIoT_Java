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
public enum LogType {

    INPUT, // default input to pyterm
    OUTPUT, // default output from pyterm
    PACKET, // got a packet
    ERROR, // default error from pyterm
    ERROR_MESSAGE, // error message from us
    MESSAGE; // message from us
}
