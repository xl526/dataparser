package com.ts.zx.ig.fbox;

public enum AlarmStateEnum {
	
	STATE_0("未知", 0),
	
	STATE_1("触发", 1),
	
	STATE_2("恢复", 2),
	
	STATE_3("已确认", 3);
	
	private String name;
	
	private int value;
	
	private AlarmStateEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static String getNameByValue(int value) {
		for (AlarmStateEnum e : AlarmStateEnum.values()) {
			if (e.value == value)
				return e.name;
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
