package bu.file.ftp.server;

import static java.lang.System.out;

import org.apache.commons.lang3.StringUtils;

import bu.file.ftp.server.util.NetworkUtils;

public class PrintUssageHolder {

	
	
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
