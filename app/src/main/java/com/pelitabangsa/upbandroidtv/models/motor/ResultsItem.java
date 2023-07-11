package com.pelitabangsa.upbandroidtv.models.motor;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("details")
	private List<DetailsItem> details;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public List<DetailsItem> getDetails(){
		return details;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}
}