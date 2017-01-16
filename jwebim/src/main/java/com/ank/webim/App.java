package com.ank.webim;

import org.apache.log4j.Logger;

public class App {
	private static final Logger LOG = Logger.getLogger(App.class);
	
	private static final String BOOT_PROXY 	= "proxy";
	private static final String BOOT_SERVER = "server";
	private static final String BOOT_ALL 	= "all";
	
	public static void main(String[] args) {
		try {
			String boot = null;
			
			if(args.length > 0){
				boot = args[0];
			}
			
			Bootstrap bootstrap = null;
			if(BOOT_PROXY.equals(boot)){
				LOG.info("jwebim bootstrap " + BOOT_PROXY);
				bootstrap = new ProxyBootstrap();
				bootstrap.start();
			}else if(BOOT_SERVER.equals(boot)){
				LOG.info("jwebim bootstrap " + BOOT_SERVER);
				bootstrap = new ServerBootstrap();
				bootstrap.start();
			}else{
				LOG.info("jwebim bootstrap " + BOOT_ALL);
				bootstrap = new ProxyBootstrap();
				bootstrap.start();
				bootstrap = new ServerBootstrap();
				bootstrap.start();
			}
		} catch (Exception e) {
			LOG.error("jwebim bootstrap fail.", e);
		}
	}
	
}
