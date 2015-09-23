package bu.file.ftp.server;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Test;

import bu.file.ftp.server.util.PropertierUtil;

/**
 * @author jxs
 */
public class CommanderConfigParseTest {

	@Test
	public void testParse() {
		String[] commandArgs = new String[]{"port=2121", "ssl=true"};
		Properties options = PropertierUtil.parseConfigParams(commandArgs);
		
		assertThat(options.getProperty("port"), is("2121"));
		assertThat(options.getProperty("ssl"), is("true"));
	}
	
	@Test
	public void testParseEmpty() {
		String[] commandArgs = new String[]{};
		Properties options = PropertierUtil.parseConfigParams(commandArgs);
		
		assertThat(options.getProperty("port"), is(nullValue()));
	}
	
}
