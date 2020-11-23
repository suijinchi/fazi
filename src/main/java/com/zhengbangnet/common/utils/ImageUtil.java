package com.zhengbangnet.common.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		download("http://avatar.csdn.net/7/8/A/1_lincyang.jpg", "1.jpg", "C:\\Users\\Administrator\\Desktop\\fazi");
//		download("http://wx.qlogo.cn/mmopen/RyibXjDjTUibJtnyVWykO4d9UVY7E0zS3iakKIXTkRGVE5piaPOiccamuls25J1tCpn9LLQLeeOWroibTFYmse3k068pY4c5xuL1zG/0", "1.jpg", "C:\\Users\\Administrator\\Desktop\\fazi");
		
		/*
		int i=10;
		while(i-->0){
			File file = new File("E:\\thumb\\3.jpg");
			ImageUtil.compress(file, file, 10);
		}
		*/
		/*
		Builder<File> qrcode = Thumbnails.of("E:\\thumb\\2\\q22.jpg").size(170, 170);
		Builder<File> head = Thumbnails.of("E:\\thumb\\2\\a22.jpg").size(110, 110);
		
		int cornerRadius = 720;
		BufferedImage headBi = head.asBufferedImage();
		BufferedImage image = new BufferedImage(headBi.getWidth(), headBi.getHeight(),BufferedImage.TYPE_INT_ARGB);  
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, headBi.getWidth(), headBi.getHeight());  
           
        Graphics2D g2 = image.createGraphics();  
        image = g2.getDeviceConfiguration().createCompatibleImage(headBi.getWidth(), headBi.getHeight(), Transparency.TRANSLUCENT);  
        g2 = image.createGraphics();  
        g2.setComposite(AlphaComposite.Clear);  
        g2.fill(new Rectangle(image.getWidth(), image.getHeight()));  
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));  
        g2.setClip(shape);  
        // 使用 setRenderingHint 设置抗锯齿  
        g2 = image.createGraphics();  
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
        g2.fillRoundRect(0, 0,headBi.getWidth(), headBi.getHeight(), cornerRadius, cornerRadius);  
        g2.setComposite(AlphaComposite.SrcIn);  
        g2.drawImage(headBi, 0, 0, headBi.getWidth(), headBi.getHeight(), null);  
        g2.dispose();
		
		
		File baseFile = new File("E:\\thumb\\2\\113.jpg");
		BufferedImage bi = ImageIO.read(baseFile);
		Graphics2D baseG2 = (Graphics2D)bi.getGraphics();
		baseG2.drawImage(qrcode.asBufferedImage(), null, 290, 760);
		baseG2.drawImage(image, null, 180, 90);
		
		ImageIO.write(bi, "png", new File("E:\\thumb\\2\\a.jpg"));  
		*/
		compress(new File("C:\\Users\\Admin\\Pictures\\图片\\1.png"),new File("C:\\Users\\Admin\\Pictures\\图片\\2.png"),500*1024);
	}
	
	/**
	 * 压缩图片，
	 * 
	 * @param source
	 *            原路径
	 * @param target
	 *            目标
	 * @param size
	 *            小于多大不进行压缩 5k = 5*1024
	 */
	public static void compress(String source, String target, int size) {
		File sourceFile = new File(source);
		File targetFile = new File(target);
		compressImage(sourceFile,targetFile,size);
	}

	/**
	 * 压缩图片，
	 * 
	 * @param source
	 *            原路径
	 * @param target
	 *            目标
	 * @param size
	 *            小于多大不进行压缩 5k = 5*1024
	 */
	public static void compress(File source, File target, int size) {
		int i=3;//最多压缩次数
		while(source.length()>size&&i>0){
			File temp = compressImage(source,target,size);
			source = temp;
			i--;
		}
		
	}
	
	private static File compressImage(File source, File target, int size) {
		if (source.length() > size) {
			try {
				Thumbnails.of(source).scale(1f).outputQuality(0.5f).toFile(target);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				FileUtils.moveFile(source, target);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return target;
	}

	/**
	 * 下载网络图片
	 * 
	 * @param urlString
	 * @param filename
	 * @param savePath
	 * @throws Exception
	 */
	public static void download(String urlString, String filename, String savePath) throws Exception {

		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(5 * 1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}

}
