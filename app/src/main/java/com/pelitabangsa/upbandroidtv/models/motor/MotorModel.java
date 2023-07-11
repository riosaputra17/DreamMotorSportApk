package com.pelitabangsa.upbandroidtv.models.motor;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MotorModel{

	@SerializedName("results")
	private List<ResultsItem> results;

	public List<ResultsItem> getResults(){
		return results;
	}
}