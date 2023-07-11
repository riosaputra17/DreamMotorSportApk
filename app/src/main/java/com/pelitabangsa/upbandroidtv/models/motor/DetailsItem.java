package com.pelitabangsa.upbandroidtv.models.motor;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailsItem{

	@SerializedName("motor_poster")
	private String motorPoster;

	@SerializedName("spesifikasi")
	private List<SpesifikasiItem> spesifikasi;

	@SerializedName("motor_bg")
	private String motorBg;

	@SerializedName("id")
	private int id;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("motor_model")
	private String motorModel;

	@SerializedName("motor_detail")
	private String motorDetail;

	@SerializedName("link_youtube")
	private String linkYoutube;

	public String getMotorPoster(){
		return motorPoster;
	}

	public List<SpesifikasiItem> getSpesifikasi(){
		return spesifikasi;
	}

	public String getMotorBg(){
		return motorBg;
	}

	public int getId(){
		return id;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public String getMotorModel(){
		return motorModel;
	}

	public String getMotorDetail() { return motorDetail; }
	public String getLinkYoutube() { return linkYoutube; }
}