package org.collectd.java;

import java.io.*;
import java.net.*;
import org.collectd.*;
import org.collectd.api.*;
import java.util.List;

public class ExampleHttp implements CollectdInitInterface,CollectdConfigInterface,
		CollectdReadInterface {

	private String host;
	private int buffersize;

	public ExampleHttp(){
		Collectd.registerInit     ("ExampleHttp", this);
	    Collectd.registerRead     ("ExampleHttp", this);
	    Collectd.registerConfig	  ("ExampleHttp", this);
		
	}
	 

	public int config (OConfigItem ci){
		
	//	Collectd.logError("entering config callback");
		 List<OConfigItem> children;
		    int i;
		//    Collectd.logError ("ExampleHttp plugin: config: ci = " + ci + ";");
		    children = ci.getChildren ();
		    for (i = 0; i < children.size (); i++)
		    {
		      OConfigItem child;
		      String key;

		      child = children.get (i);
		      key = child.getKey ();
		      
		      if (key.equalsIgnoreCase ("Connection"))
		      {
		        try
		        {
		        	if(child.getChildren().get(0).getKey().equalsIgnoreCase("Host")){
		        		host=child.getChildren().get(0).getValues().get(0).getString();
		        	if (host==null)
		        		return -1;
		        	}
		        	
		        }
		        catch (IllegalArgumentException e)
		        {
		          Collectd.logError ("ExampleHttp plugin: "
		              + "Evaluating Connection block failed: " + e);
		        }
		      }
		      else if (key.equalsIgnoreCase ("Performance"))
		      {
		        try
		        {
		        	if(child.getChildren().get(0).getKey().equalsIgnoreCase("BufferSize")){
		          buffersize=child.getChildren().get(0).getValues().get(0).getNumber().intValue();
		          if (buffersize<=0)
		        	  return -1;
		        	}
		        	
		        }
		        catch (IllegalArgumentException e)
		        {
		          Collectd.logError ("ExampleHttp plugin: "
		              + "Evaluating Performance block failed: " + e);
		        }
		      }
		      else
		      {
		        Collectd.logError ("ExampleHttp plugin: Unknown config option: " + key);
		      }

		    }
		    return 0;
	}
	public int read() {
	
		Collectd.logError("entering read callback");
        long startBuffer = System.currentTimeMillis();
        test(true, buffersize);
        long TimeBuffer = System.currentTimeMillis() - startBuffer;                   
        long startNoBuffer = System.currentTimeMillis();
        test(false, buffersize);
        long TimeNoBuffer = System.currentTimeMillis() - startNoBuffer;
          
        ValueList vl= new ValueList ();      
        vl.setHost (host); 
        vl.setPlugin ("ExampleHttp");
        vl.setPluginInstance ("ResponseTime");
        vl.setType ("gauge");
        
        vl.setTypeInstance ("BufferedStream");
        vl.addValue (TimeBuffer/1000);
        Collectd.logError ("ExampleHttp 1 ValueList contents before dispatch are = "+vl.toString() );
        Collectd.dispatchValues (vl);
        vl.clearValues();
        
        vl.setTypeInstance ("NonBufferedStream");
        vl.addValue (TimeNoBuffer/1000);
        Collectd.logError ("ExampleHttp 2 ValueList contents before dispatch are = "+vl.toString() );
        Collectd.dispatchValues (vl);
        vl.clearValues();
  	            
	
		return 0;
	}

	public int init() {
		// TODO Auto-generated method stub
		
		return 0;
	}
	
	
	static void readAll(InputStream iStream, int bufferSize) throws IOException {
        		
        try {
        byte[] buffer = new byte[bufferSize];
        int total = 0;
        int bitsLus = 0;
        while (  (bitsLus = iStream.read(buffer)) > 0) {
                total+= bitsLus;
                if(total>10000)
                        break;
        }
        }
        catch(Exception e){
        	Collectd.logError ("ExampleHttp : problem reading data from socket  " + e);
       	 for (StackTraceElement element : e.getStackTrace()) { Collectd.logError(element.toString());}
		    
        }
	}
	
	 public void test(boolean buffered, int bufferSize)  
	 {

         Socket s = null;
         OutputStream oStream = null;
         InputStream iStream = null;
         String g = "GET / HTTP/1.1\n" +"Host: "+ host+"\n\n";
         try {
         s = new Socket(host, 80);        
         oStream = s.getOutputStream();
         iStream = s.getInputStream();
         BufferedInputStream bIStream = new BufferedInputStream(iStream, 10);
         oStream.write(g.getBytes());
         if (buffered)
                 readAll(bIStream, bufferSize);
         else
                 readAll(iStream, bufferSize);

         bIStream.close();
         oStream.close();
         iStream.close();
         s.close();
         }
         catch(Exception e){
        	 Collectd.logError ("ExampleHttp : Networking exception " + e);
        	 for (StackTraceElement element : e.getStackTrace()) { Collectd.logError(element.toString());}
 		    
         }

         
	 }
	 

	 

	

}
