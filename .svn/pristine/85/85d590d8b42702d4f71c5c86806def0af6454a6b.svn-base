package com.ts.zx.ig.fbox;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

public class AlarmVO {
	
	private String code;
	
	private String name;
	
	private String alarmMsg;
	
	private int alarmState;
	
	private String valueOnLastEvent;
	
	private String lastRecoveredTime;
	
	private String lastTriggeredTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlarmMsg() {
		return alarmMsg;
	}

	public void setAlarmMsg(String alarmMsg) {
		this.alarmMsg = alarmMsg;
	}

	public int getAlarmState() {
		return alarmState;
	}

	public void setAlarmState(int alarmState) {
		this.alarmState = alarmState;
	}

	public String getValueOnLastEvent() {
		return valueOnLastEvent;
	}

	public void setValueOnLastEvent(String valueOnLastEvent) {
		this.valueOnLastEvent = valueOnLastEvent;
	}

	public String getLastRecoveredTime() {
		return lastRecoveredTime;
	}

	public void setLastRecoveredTime(String lastRecoveredTime) {
		this.lastRecoveredTime = parseTime(lastRecoveredTime);
	}

	public String getLastTriggeredTime() {
		return lastTriggeredTime;
	}

	public void setLastTriggeredTime(String lastTriggeredTime) {
		this.lastTriggeredTime = parseTime(lastTriggeredTime);
	}
	
	public String getAlarmStateStr() {
		return AlarmStateEnum.getNameByValue(alarmState);
	}
	
	private String parseTime(String time) {
		String str = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!StringUtils.isEmpty(time)) {
			try {
				Date date = df.parse(time.replace("T", " "));
				df.setTimeZone(TimeZone.getTimeZone("GMT+16"));
				str = df.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

}
