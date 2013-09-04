package org.collectd.java;

import java.util.List;

import org.collectd.api.Collectd;
import org.collectd.api.OConfigItem;
import org.collectd.api.OConfigValue;


public class GetConf {

	/* code to parse this sample config
	 * <Plugin "Skeleton">
	 * Interval 20
	 * Host localhost
	 * CheckNeighbor false
	 * <logging>
	 * File /opt/collectd/var/log/skeleton.log
	 * Level error
	 * </logging> 
	 * </Plugin>
	 */
	/*
	 * Extra code is needed to pass those variables to plugin class
	 */
	private int interval=0;
	String host=null,file=null,Level=null;
	boolean checkn=false;
	
	
	
	public int parse(OConfigItem ci){
		
		Collectd.logError ("displaying all configuration: config: ci = " + ci + ";"); 
		List<OConfigItem> children;
		children = ci.getChildren ();
		    for (int i = 0; i < children.size (); i++)
		    {
		      OConfigItem child=children.get (i);
		      String key = child.getKey ();
		      if (key.equalsIgnoreCase ("Interval"))
		      {
		        try
		        {
		        	if(child.getValues().size()==1)
		        	interval=getConfigNumber(child);
		        }
		        	
		        }
		        catch (IllegalArgumentException e)
		        {
		          Collectd.logError ("Skeleton plugin: Evaluating Interval param block failed: " + e);
		        }
		      }
		      else if (key.equalsIgnoreCase ("Host"))
		      {
		    	  try
			        {
			        	if(child.getValues().size()==1)
			        	interval=getConfigString(child);
			        	
			        }
			        catch (IllegalArgumentException e)
			        {
			          Collectd.logError ("Skeleton plugin: Evaluating Host param block failed: " + e);
			        }
		      }
		      else if (key.equalsIgnoreCase ("CheckNeighbor"))
		      {
		    	  try
			        {
			        	if(child.getValues().size()==1)
			        	interval=getConfigBoolean(child);
			        	
			        }
			        catch (IllegalArgumentException e)
			        {
			          Collectd.logError ("Skeleton plugin: Evaluating CheckNeighbor param block failed: " + e);
			        }
		      }
		      else if (key.equalsIgnoreCase ("logging"))
		      {
		    	List<OConfigItem> children2=child.getChildren ();
		    	for(int j=1;j<children2.size();j++){
		    		if(children2.get(0).getKey().equalsIgnoreCase("File"))
		    			file=getConfigString(children2.get(i));
		    		else if (children2.get(0).getKey().equalsIgnoreCase("Level"))
		    			Level=getConfigString(children2.get(i));
		    		else
		    			Collectd.logError ("Skeleton plugin: Unknown config option: " +children2.get(0).getKey());
		    			
		    	}
		      }
		      
		      else
		      {
		        Collectd.logError ("Skeleton plugin: Unknown config option: " + key);
		      }

		    }

		
		
	}
	
	private String getConfigString (OConfigItem ci) 
	  {
	    List<OConfigValue> values;
	    OConfigValue v;

	    values = ci.getValues ();
	    if (values.size () != 1)
	    {
	      Collectd.logError ("The " + ci.getKey () + " configuration option needs exactly one string argument.");
	      return null;
	    }

	    v = values.get (0);
	    if (v.getType () != OConfigValue.OCONFIG_TYPE_STRING)
	    {
	      Collectd.logError ("The " + ci.getKey ()+" configuration option needs exactly one string argument.");
	      return null;
	    }
	     
	    return (v.getString ());
	  }
	 
	 private int getConfigNumber (OConfigItem ci) 
	  {
	    List<OConfigValue> values;
	    OConfigValue v;

	    values = ci.getValues ();
	    if (values.size () != 1)
	    {
	      Collectd.logError ("The " + ci.getKey () + " configuration option needs exactly one number argument.");
	      return -1;
	    }

	    v = values.get (0);
	    if (v.getType () != OConfigValue.OCONFIG_TYPE_NUMBER)
	    {
	      Collectd.logError ("The " + ci.getKey () + " configuration option needs exactly one number argument.");
	      return -1;
	    } 
	    return (v.getNumber().intValue());
	  } 

	 
	 private boolean getConfigBoolean (OConfigItem ci) 
	  {
	    List<OConfigValue> values;
	    OConfigValue v;

	    values = ci.getValues ();
	    if (values.size () != 1)
	    {
	      Collectd.logError ("The " + ci.getKey () + " configuration option needs exactly one number argument.");
	      return -1;
	    }

	    v = values.get (0);
	    if (v.getType () != OConfigValue.OCONFIG_TYPE_BOOLEAN)
	    {
	      Collectd.logError ("The " + ci.getKey ()+ " configuration option needs exactly one boolean argument.");
	      return -1;
	    }
	    
	    return (v.getBoolean());
	  } 
	
}
