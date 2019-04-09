package com.zjezyy.entity;

import lombok.Data;
/**
 * 
* @ClassName: TeskEntity
* @Description: 任务工作量对象
* @author wuxuecheng
* @date 2019年3月5日
*
 */
@Data
public class TaskEntity {
	private String taskname;//工作名称
	private int tasksize;//工作量
	@Override
	public String toString() {
		return "TaskEntity [taskname=" + taskname + ", tasksize=" + tasksize + "]";
	}
	
	public boolean equals(TaskEntity taskEntity) {
		return this.taskname.equals(taskEntity.taskname)&& this.tasksize==taskEntity.tasksize;
    }
	
}
