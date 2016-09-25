package sar.bean;

import lombok.Data;

/** 日報レコード */
@Data
public class Nippou {
	private String kinmuDate;
	private String shukkinTimeHour;
	private String shukkinTimeMin;
	private String taikinTimeHour;
	private String taikinTimeMin;
	private String kinmuShubetsuView;
}
