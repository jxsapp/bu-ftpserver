/*
 * @(#)NetworkUtilsTest.java $version 2012. 5. 15.
 *
 * Copyright 2007 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package bu.file.ftp.server.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import bu.file.ftp.server.util.NetworkUtils;


/**
 * @author jxs
 */
public class NetworkUtilsTest {
	
	@Test
	public void testGetLocalhostIp(){
		String ip = NetworkUtils.getLocalhostIp();
		int numOfDot = StringUtils.countMatches(ip, ".");
		assertThat(numOfDot,is(3));
	}
}
