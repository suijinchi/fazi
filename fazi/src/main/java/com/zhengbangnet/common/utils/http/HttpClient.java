package com.zhengbangnet.common.utils.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.zhengbangnet.common.HttpException;
import com.zhengbangnet.common.utils.SettingUtils;


/**
 * HttpClient
 */
public class HttpClient implements java.io.Serializable {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final long serialVersionUID = 4496306190237188557L;
	private static final int OK = 200; // OK: Success!
	private static final int ConnectionTimeout = 15000;
	private static final int ReadTimeout = 15000;
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String _GET = "GET";
	private static final String _POST = "POST";

	/**
     * Post JSON数据
     *
     * @param url 提交地址
     * @param json JSON数据
     * @return 输出流对象
     * @throws WeimobException
     */
    public Response post(String url, JSONObject json) throws HttpException {
        //将JSON数据转换为String字符串
        String jsonString = json == null ? null : json.toString();
        if (SettingUtils.get().getIsDebug()) {
            logger.info("URL POST 数据：" + jsonString);
        }
        //提交数据
        return httpRequest(url, _POST, jsonString);
    }
    
    /**
     * Post 数据
     *
     * @param url 提交地址
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 输出流对象
     * @throws WeimobException
     */
    public Response post(String url, String param) throws HttpException {
        //将JSON数据转换为String字符串
        if (SettingUtils.get().getIsDebug()) {
            logger.info("URL POST 数据：" + param);
        }
        //提交数据
        return httpRequest(url, _POST, param);
    }
	
	/**
	 * Get 请求
	 *
	 * @param url
	 *            请求地址
	 * @return 输出流对象
	 * @throws HttpException
	 */
	public Response get(String url) throws HttpException {
		return httpRequest(url, _GET, null);
	}

	/**
	 * 上传文件
	 *
	 * @param url
	 *            上传地址
	 * @param file
	 *            上传文件对象
	 * @return 服务器上传响应结果
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public String upload(String url, File file)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		HttpURLConnection http = null;
		StringBuffer bufferRes = new StringBuffer();
		try {
			// 定义数据分隔线
			String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL";
			// 创建https请求连接
			http = getHttpURLConnection(url);
			// 设置header和ssl证书
			setHttpHeader(http, _POST);
			// 不缓存
			http.setUseCaches(false);
			// 保持连接
			http.setRequestProperty("connection", "Keep-Alive");
			// 设置文档类型
			http.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			// 定义输出流
			OutputStream out = null;
			// 定义输入流
			DataInputStream dataInputStream;
			try {
				out = new DataOutputStream(http.getOutputStream());
				byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
				StringBuilder sb = new StringBuilder();
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data;name=\"media\";filename=\"").append(file.getName())
						.append("\"\r\n");
				sb.append("Content-Type:application/octet-stream\r\n\r\n");
				byte[] data = sb.toString().getBytes();
				out.write(data);
				// 读取文件流
				dataInputStream = new DataInputStream(new FileInputStream(file));
				int bytes;
				byte[] bufferOut = new byte[1024];
				while ((bytes = dataInputStream.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, bytes);
				}
				out.write("\r\n".getBytes()); // 多个文件时，二个文件之间加入这个
				dataInputStream.close();
				out.write(end_data);
				out.flush();
			} finally {
				if (out != null) {
					out.close();
				}
			}

			// 定义BufferedReader输入流来读取URL的响应
			InputStream ins = null;
			try {
				ins = http.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
				String valueString;
				bufferRes = new StringBuffer();
				while ((valueString = read.readLine()) != null) {
					bufferRes.append(valueString);
				}
			} finally {
				if (ins != null) {
					ins.close();
				}
			}
		} finally {
			if (http != null) {
				// 关闭连接
				http.disconnect();
			}
		}
		return bufferRes.toString();
	}

	/**
	 * 下载附件
	 * 
	 * @param url
	 *            附件地址
	 * @return 附件对象
	 * @throws IOException
	 */
	public InputStream downloadFile(String url) throws IOException {
		URL _url = new URL(url);
		HttpURLConnection http = (HttpURLConnection) _url.openConnection();
		// 设置头
		setHttpHeader(http, "GET");
		http.connect();
		return http.getInputStream();
	}

	/**
	 * 获取http请求连接
	 *
	 * @param url
	 *            连接地址
	 * @return http连接对象
	 * @throws IOException
	 */
	private HttpURLConnection getHttpURLConnection(String url) throws IOException {
		URL urlGet = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlGet.openConnection();
		return con;
	}

	/**
	 * 通过http协议请求url
	 *
	 * @param url
	 *            提交地址
	 * @param method
	 *            提交方式
	 * @param postData
	 *            提交数据
	 * @return 响应流
	 * @throws HttpException
	 */
	private Response httpRequest(String url, String method, String postData) throws HttpException {
		//用于fiddler抓包
/*		System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "8888");
        // set https proxy
        System.setProperty("https.proxyHost", "localhost");
        System.setProperty("https.proxyPort", "8888");*/

		Response res = null;
		OutputStream output;
		HttpURLConnection http;
		try {
			// 创建https请求连接
			http = getHttpURLConnection(url);
			// 判断https是否为空，如果为空返回null响应
			if (http != null) {
				// 设置Header信息
				setHttpHeader(http, method);
				// 判断是否需要提交数据
				if (method.equals(_POST) && null != postData) {
					// 讲参数转换为字节提交
					byte[] bytes = postData.getBytes(DEFAULT_CHARSET);
					// 设置头信息
					http.setRequestProperty("Content-Length", Integer.toString(bytes.length));
					
					// 开始连接
					http.connect();
					// 获取返回信息
					output = http.getOutputStream();
					output.write(bytes);
					output.flush();
					output.close();
				/*	DataOutputStream dos=new DataOutputStream(http.getOutputStream());
				    dos.writeBytes(postData);
				    dos.flush();
				    dos.close();*/
					
				} else {
					// 开始连接
					http.connect();
				}
				// 创建输出对象
				res = new Response(http);
				// 获取响应代码
				if (res.getStatus() == OK) {
					return res;
				}
			}
		} catch (IOException ex) {
			throw new HttpException(ex.getMessage(), ex);
		}
		return res;
	}

	private void setHttpHeader(HttpURLConnection httpUrlConnection, String method) throws IOException {
		// 设置header信息
		httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// 设置User-Agent信息
		httpUrlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
		// 设置可接受信息
		httpUrlConnection.setDoOutput(true);
		// 设置可输入信息
		httpUrlConnection.setDoInput(true);
		// 设置请求方式
		httpUrlConnection.setRequestMethod(method);
		// 设置连接超时时间
		if (ConnectionTimeout > 0) {
			httpUrlConnection.setConnectTimeout(ConnectionTimeout);
		} else {
			// 默认10秒超时
			httpUrlConnection.setConnectTimeout(10000);
		}
		// 设置请求超时
		if (ReadTimeout > 0) {
			httpUrlConnection.setReadTimeout(ReadTimeout);
		} else {
			// 默认10秒超时
			httpUrlConnection.setReadTimeout(10000);
		}
		// 设置编码
		httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
	}
}
