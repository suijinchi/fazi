package org.weixin4j;

import java.io.Serializable;

/**
 *
 */
public class OAuth2User implements Serializable {

	private static final long serialVersionUID = -1444599306996949472L;

    private String openid;          //用户的标识，对当前公众号唯一
    private String nickname;        //用户的昵称
    private int sex;                //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String province;        //用户所在省份
    private String city;            //用户所在城市
    private String country;         //用户所在国家
    private String headimgurl;      //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
    private String[] privilege;     //用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
    private String unionid;
    private String language;
    
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
