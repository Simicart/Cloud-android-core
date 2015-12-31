package com.simicart.core.cms.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Constants;

public class Cms extends SimiEntity {
	String _id;
	String title;
	String key;
	String icon;
	String content;
	String status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}
