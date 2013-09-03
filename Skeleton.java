package org.collectd.java;

import org.collectd.*;
import org.collectd.api.*;
//other imports

public class Skeleton implements CollectdInitInterface,CollectdConfigInterface,
CollectdReadInterface,CollectdShutdownInterface {
	/*
	 * other interfaces may be implemented, see the detailed description of each here:
	 * http://collectd.org/documentation/manpages/collectd-java.5.shtml 
	 */
	
	//private variables declaration
	
	public Skeleton() {
	/* The constructor should register "callback methods" that will be called by the daemon when appropriate.
	 * 
	 */
		
	    Collectd.registerConfig	  ("Skeleton", this);
	    Collectd.registerInit     ("Skeleton", this);
	    Collectd.registerRead     ("Skeleton", this);
	    Collectd.registerShutdown ("Skeleton", this);
	}
	
	public int config (OConfigItem ci){
	//called first to parse configuration. The OConfigItem object is the root of 
	//a tree representing the configuration for this plugin. 
	//The root itself is the representation of the <Plugin /> block, 
	// so in next to all cases the children of the root are the first interesting objects.
	
	//see GetConf.java for sample code

		//if success
		//return(0);

		
	}
	
	public int init() {
	//called after config, It is supposed to set up the plugin. e. g. start threads, open connections...
	
	
	//if success
	//return(0);
	
	}
	
	public int read() {
	
	//called periodically to gather statistics. These statistics are represented as a ValueList object
	//and sent to the daemon using dispatchValues.Simple example :
	
		/*
		 * do some work to collect data, then..
		 */
		
		/*
	    ValueList vl= new ValueList ();      
        vl.setHost ("hostname"); //host from which data are collected
        vl.setPlugin ("Skeleton");  //plugin name
        vl.setPluginInstance ("Instance"); //chosen according to plugin functionnality
        vl.setType ("data-type"); the chosen data type as listed in types.db
        vl.setTypeInstance ("Type"); //to give more meaning to collected data
        vl.addValue (the collected value); //has to be a Number
        Collectd.dispatchValues (vl);
        vl.clearValues();
		*/
		
		//if success
		//return(0);
		
		
	}
	    
	public int shutdown () 
	  {
	  // called when the daemon is shutting down. used to make sure of cleaning up
	
		//if success
		//return(0);
		
	
	  } 
	
	

	
}
