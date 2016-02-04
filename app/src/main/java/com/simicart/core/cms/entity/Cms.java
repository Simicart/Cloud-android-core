package com.simicart.core.cms.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class Cms extends SimiEntity {
	String _id;
	String title;
	String key;
	String icon;
	String content;
	String iconID;
	String iconURL;
	boolean enable;
	int seq_no;
	String updated_at;
	String created_at;

	@Override
	public void parse() {
		if(mJSON != null){
			if(mJSON.has("_id")){
				_id = getData("_id");
			}

			if(mJSON.has("title")){
				title = getData("title");
			}

			if(mJSON.has("key")){
				key = getData("key");
			}

			if(mJSON.has("icon")){
				icon = getData("icon");
				if(Utils.validateString(icon)){
					try {
						JSONObject icon = mJSON.getJSONObject("icon");
						iconID = icon.getString("_id");
						iconURL = icon.getString("url");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			if(mJSON.has("content")){
				content = getData("content");
			}

			if(mJSON.has("seq_no")){
				seq_no = Integer.parseInt(getData("seq_no"));
			}

			if(mJSON.has("updated_at")){
				updated_at = getData("updated_at");
			}

			if(mJSON.has("created_at")){
				created_at = getData("created_at");
			}

			if(mJSON.has("status")){
				String statusValue = getData("status");
				if(Utils.validateString(statusValue) && statusValue.equals("1")){
					enable = true;
				}
			}
		}
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(int seq_no) {
		this.seq_no = seq_no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getIconID() {
		return iconID;
	}

	public void setIconID(String iconID) {
		this.iconID = iconID;
	}

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}
}
