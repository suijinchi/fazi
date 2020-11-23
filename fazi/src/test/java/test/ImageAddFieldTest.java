package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

public class ImageAddFieldTest {
	
	public static void main(String[] args) throws IOException {
		
		String secret = "123";
		String keyword = "情感洁癖";
		String keywordDesc = "生活让你苦一阵子，等你适应以后让你苦上一辈子";
//		String keywordDesc = "生活让你苦一阵子";
		String skinCipher = "小主人需加强保养，早晚多使用精华、原液类产品，建议小主开始使用科技仪器类保养哟";
		String characterCode = "促动者";
		String characterCodeDesc = "被接受、肯定、爱交际、有竞争性、目标感、主动";
		
		File file = new File("E://thumb//dt.png");
		
		BufferedImage bi = ImageIO.read(file);
		
		Graphics2D g2 = (Graphics2D)bi.getGraphics();     
        g2.setBackground(Color.WHITE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //secret
        g2.setColor(new Color(65,65,65));
        g2.setFont(new Font("黑体", Font.PLAIN, 54));
        g2.drawString(secret, 500, 160);  
        
        //keyword
        g2.setColor(new Color(255,255,255));
        g2.setFont(new Font("黑体", Font.PLAIN, 30));
        g2.drawString(keyword, 308, 295);  
        
        //keywordDesc
        String[] keywordDescArr = fieldLineWrap(keywordDesc,14);
        Font keywordDescFont = new Font("黑体", Font.PLAIN, 32);
        FontMetrics fm = g2.getFontMetrics(keywordDescFont);
        g2.setFont(keywordDescFont);
        g2.setColor(new Color(65,65,65));
        if(keywordDesc!=null&&keywordDescArr.length==2){
          	
          	int textWidth0 = fm.stringWidth(keywordDescArr[0]);
          	int width0 = (bi.getWidth()-textWidth0)/2;
          	g2.drawString(keywordDescArr[0], width0, 338);
            
          	int textWidth1 = fm.stringWidth(keywordDescArr[1]);
          	int width1 = (bi.getWidth()-textWidth1)/2;
            g2.drawString(keywordDescArr[1], width1, 378);
            
        }else if(keywordDesc!=null&&keywordDescArr.length==1){
            
          	int textWidth = fm.stringWidth(keywordDescArr[0]);
          	int width = (bi.getWidth()-textWidth)/2;
            g2.drawString(keywordDescArr[0], width, 358);
        }
        
        //skinCipher
        String[] skinCipherArr = fieldLineWrap(skinCipher,18);
        Font skinCipherFont = new Font("黑体", Font.PLAIN, 22);
        FontMetrics skinCipherFm = g2.getFontMetrics(skinCipherFont);
        g2.setFont(skinCipherFont);
        g2.setColor(new Color(65,65,65));
        if(skinCipher!=null&&skinCipherArr.length==2){
          	
          	int textWidth0 = skinCipherFm.stringWidth(skinCipherArr[0]);
          	int width0 = (bi.getWidth()-textWidth0)/2;
          	g2.drawString(skinCipherArr[0], width0, 523);
            
          	int textWidth1 = skinCipherFm.stringWidth(skinCipherArr[1]);
          	int width1 = (bi.getWidth()-textWidth1)/2;
            g2.drawString(skinCipherArr[1], width1, 563);
            
        }else if(skinCipher!=null&&skinCipherArr.length==1){
            
          	int textWidth = skinCipherFm.stringWidth(skinCipherArr[0]);
          	int width = (bi.getWidth()-textWidth)/2;
            g2.drawString(skinCipherArr[0], width, 543);
        }
        
        
        //characterCode
        g2.setColor(new Color(255,255,255));
        g2.setColor(Color.RED);
        g2.setFont(new Font("黑体", Font.PLAIN, 30));
        g2.drawString(characterCode, 308, 695);  

        try {
            ImageIO.write(bi, "png", new File("E://thumb//dt2.png"));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		
	}
	
	private static String[] fieldLineWrap(String field,int length){
		if(StringUtils.isBlank(field)){
			return null;
		}
		if(field.length()>length){
			String s1 = field.substring(0, length);
			String s2 = field.substring(length, field.length());
			String[] fieldArr = new String[]{s1,s2};
			return fieldArr;
		}
		return new String[]{field};
	}
	
	
}
