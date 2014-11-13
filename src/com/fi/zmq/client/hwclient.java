package com.fi.zmq.client;

import org.zeromq.ZMQ;


public class hwclient {

public static void main(String[] args) {
	String HOST_IP = "tcp://192.168.10.7:5555";
    ZMQ.Context context = ZMQ.context(1);
    IoAdapter ioa = new IoAdapter();
    
    //  Socket to talk to server
    System.out.println("Connecting to fi mgt server...");

    ZMQ.Socket requester = context.socket(ZMQ.REQ);
    requester.connect(HOST_IP);
    
    
    String requestT = ioa.get_all_pins();
    System.out.println("Request: "+requestT);
    requester.send(requestT.getBytes(),0);
	byte[] replyT = requester.recv(0);
	System.out.println("Received " + new String(replyT) + ".");
	
    
    /**
     *  Test case for group set command
     */
    
    /*
    int[] pinArray =  new int[4];
    pinArray[0] = 1;
    pinArray[1] = 1;
    pinArray[2] = 1;
    pinArray[3] = 1;
    String requestT = ioa.set_all_pins(pinArray);
    System.out.println("request: "+requestT);
    requester.send(requestT.getBytes(),0);
	byte[] replyT = requester.recv(0);
	System.out.println("Received " + new String(replyT) + ".");
    */
    
    /**
     * Test case for get command
     */
    
    /* pin = 1;
    String requestT = ioa.get_pin(pin);
    requester.send(requestT.getBytes(),0);
    System.out.println("get pin "+pin);
    byte[] replyT = requester.recv(0);
    System.out.println("Received " + new String(replyT) + ".");
    /**/

   /** 
    * Test Case for set command
    */
    /*/pin = 1;
    String requestT = ioa.set_pin(pin);
    requester.send(requestT.getBytes(),0);
    System.out.println("Set pin "+pin);
    byte[] replyT = requester.recv(0);
    System.out.println("Received " + new String(replyT) + ".");
    
    /*requestT = ioa.set_pin(pin, 0);
    requester.send(requestT.getBytes(),0);
    System.out.println("Set pin "+pin);
    replyT = requester.recv(0);
    System.out.println("Received " + new String(replyT) + ".");

    requestT = ioa.set_pin(pin, 0);
    requester.send(requestT.getBytes(),0);
    System.out.println("Set pin "+pin);
    replyT = requester.recv(0);
    System.out.println("Received " + new String(replyT) + ".");
    
    requestT = "unknown command";
    requester.send(requestT.getBytes(),0);
    System.out.println("Set pin "+pin);
    replyT = requester.recv(0);
    System.out.println("Received " + new String(replyT) + ".");
   /* **/
    
    requester.close();
    context.term();
}


}