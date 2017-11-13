package com.iotek.biz;

/**
 * 管理订单子界面的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ManageOrderInnerController {
	/**
	 * 执行选项的方法
	 * 
	 * @param option
	 *            选项
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public int doOption(int option) {
		switch (option) {
		case 1:
			// 修改指定订单中所有订单项状态
			new ManageOrderController().updateOrder();
			break;
		case 2:
			// 修改指定订单项状态
			new ManageOrderController().updateOrderDetail();
			break;
		case 9:
			// 返回上级菜单
			return 1;
		case 0:
			// 退出
			return 2;
		default:
			System.out.println("无此选项，请重新输入！");
			break;
		}
		return 0;
	}
}
