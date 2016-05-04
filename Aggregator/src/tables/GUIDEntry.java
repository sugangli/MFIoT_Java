/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tables;

/**
 *
 * @author suggang
 */
public class GUIDEntry {
    private byte[] _luid;
    private byte[] _guid;
    private String _type;
    
    
    public GUIDEntry(byte[] LUID, byte[] GUID, String type){
        _luid = LUID;
        _guid = GUID;
        _type = type;
    }

    public byte[] getLuid() {
        return _luid;
    }

    public byte[] getGuid() {
        return _guid;
    }

    public void setLuid(byte[] _luid) {
        this._luid = _luid;
    }

    public void setGuid(byte[] _guid) {
        this._guid = _guid;
    }

    public void setType(String _type) {
        this._type = _type;
    }
    
    
    public String getType(){
        return _type;
    }
    
    public int getGuidInt (){
        byte[] buf = getGuid();
        byte[] tmp = new byte[4];
        System.arraycopy(buf, buf.length-4, tmp, 0, 4);
        return java.nio.ByteBuffer.wrap(tmp).getInt();
    }
    
        
}
