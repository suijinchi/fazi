package com.zhengbangnet.modules.entity;

import com.zhengbangnet.common.entity.BaseEntity;
import com.zhengbangnet.common.utils.ImageUtil;
import com.zhengbangnet.common.utils.QRCodeUtil;
import com.zhengbangnet.common.utils.SettingUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 会员实体
 */
public class Member extends BaseEntity {

    public static final String TREEPATH_SPRATOR = ",";

    /**
     * 当前登录cookiename
     */
    public static final String CURRENT_LOGIN_MEMBER = "currentLoginMember";

    /**
     * 当前邀请cookiename
     */
    public static final String CURRENT_INVITE_MEMBER = "currentInviteMember";

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别 0女 1男 2未知
     **/
    private String gender;

    /**
     * 余额
     */
    private java.math.BigDecimal balance;

    /**
     * 历史总余额
     */
    private java.math.BigDecimal historyBalance;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 历史总积分
     */
    private Integer historyPoint;

    /**
     * 状态 0 正常 1冻结
     */
    private String status;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * openId
     */
    private String openid;

    /**
     * 二维码地址
     */
    private String qrCodeUrl;

    /**
     * 会员类型 0普通用户 1会员
     */
    private Integer type;

    /**
     * 用户编号
     */
    private String memberNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 生日
     */
    private String birthday;

    private Integer birthdayYear;

    private Integer birthdayMonth;

    private Integer birthdayDay;

    /**
     * 会员等级id
     */
    private Long memberRankId;

    private Long recommendId;

    private Long recommendId2;

    private Long recommendId3;

    private String treePath;

    /**
     * 连续签到次数
     */
    private Integer signSerialTimes;

    public Integer getSignSerialTimes() {
        return signSerialTimes;
    }

    public void setSignSerialTimes(Integer signSerialTimes) {
        this.signSerialTimes = signSerialTimes;
    }

    public Integer getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(Integer birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public Integer getBirthdayMonth() {
        return birthdayMonth;
    }

    public void setBirthdayMonth(Integer birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    public Integer getBirthdayDay() {
        return birthdayDay;
    }

    public void setBirthdayDay(Integer birthdayDay) {
        this.birthdayDay = birthdayDay;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Long getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    public Long getRecommendId2() {
        return recommendId2;
    }

    public void setRecommendId2(Long recommendId2) {
        this.recommendId2 = recommendId2;
    }

    public Long getRecommendId3() {
        return recommendId3;
    }

    public void setRecommendId3(Long recommendId3) {
        this.recommendId3 = recommendId3;
    }

    public Long getMemberRankId() {
        return memberRankId;
    }

    public void setMemberRankId(Long memberRankId) {
        this.memberRankId = memberRankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public BigDecimal getHistoryBalance() {
        return historyBalance;
    }

    public void setHistoryBalance(BigDecimal historyBalance) {
        this.historyBalance = historyBalance;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
    }

    public java.math.BigDecimal getBalance() {
        return this.balance;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getPoint() {
        return this.point;
    }

    public void setHistoryPoint(Integer historyPoint) {
        this.historyPoint = historyPoint;
    }

    public Integer getHistoryPoint() {
        return this.historyPoint;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }

    /**
     * 获取头像相对目录图片名
     * /resources/upload/head/1/999.jpg
     *
     * @return
     */
    public String getHeadRelativeFile() {
        String headImageName = getId() + ".jpg";
        String headRelativePath = "/resources/upload/head/" + getId() / 10000;
        String headAbsolutePath = headRelativePath + "/" + headImageName;
        return headAbsolutePath;
    }

    /**
     * 获取头像相对目录
     * /resources/upload/head/1
     *
     * @return
     */
    public String getHeadRelativePath() {
        String headRelativePath = "/resources/upload/head/" + getId() / 10000;
        return headRelativePath;
    }

    /**
     * 处理下载头像
     */
    public void processAvatar(String headUrl, HttpSession session) {
        if (!new File(session.getServletContext().getRealPath(getHeadRelativeFile())).exists()) {
            String headImageName = getId() + ".jpg";
            String headAbsolutePath = SettingUtils.get().getDomain() + getHeadRelativePath() + "/" + headImageName;
            if (!new File(session.getServletContext().getRealPath(getHeadRelativePath())).exists()) {
                new File(session.getServletContext().getRealPath(getHeadRelativePath())).mkdirs();
            }
            try {
                ImageUtil.download(headUrl, headImageName, session.getServletContext().getRealPath(getHeadRelativePath()));
                setAvatarUrl(headAbsolutePath);
            } catch (Exception e) {

            }
        }
    }

    /**
     * 获取二维码相对目录图片名
     * /resources/upload/qrcode/1/999.jpg
     *
     * @return
     */
    public String getQrcodeRelativeFile() {
        String headImageName = getId() + ".jpg";
        String headRelativePath = "/resources/upload/qrcode/" + getId() / 10000;
        String headAbsolutePath = headRelativePath + "/" + headImageName;
        return headAbsolutePath;
    }

    /**
     * 获取二维码相对目录
     * /resources/upload/qrcode/1
     *
     * @return
     */
    public String getQrcodeRelativePath() {
        String headRelativePath = "/resources/upload/qrcode/" + getId() / 10000;
        return headRelativePath;
    }

    /**
     * 处理生成二维码
     */
    public void processQrcode(String url, HttpSession session) {
        if (!new File(session.getServletContext().getRealPath(getQrcodeRelativeFile())).exists()) {
//			String imageName = getId()+".jpg";
//			String qrcodeAbsolutePath = SettingUtils.get().getDomain()+getQrcodeRelativePath()+"/"+imageName;
            if (!new File(session.getServletContext().getRealPath(getQrcodeRelativePath())).exists()) {
                new File(session.getServletContext().getRealPath(getQrcodeRelativePath())).mkdirs();
            }
            try {
                QRCodeUtil.encode(url, session.getServletContext().getRealPath(getQrcodeRelativeFile()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成推广二维码
     *
     * @param baseFile 海报地图
     * @param session
     */
    public String processQrcode(String shopMemberId, String shopName, String logo, File baseFile, HttpSession session) throws IOException {
        String newLogo = logo.replace("http://lingdumeichang.qd-weimob.com/lingdu/", "");
        File headFile = new File(session.getServletContext().getRealPath(newLogo));
        File qrcodeFile = new File(session.getServletContext().getRealPath(getQrcodeRelativeFile()));

        Builder<File> qrcode = Thumbnails.of(qrcodeFile).size(350, 350);
        Builder<File> head = Thumbnails.of(headFile).size(110, 110);

        int cornerRadius = 720;
        BufferedImage headBi = head.asBufferedImage();
        BufferedImage image = new BufferedImage(headBi.getWidth(), headBi.getHeight(), BufferedImage.TYPE_INT_ARGB);
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
        g2.fillRoundRect(0, 0, headBi.getWidth(), headBi.getHeight(), cornerRadius, cornerRadius);
        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(headBi, 0, 0, headBi.getWidth(), headBi.getHeight(), null);
        g2.dispose();

        BufferedImage bi = ImageIO.read(baseFile);
        Graphics2D baseG2 = (Graphics2D) bi.getGraphics();

        baseG2.setColor(Color.BLACK);
        baseG2.setFont(new Font("黑体", Font.PLAIN, 22)); //字体、字型、字号

        baseG2.drawString(shopMemberId, 376, 259);        //ID
        baseG2.drawString(shopName, 332, 295);        //店铺名
        baseG2.drawImage(qrcode.asBufferedImage(), null, 198, 362);     //二维码图片
        baseG2.drawImage(image, null, 210, 235);      //头像

        String posterRelativePath = "/resources/upload/shopQrcode/" + getId() / 10000;
        if (!new File(session.getServletContext().getRealPath(posterRelativePath)).exists()) {
            new File(session.getServletContext().getRealPath(posterRelativePath)).mkdirs();
        }

        File posterFile = new File(session.getServletContext().getRealPath(posterRelativePath + "/" + getId() + ".jpg"));
        ImageIO.write(bi, "png", posterFile);

//		Thumbnails.of(posterFile).scale(1).outputQuality(0.9).toFile(posterFile);

        return SettingUtils.get().getDomain() + posterRelativePath + "/" + getId() + ".jpg";
    }


}
