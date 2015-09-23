package bu.file.ftp.server.util;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class PropertierUtil {

	public static Properties parseConfigParams(String[] args) {
		Properties props = new Properties();
		for (String pair : args) {
			String[] keyAndValue = StringUtils.split(pair, '=');
			props.put(keyAndValue[0], keyAndValue[1]);
		}
		return props;
	}

}
