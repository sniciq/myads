package com.ku6ads.dao.entity.webdata;

import com.ku6ads.util.ExtLimit;

public class HotfilmVideo {

	private Integer id;
	private String videoName;
	private String vid;
	private Integer hotfilmId;
	private String remark;
	private ExtLimit extLimit;
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getVideoName() {
		return this.videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVid() {
		return this.vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}

	public Integer getHotfilmId() {
		return this.hotfilmId;
	}
	public void setHotfilmId(Integer hotfilmId) {
		this.hotfilmId = hotfilmId;
	}

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ExtLimit getExtLimit() {
		return extLimit;
	}
	public void setExtLimit(ExtLimit extLimit) {
		this.extLimit = extLimit;
	}
}
