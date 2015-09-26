package bu.file.ftp.server;

import java.util.Properties;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;

import bu.file.ftp.server.util.FtpServerPropertiesHolder;
import bu.file.ftp.server.util.PropertierUtil;

public class BuFtpServer {

	private static FtpServerLauncher launcher;
	private static final String keyStoreFileName = "ftpkeystore.jks";
	private static final String keyPassword = "pass0101";

	public FtpServer init(int serverPort,// PORT
			String rootPath,// 跟路径
			String username,// 用户名
			String password// 访问密码
	) throws FtpException {
		String[] args = new String[4];
		args[0] = String.format("port=%d", serverPort);
		args[1] = String.format("id=%s", username);
		args[2] = String.format("password=%s", password);
		args[3] = String.format("home=%s", rootPath);
		return init(args);
	}

	public FtpServer init() throws FtpException {
		String[] args = new String[4];
		args[0] = String.format("port=%d", FtpServerPropertiesHolder.getIntValue("ftp.port"));
		args[1] = String.format("id=%s", FtpServerPropertiesHolder.getValue("ftp.user"));
		args[2] = String.format("password=%s", FtpServerPropertiesHolder.getValue("ftp.pwd"));
		args[3] = String.format("home=%s", FtpServerPropertiesHolder.getValue("ftp.home"));
		return init(args);
	}

	public FtpServer init(String[] args) throws FtpException {
		Properties configParams = PropertierUtil.parseConfigParams(args);
		SingleUserFtpConfig config = new SingleUserFtpConfig(configParams);
		FtpServerLauncherFactory factory = new FtpServerLauncherFactory(keyStoreFileName, keyPassword);
		launcher = factory.createLauncher(config);
		launcher.start();

		PrintUssageHolder.printUssage(config);
		return launcher.getFtpServer();
	}

	public void stop() {
		if (null != launcher) {
			launcher.stop();
		}
	}

	public boolean isStop() {
		if (null != launcher) {
			return launcher.getFtpServer().isStopped();
		}
		return true;
	}
}
