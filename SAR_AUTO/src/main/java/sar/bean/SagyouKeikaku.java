package sar.bean;

import lombok.Data;

/** 作業計画レコード */
@Data
public class SagyouKeikaku {
	private String sagyouDate;
	private String bumonName;
	private String projectName;
	private String projectKoutei;
	private String sagyouTimeHour;
	private String sagyouTimeMin;
	private String taskId;
	private String bikou;
}
