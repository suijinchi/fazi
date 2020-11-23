/*
 * 
 * 
 * 
 */
package com.zhengbangnet.modules.controller.mobile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zhengbangnet.common.controller.MobileBaseController;
import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.utils.URLUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.service.CaptchaService;
import com.zhengbangnet.common.utils.JsonUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.entity.Area;
import com.zhengbangnet.modules.entity.Files;
import com.zhengbangnet.modules.entity.Files.FileType;
import com.zhengbangnet.modules.service.AreaService;
import com.zhengbangnet.modules.service.FilesService;
import com.zhengbangnet.modules.service.SysAdminService;
import org.weixin4j.Configuration;
import org.weixin4j.Weixin;
import org.weixin4j.WeixinException;
import org.weixin4j.jssdk.JSPermission;

/**
 * Controller - 共用
 * 
 * 
 * 
 */
@Controller("mobileCommonController")
@RequestMapping("/mobile/common")
public class CommonController extends MobileBaseController {

	@Resource(name="sysAdminServiceImpl")
	private SysAdminService adminService;

	@Resource(name="filesServiceImpl")
	private FilesService filesService;
	
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	
	@Resource(name="areaServiceImpl")
	private AreaService areaService;

	/**
	 * 客服微信二维码
	 */
	@RequestMapping(value = "/jsPermission", method = RequestMethod.POST)
	public @ResponseBody Message jsPermission(String url,HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isBlank(url)){
			return Message.error("授权失败");
		}
		//分享授权
		Setting setting = SettingUtils.get();
		JSPermission jsPermission = null;
		try {
			weixin.login(Configuration.getOAuthAppId(), Configuration.getOAuthSecret());
			jsPermission = weixin.getJSPermission(url);
		} catch (WeixinException e) {
			logger.warn("jsPermission异常", e);
			return Message.error("授权失败");
		}
		Message success = Message.success("获取成功");
		success.getData().put("jsPermission", jsPermission);
		success.getData().put("domain", setting.getDomain());
		return success;
	}

	/**
	 * 裁剪图片
	 */
	@RequestMapping(value = "/cut", method = RequestMethod.GET)
	public String cut(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "/mobile/include/cut";
	}
	
	/**
	 * Base64图片解码上传
	 */
	@RequestMapping(value = "/uploadBaseImg", method = RequestMethod.POST)
	public @ResponseBody Message uploadBaseImg(Integer compress, String imgBase64, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Files files = filesService.uploadBase(imgBase64, compress);

		Message message = Message.success("上传成功");
		message.getData().put("files", files.getMd5());
		message.getData().put("absolute", files.getAbsolute());
		message.getData().put("relative", files.getRelative());
		message.getData().put("id", files.getId());
		
		return message;
	}
	
	/**
	 * 图片上传
	 *
	 * @author suijinchi
	 * @param compress
	 * @param fileType
	 * @param file
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public void uploadImg(Integer compress,FileType fileType,MultipartFile file,HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException{

		Boolean valid = filesService.isValid(fileType,file);
		if(!valid){
			response.getWriter().write(JSON.toJSONString(Message.error("文件类型不正确")));
			return;
		}
		
		Files files = filesService.upload(fileType,file,compress);
		
		Message message = Message.success("上传成功");
		message.getData().put("files", files.getMd5());
		message.getData().put("absolute", files.getAbsolute());
		message.getData().put("relative", files.getRelative());
		message.getData().put("id", files.getId());
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/html");
		response.getWriter().write(JSON.toJSONString(message));
		
	}
	
	/**
	 * 文本编辑器上传
	 */
	@RequestMapping(value="/upload1",method=RequestMethod.POST,produces = "text/html; charset=UTF-8")
	public void FileUpload1(Integer compress,String id,FileType dir,MultipartFile imgFile,HttpServletResponse response){
		if (!this.filesService.isValid(dir, imgFile)) {
			JsonUtils.toJson(response, "text/html; charset=UTF-8", Message.warn("文件类型不正确！"));
		}else{
			Files str = this.filesService.upload(dir, imgFile,compress);
			//String str =null;
			if (str == null) {
				JsonUtils.toJson(response, "text/html; charset=UTF-8", Message.error("上传失败！请重试！"));
			} else {
				/** +"/"+file.getOriginalFilename()*/
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("error", 0);
				map.put("url", str.getAbsolute());
				JsonUtils.toJson(response, "text/html; charset=UTF-8", map);
			}
		}
	}

	@RequestMapping(value="findProvince" , method = RequestMethod.POST)
	public @ResponseBody
	Map<String,Object> findProvince(Long id){
		Area area = areaService.getByPrimaryKey(id);
		Map<String,Object> map = new HashMap<String, Object>();
		String [] areaIds = area.getTreePath().split(",");
		if(areaIds.length>=3){
			map.put("province", areaIds[1]);
			map.put("city", areaIds[2]);
		}else{
			map.put("province", areaIds[1]);
		}
		return map;
	}

	/**
	 * 错误提示
	 */
	@RequestMapping("/error")
	public String error() {
		return "/admin/common/error";
	}
	
	/**
	 * 验证码
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void image(String captchaId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(captchaId)) {
			captchaId = request.getSession().getId();
		}
		String pragma = new StringBuffer().append("yB").append("-").append("der").append("ewoP").reverse().toString();
		String value = new StringBuffer().append("ten").append(".").append("xxp").append("ohs").reverse().toString();
		response.addHeader(pragma, value);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			BufferedImage bufferedImage = captchaService.buildImage(captchaId);
			ImageIO.write(bufferedImage, "jpg", servletOutputStream);
			servletOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(servletOutputStream);
		}
	}
}