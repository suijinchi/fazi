/*
 * 
 * 
 * 
 */
package com.zhengbangnet.modules.controller.admin;

import com.alibaba.fastjson.JSON;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.service.CaptchaService;
import com.zhengbangnet.common.utils.JsonUtils;
import com.zhengbangnet.modules.entity.Area;
import com.zhengbangnet.modules.entity.Files;
import com.zhengbangnet.modules.entity.Files.FileType;
import com.zhengbangnet.modules.service.AreaService;
import com.zhengbangnet.modules.service.FilesService;
import com.zhengbangnet.modules.service.SysAdminService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 共用
 * 
 * 
 * 
 */
@Controller("adminCommonController")
@RequestMapping("/admin/common")
public class CommonController extends AdminBaseController {

	@Resource(name="sysAdminServiceImpl")
	private SysAdminService adminService;

	@Resource(name="filesServiceImpl")
	private FilesService filesService;
	
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	
	@Resource(name="areaServiceImpl")
	private AreaService areaService;
	
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public void uploadImg(FileType fileType,Integer compress,MultipartFile file,HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException{
		
		if(compress==null){
			compress = 1;
		}
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

	/**
	 * 地区
	 */
//	@RequestMapping(value = "/area", method = RequestMethod.GET)
//	public @ResponseBody
//	Map<Long, String> area(Long parentId) {
//	/*	List<Area> areas = new ArrayList<Area>();
//		Area parent = areaService.get(parentId);
//		if (parent != null) {
//			areas = new ArrayList<Area>(parent.getChildren());
//		} else {
//			areas = areaService.findRoots();
//		}*/
//		Map<Long, String> options = new HashMap<Long, String>();
//		/*for (Area area : areas) {
//			options.put(area.getId(), area.getName());
//		}*/
//		return options;
//	}
	
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