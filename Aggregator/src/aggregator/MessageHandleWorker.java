/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aggregator;

/**
 *
 * @author suggang
 */
import java.io.UnsupportedEncodingException;

public class MessageHandleWorker implements Runnable{
	
	private byte[] msg;
	
	
	public MessageHandleWorker (byte[] buf){	
		this.msg = buf;        
	}

	@Override
	public void run() {
		
		System.out.println("Do something with the msg:"+this.msg);
		// TODO Auto-generated method stub
		
	}

}
