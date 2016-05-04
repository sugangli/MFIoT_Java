/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packets;

import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author suggang
 */
public class MFIoTAppPacket extends Packet {

    private static final int PACKET_HEADER_FIXED_SIZE = 8;
    private static final int MFIoT_APP_PACKET_INT_TYPE = 1;
    private static final String MFIoT_APP_PACKET_TYPE = "NETTYPE_MF_LITE";
    
    public static final int POSITION_VER_TYP = 0;
    public static final int POSITION_SID_PORT = 1;
    public static final int POSITION_TTL_LEN_HIGH = 2;
    public static final int POSITION_LEN_LOW = 3;
    public static final int POSITION_NONCE = 4;
    public static final int POSITION_SRC_LEN = 6;
    public static final int POSITION_DST_LEN = 8;

    public MFIoTAppPacket(byte[] buf, int intType, String type, String description, Packet next) {
        super(buf, intType, type, description, next);

    }

    public MFIoTAppPacket(Packet p) {
        super(p.getBuf(), p.getIntType(), p.getType(), p.getDescription(), p.getNext());

    }

    public MFIoTAppPacket(int ver, int sid, int prot, int ttl, int length, byte[] src, byte[] dst, int nonce, Packet next) {
        super(new byte[PACKET_HEADER_FIXED_SIZE], MFIoT_APP_PACKET_INT_TYPE, MFIoT_APP_PACKET_TYPE, "", next);
        setSid(sid);
        setVersion(ver);
        setProt(prot);
        setTtl(ttl);
        setLength(length);
        setSrcLength(src.length);
        setDstLength(dst.length);
        setSrc(src);
        setDst(dst);
        setNonce(nonce);
    }

    public int getNonce() {
        byte[] buf = getBuf();
        return ((buf[4] << 8) & 0xFF) | ((buf[5]));
    }

    public void setNonce(int nonce) {
        byte[] buf = getBuf();
        buf[4] = (byte) ((nonce >> 8) & 0xFF);
        buf[5] = (byte) (nonce & 0xFF);
    }

    public int getSrcLength() {
        byte[] buf = getBuf();
        return (buf[6] & 0xFF);
    }

    public int getDstLength() {
        byte[] buf = getBuf();
        return (buf[7] & 0xFF);
    }

    private void setSrcLength(int srcLength) {
        byte[] buf = getBuf();
        buf[6] = (byte) (srcLength & 0xFF);
    }

    private void setDstLength(int dstLength) {
        byte[] buf = getBuf();
        buf[7] = (byte) (dstLength & 0xFF);
    }

    public void setSrc(byte[] src) {
        byte[] buf = getBuf();
        int originalSrcLength = getSrcLength();
        if (src.length != originalSrcLength) {
            byte[] tmp = new byte[PACKET_HEADER_FIXED_SIZE + src.length + getDstLength()];
            System.arraycopy(buf, 0, tmp, 0, PACKET_HEADER_FIXED_SIZE);
            System.arraycopy(buf, PACKET_HEADER_FIXED_SIZE + originalSrcLength, tmp, PACKET_HEADER_FIXED_SIZE + src.length, getDstLength());
            System.arraycopy(src, 0, tmp, PACKET_HEADER_FIXED_SIZE, src.length);
            setBuf(tmp);
            setSrcLength(src.length);
        } else {
            System.arraycopy(src, 0, buf, PACKET_HEADER_FIXED_SIZE, src.length);
        }
    }

    public void setDst(byte[] dst) {
        byte[] buf = getBuf();
        int originalDstLength = getDstLength();
        if (dst.length != originalDstLength) {
            byte[] tmp = new byte[PACKET_HEADER_FIXED_SIZE + getSrcLength() + dst.length];
            System.arraycopy(buf, 0, tmp, 0, PACKET_HEADER_FIXED_SIZE);
            System.arraycopy(buf, PACKET_HEADER_FIXED_SIZE, tmp, PACKET_HEADER_FIXED_SIZE, getSrcLength());
            System.arraycopy(dst, 0, tmp, PACKET_HEADER_FIXED_SIZE + getSrcLength(), dst.length);
            setBuf(tmp);
            setDstLength(dst.length);
        } else {
            System.arraycopy(dst, 0, buf, PACKET_HEADER_FIXED_SIZE + getSrcLength(), dst.length);
        }

    }

    public int getVersion() {
       return getUpper4Bits(getBuf(), POSITION_VER_TYP);
    }

    public void setVersion(int version) {
        setUpper4Bits(getBuf(), POSITION_VER_TYP, version);
    }
    
    public int getInitType() {
        return getLower4Bits(getBuf(), POSITION_VER_TYP);
    }
    
    public void setInitType(int initType) {
        setLower4Bits(getBuf(), POSITION_VER_TYP, initType);
    }

    public byte[] getSrc() {
        byte[] buf = getBuf();
        byte[] tmp = new byte[getSrcLength()];
        System.arraycopy(buf, PACKET_HEADER_FIXED_SIZE, tmp, 0, getSrcLength());
        if (getSrcLength() == 2) {
            tmp = flipBytes(tmp);
            return tmp;
        }
        return tmp;
    }
    
    public byte[] getDst() {
        byte[] buf = getBuf();
        byte[] tmp = new byte[getDstLength()];
        System.arraycopy(buf, PACKET_HEADER_FIXED_SIZE + getSrcLength(), tmp, 0, getDstLength());
        if (getDstLength() == 2) {
            tmp = flipBytes(tmp);
            return tmp;
        }
        return tmp;
    }
    
    public void setSid(int sid) {
        setUpper4Bits(getBuf(), 1, sid);
    }

    public int getSid() {
        return getUpper4Bits(getBuf(), 1);
    }

    public void setProt(int prot) {
        setLower4Bits(getBuf(), 1, prot);
    }

    public int getProt() {
        return getLower4Bits(getBuf(), 1);
    }

    public void setTtl(int ttl) {
        setUpper4Bits(getBuf(), 2, ttl);
    }

    public int getTtl() {
        return getUpper4Bits(getBuf(), 2);
    }

    public void setLength(int length) {
        byte[] buf = getBuf();
        byte tmp = (byte) (buf[2] & 0xF0);
        buf[2] = (byte) (tmp | (length >> 8 & 0xF));
        buf[3] = (byte) ((length & 0xFF));
    }

    public int getLength() {
        byte[] buf = getBuf();
        return (buf[2] & 0xF) << 8 | buf[3];
    }

    public static byte[] flipBytes(byte[] arr) {

        byte tmp = arr[0];
        arr[0] = arr[1];
        arr[1] = tmp;

        return arr;

    }
}
