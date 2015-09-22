package bu.file.ftp.server;

import static java.lang.System.out;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ftpserver.ftplet.FtpException;

import bu.file.ftp.server.util.NetworkUtils;
import bu.file.ftp.server.util.PropertiesHolder;

/**
 * @author jxs
 */
public class Commander {
	static FtpServerLauncher launcher;
	private static final String keyStoreFileName = "ftpkeystore.jks";
	private static final String keyPassword = "pass0101";

	public static void main(String[] args) throws FtpException {
		if (null == args || args.length == 0) {
			args = new String[4];
			args[0] = String.format("port=%d", PropertiesHolder.getIntValue("ftp.port"));
			args[1] = String.format("id=%s", PropertiesHolder.getValue("ftp.user"));
			args[2] = String.format("password=%s", PropertiesHolder.getValue("ftp.pwd"));
			args[3] = String.format("home=%s", PropertiesHolder.getValue("ftp.home"));
		}

		Properties configParams = parseConfigParams(args);
		SingleUserFtpConfig config = new SingleUserFtpConfig(configParams);

		FtpServerLauncherFactory factory = new FtpServerLauncherFactory(keyStoreFileName, keyPassword);
		launcher = factory.createLauncher(config);
		launcher.start();

		printUssage(config);
	}

	protected static void printUssage(SingleUserFtpConfig config) {
		String serverIp = NetworkUtils.getLocalhostIp();
		String protocol = config.isSsl() ? "ftps" : "ftp";
		String serverAddress = String.format("%s://%s:%d", protocol, serverIp, config.getPort());

		printServerStatus(config, serverAddress);
		printUssage(config, serverIp, serverAddress);
	}

	private static void printServerStatus(SingleUserFtpConfig config, String serverAddress) {
		printf("FTP sever started : %s \n", serverAddress);
		println("# Configuration parameters");
		printf("- ssl : %s \n", config.isSsl());
		printf("- port : %d", config.getPort());
		if (config.isDefaultPort()) {
			printf(" (Default port)");
		}
		println();
		printf("- passivePorts : %s\n", StringUtils.defaultString(config.getPassivePorts()));
		printf("- id : %s\n", config.getId());
		if (config.isAnonymousId()) {
			printf("- password : [none]\n");
		} else {
			printf("- password : %s\n", config.getPassword());
		}
		printf("- home : %s\n", config.getHome());
		println();
		println("You can specify these parameters by command line arguments with 'key=value' format.");
		println("	example 1> java -jar bu-ftpserver.jar port=10021 id=jxs password=1234");
		println("	example 2> java -jar bu-ftpserver.jar port=10021 passivePorts=10125-10199 ssl=true id=jxs password=1234 home=/home/jxs/programs ");
		println();
	}

	private static void printUssage(SingleUserFtpConfig config, String serverIp, String serverAddress) {
		println("# Ussage examples of clients");
		if (!config.isSsl()) {
			println("## wget");
			if (config.isAnonymousId()) {
				printf("- download : wget ftp://%s:%d/[filename]\n", serverIp, config.getPort());
			} else {
				printf("- download : wget ftp://%s:%s@%s:%d/[filename]\n", config.getId(), config.getPassword(), serverIp, config.getPort());
			}
		}
		println("## curl");

		String additionOption = config.isSsl() ? "-k" : "";
		if (config.isAnonymousId()) {
			printf("- upload : curl -T [filename] %s %s\n", serverAddress, additionOption);
			printf("- download : curl %s/[filename] %s\n", serverAddress, additionOption);
		} else {
			printf("- upload : curl -T [filename] %s -u %s:%s %s\n", serverAddress, config.getId(), config.getPassword(), additionOption);
			printf("- download : curl %s/[filename] -u %s:%s %s\n", serverAddress, config.getId(), config.getPassword(), additionOption);
		}
	}

	public static void requestStop() {
		launcher.stop();
	}

	static Properties parseConfigParams(String[] args) {
		Properties props = new Properties();
		for (String pair : args) {
			String[] keyAndValue = StringUtils.split(pair, '=');
			props.put(keyAndValue[0], keyAndValue[1]);
		}
		return props;
	}

	private static void printf(String format, Object... args) {
		out.printf(format, args);
	}

	private static void println(String str) {
		out.println(str);
	}

	private static void println() {
		out.println();
	}
}
