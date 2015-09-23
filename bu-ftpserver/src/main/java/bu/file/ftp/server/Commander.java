package bu.file.ftp.server;

import org.apache.ftpserver.ftplet.FtpException;

/**
 * @author jxs
 */
public class Commander {

	static BuFtpServer buFtpServer;

	public static void main(String[] args) throws FtpException {
		buFtpServer = new BuFtpServer();
		buFtpServer.init(args);
	}

	public static void stop() {
		buFtpServer.stop();
	}

}
