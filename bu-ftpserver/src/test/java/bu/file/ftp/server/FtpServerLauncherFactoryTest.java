package bu.file.ftp.server;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import bu.file.ftp.server.FtpServerLauncherFactory;


/**
 * @author jxs
 */
public class FtpServerLauncherFactoryTest {
	FtpServerLauncherFactory factory = new FtpServerLauncherFactory("", "");
	
	@Test
	public void testReadFromClassPath() throws IOException {
		File keyStore = factory.getKeyStoreFile("ftpkeystore.jks");
		assertTrue(keyStore.exists());
		assertTrue(keyStore.isFile());
	}
}
