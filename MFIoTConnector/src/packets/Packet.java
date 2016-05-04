/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packets;

import java.util.Arrays;

/**
 *
 * @author ubuntu
 */
public class Packet {

    private int _intType;
    private String _type;
    private byte[] _buf;
    private String _description;
    private Packet _next;

    public Packet(byte[] buf, int intType, String type, String description, Packet next) {
        _buf = buf;
        _intType = intType;
        _type = type;
        _description = description;
        _next = next;
    }

    public byte[] getBuf() {
        return _buf;
    }

    public void setBuf(byte[] buf) {
        _buf = buf;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }

    public Packet getNext() {
        return _next;
    }

    public void setNext(Packet _next) {
        this._next = _next;
    }

    public int getIntType() {
        return _intType;
    }

    public void setIntType(int _intType) {
        this._intType = _intType;
    }

    public String getType() {
        return _type;
    }

    public void setType(String _type) {
        this._type = _type;
    }

    @Override
    public String toString() {
        return String.format("Type: %s (%d)%nBuf:%s%n%s%n%s", _type, _intType, Arrays.toString(_buf), _description, _next);
    }
    
    public static void setUpper4Bits(byte[] buf, int position, int value) {
        buf[position] = (byte)((buf[position] & 0xF) | ((value & 0xF) << 4));
    }
    
    public static void setLower4Bits(byte[] buf, int position, int value) {
        buf[position] = (byte)((buf[position] & 0xF0) | ((value & 0xF)));
    }
    
    public static int getUpper4Bits(byte byt) {
        return (byt & 0xF0) >> 4;
    }

    public static int getUpper4Bits(byte[] buf, int position) {
        return getUpper4Bits(buf[position]);
    }
    
    public static int getLower4Bits(byte byt) {
        return (byt & 0xF);
    }

    public static int getLower4Bits(byte[] buf, int position) {
        return getLower4Bits(buf[position]);
    }
}
