/*
 * @(#)CommaderPrintTest.java $version 2012. 5. 16.
 *
 * Copyright 2007 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package bu.file.ftp.server;

import java.util.Properties;

import org.junit.Test;

import bu.file.ftp.server.Commander;
import bu.file.ftp.server.SingleUserFtpConfig;

/**
 * @author jxs
 */
public class CommaderPrintTest {

	@Test
	public void testPrintDefault() {
		
		Properties props = new Properties();
		SingleUserFtpConfig config = new SingleUserFtpConfig(props);
		
		Commander.printUssage(config);
	}

	@Test
	public void testPrintSslTrue() {
		Properties props = new Properties();
		props.put("ssl", "true");
		SingleUserFtpConfig config = new SingleUserFtpConfig(props);
		
		Commander.printUssage(config);
	}

	@Test
	public void testPrintIdAndPassword() {
		Properties props = new Properties();
		props.put("port", "2003");
		props.put("id", "jxs");
		props.put("password", "1234");
		
		SingleUserFtpConfig config = new SingleUserFtpConfig(props);
		
		Commander.printUssage(config);
	}

}
