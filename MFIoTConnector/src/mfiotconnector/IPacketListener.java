/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiotconnector;

import packets.Packet;

/**
 *
 * @author ubuntu
 */
public interface IPacketListener {
    public enum PacketMessageType {

        SEND,
        RECEIVE;
    }

    public void handlePacket(PacketMessageType type, Packet packet);
}
