package com.ku6ads.util;

/**
 * 业务状态类
 * @author yangHanguang
 *
 */
public class BussinessStatus {
	
	/**
	 * 草稿 <br>
	 * 0
	 */
	public static int draft = 0;
	
	/**
	 * 待一审 <br>
	 * 1
	 */
	public static int verify_first = 1;
	
	/**
	 * 待客户确认<br>
	 * 2
	 */
	public static int verify_customer = 2;
	
	/**
	 * 待二审<br> 
	 * 3
	 */
	public static int verify_second = 3;
	
	/**
	 * 退回待修改<br> 
	 * 4
	 */
	public static int back = 4;
	
	/**
	 * 未通过<br>
	 * 5
	 */
	public static int verify_not_pass = 5;
	
	/**
	 * 通过
	 * 6
	 */
	public static int verify_pass = 6;
	
	/**
	 * 执行中 <br>
	 * 7
	 */
	public static int running = 7;
	
	/**
	 * 执行完成 <br>
	 * 8
	 */
	public static int run_over = 8;
	
	/**
	 * 终止 <br>
	 * 9
	 */
	public static int terminate = 9;
	
	
	/**
	 * 执行中修改状态 <br>
	 * 71
	 */
	public static int running_update = 71;
	
	/**
	 * 执行单是否执行过---是<br>
	 * 0
	 */
	public static int statusFlag_notExcuted = 0;
	
	/**
	 * 执行单是否执行过---否<br>
	 * 1
	 */
	public static int statusFlag_hasExcuted = 1;
	
	/**
	 * 广告位业务状态
	 * 
	 * 删除 <br>
	 * 0
	 */
	public static int ADVPOSITION_DELETE = 0;
	
	/**
	 * 广告位业务状态
	 * 
	 * 创建 <br>
	 * 1
	 */
	public static int ADVPOSITION_CREATE = 1;
	
	/**
	 * 广告位业务状态
	 * 
	 * 启用 <br>
	 * 2
	 */
	public static int ADVPOSITION_START = 2;
	
	/**
	 * 广告位业务状态
	 * 
	 * 停止 <br>
	 * 3
	 */
	public static int ADVPOSITION_STOP = 3;
	
	/**
	 * 是默认执行单<br>
	 * 1
	 */
	public static int project_default = 1;
}
