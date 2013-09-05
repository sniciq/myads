package com.ku6ads.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * FTP 上传工具类
 * @author liujunshi
 *
 */
public class FTPClientApache {
	Logger log = Logger.getLogger(this.getClass());
	private FTPClient ftp;

	public FTPClientApache(String server, String username, String password,int port) throws IOException {
		try {
			ftp = new FTPClient();
			ftp.addProtocolCommandListener(new PrintCommandListener(
					new PrintWriter(System.out)));
			ftp.connect(server, port);
			log.debug("connect to " + server);
			// After connection attempt, you should check the reply code to
			// verify
			// success.
			int reply = ftp.getReplyCode();
			log.debug("reply code is " + reply);
			log.debug("reply String is " + ftp.getReplyString());
			/*
			 * from doc http://commons.apache.org/net/api-release/org/apache/commons/net/ftp/FTPReply.html#isPositiveCompletion(int)
			 * 
			 * Determine if a reply code is a positive completion response. 
			 * All codes beginning with a 2 are positive completion responses. 
			 * The FTP server will send a positive completion response on the final successful completion of a command. 
			 */
			if (!FTPReply.isPositiveCompletion(reply)) {
				log.debug("connection refused cause reply code is" + reply);
				closeConnect();
			}
			ftp.login(username, password);

		} catch (IOException e) {
			closeConnect();
			// System.err.println("Could not connect to server.");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * ftp.logout()
	 * ftp.disconnect();
	 */
	public void closeConnect() {
		
		try {
			ftp.logout();
			log.debug("logout success");
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("logout failed cause " + e.getMessage());
		}
		
		if (ftp.isConnected()) {
			try {
				ftp.disconnect();
				log.debug("close connect success");
			} catch (IOException f) {
				log.debug("close connect failed");
				f.printStackTrace();
			}
		}
	}

	/**
	 * 上传，单层目录
	 * @param storeFile
	 * @param localfile
	 * @param remotefile
	 * @return
	 */
	public boolean upload(InputStream input, String fileName,String pathname) {
		// System.out.println("开始执行上传操作...");

		boolean binaryTransfer = false, error = false;
		try {
			ftp.setFileType(ftp.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			makeDirectory(pathname);
			ftp.cwd(pathname);
			// ftp.cwd(directory) appe(pathname);
			// ftp.listFiles(pathname);
			// ftp.cwd(pathname);
			boolean flag = ftp.storeFile(fileName.toLowerCase(), input);
			System.err.println("upload flag==" + flag);
			input.close();

		} catch (FTPConnectionClosedException e) {
			error = true;
			System.err.println("Server closed connection.");
			e.printStackTrace();
		} catch (IOException e) {
			error = true;
			e.printStackTrace();
		}
		// finally
		// {
		// closeConnect() ;
		// }

		return error;
	}
	
	public boolean makeDirectory(String path){
		boolean result = false;
		try {
			result = ftp.makeDirectory(path);
			log.debug("make directory ,result is " + result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("make directory failed");
		}
		return result;
	}



}
