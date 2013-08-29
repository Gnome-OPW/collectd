package org.collectd.java;

import java.io.*;
import java.lang.management.*;
import org.collectd.api.Collectd;
import org.collectd.api.ValueList;
import org.collectd.api.CollectdInitInterface;
import org.collectd.api.CollectdReadInterface;
import org.collectd.api.CollectdShutdownInterface;


public class LoadExample implements CollectdInitInterface,
       CollectdReadInterface,
       CollectdShutdownInterface
{
    private   OperatingSystemMXBean mbean =null;
    private int nb_values=1;
    private String output="0 0 0";
    double avgload;
    private static String LOAD_AVERAGE_TEXT = "load average:";
    

  public LoadExample ()
  {
    Collectd.registerInit     ("LoadExample", this);
    Collectd.registerRead     ("LoadExample", this);
    Collectd.registerShutdown ("LoadExample", this);
  }

  private void submit () 
  {
	
    ValueList vl= new ValueList ();      
    vl.setHost ("proxima");
    vl.setPlugin ("LoadExample");
    vl.setPluginInstance ("loads");
    
    if(nb_values==1){
    vl.setType ("gauge");
    vl.setTypeInstance ("jvm-format");
    vl.addValue (avgload);
    }
    
    if(nb_values==3){
    	String[] values=output.split(",");
    	vl.setType("load");
    	vl.setTypeInstance("linux format");
    	vl.addValue(Double.parseDouble(values[0]));
    	vl.addValue(Double.parseDouble(values[1]));
    	vl.addValue(Double.parseDouble(values[2]));
    }
try{
    Collectd.logError ("LoadExample ValueList contents before dispatch are = "+vl.toString() );
      Collectd.dispatchValues (vl);
      vl.clearValues ();
}
catch(Exception e){
Collectd.logError("LoadExample unexpected error:"+e);
for (StackTraceElement element : e.getStackTrace()) { Collectd.logError(element.toString());}
}
    
}
  public int init ()
  {
    try
    {
      mbean = ManagementFactory.getOperatingSystemMXBean();
    }
    catch (Exception e)
    {
      Collectd.logError ("Example2 : Creating MBean failed: " + e);
      return (-1);
    }

    return (0);
  } 

  public int read () 
  {
    if (mbean == null)
    {
      Collectd.logError ("Example2: mbean == null");
      return (-1);
    }

    try {
    	
    	// Obtain load average from OS command
        ProcessBuilder pb = new ProcessBuilder("/usr/bin/uptime");
        Process p = pb.start();
        output = commandOutput(p);

        // obtain load average from OperatingSystemMXBean
        avgload = mbean.getSystemLoadAverage();
        output = output.substring(output.lastIndexOf(LOAD_AVERAGE_TEXT) +
                                  LOAD_AVERAGE_TEXT.length());
        Collectd.logError("Load average returned from uptime = " + output);
        Collectd.logError("getSystemLoadAverage() returned " + avgload);
    }
    catch(Exception e){
    	Collectd.logError ("LoadExample : checking load Average failed: " + e);
        return (-1);
    }
    //to submit only one value as a counter nb_values=1 else put 3
    nb_values=1; 
    //nb_values=3;
    submit ();
    return (0);
  } 

  public int shutdown () 
  {
    System.out.print ("org.collectd.java.LoadExample.Shutdown ();\n");
    mbean = null;
    return (0);
  } 
  
  private  String commandOutput(Reader r) throws Exception {
      StringBuilder sb = new StringBuilder();
      int c;
      while ((c = r.read()) > 0) {
          if (c != '\r') {
              sb.append((char) c);
          }
      }
      return sb.toString();
  }
  private String commandOutput(Process p) throws Exception {
      Reader r = new InputStreamReader(p.getInputStream(),"UTF-8");
      String output = commandOutput(r);
      p.waitFor();
      p.exitValue();
      return output;
  }
  
} 

