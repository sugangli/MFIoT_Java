/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tables;


import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author suggang
 */
public class GUIDTable {
    private final  LinkedList<GUIDEntry>  _guidtable;
    
    public GUIDTable(){
        _guidtable = new LinkedList<> ();
   
    }
    
    public void add(GUIDEntry g_entry){
        _guidtable.addFirst(g_entry);
    }
    
    public void deleteByGUID(byte[] GUID){
        
        for(GUIDEntry g : _guidtable){
            if (Arrays.equals(g.getGuid(),GUID)){
                _guidtable.remove(_guidtable.indexOf(g));
            }
        }
        
    }
    
    public void deleteByLUID(byte[] LUID){
        for(GUIDEntry g : _guidtable){
            if (Arrays.equals(g.getLuid(),LUID)){
                _guidtable.remove(_guidtable.indexOf(g));
            }
        }
    }
    
    public void updatebyGUID(byte[] GUID, GUIDEntry g_entry){
        
        for(GUIDEntry g : _guidtable){//first remove all related entries, then add to the end 
            if (Arrays.equals(g.getGuid(),GUID)){
                _guidtable.remove(_guidtable.indexOf(g_entry));
            }
        }
        _guidtable.addFirst(g_entry);
        
    }
    
    public void updateLUID(byte[] LUID, GUIDEntry g_entry){
    
        for(GUIDEntry g : _guidtable){//first remove all related entries, then add to the end 
            if (Arrays.equals(g.getLuid(),LUID)){
                _guidtable.remove(_guidtable.indexOf(g_entry));
            }
        }
        _guidtable.addFirst(g_entry);
    }
    
    public  GUIDEntry lookupByGUID(byte[] GUID){
        for (GUIDEntry g : this._guidtable) {
            if (Arrays.equals(g.getGuid(),GUID)){
                return g;
            }
        }
        return null;
        
    
    }
    
    public GUIDEntry lookupByLUID(byte[] LUID){
        
        for(GUIDEntry g : _guidtable){//lookup the first matched entry
            if (Arrays.equals(g.getLuid(),LUID)){
                return g;
            }
        }
        return null;
        
    
    }
    
}
