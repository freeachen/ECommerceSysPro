package com.iotek.biz;

import java.util.Scanner;

import com.iotek.bean.User;
import com.iotek.db.dao.impl.UserDaoImpl;
import com.iotek.util.GrabRadEnvelope;
import com.iotek.view.AdminMenu;
import com.iotek.view.UserMenu;

/**
 * 登陆界面的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class MainMenuController {
	public static User user = null;

	/**
	 * 执行选项的方法
	 * 
	 * @param option
	 *            选项
	 * @return 0-继续循环，1-退出当前循环，2-退到登录界面
	 */
	public void doOption(int option) {
		switch (option) {
		case 1:
			// 注册
			register();
			break;
		case 2:
			// 登陆
			checkLogin(login());
			break;
		case 0:
			// 退出系统
			MainMenuController.exit();
			break;
		default:
			System.out.println("输入错误，请重新输入！");
			break;
		}
	}

	/**
	 * 注册
	 * 
	 * @return 用户对象
	 */
	public void register() {
		Scanner in = new Scanner(System.in);
		User user = new User();
		// 用户名重名检查
		user = new ManageUserController().checkUserName(user);
		// 密码确认验证
		user = new ManageUserController().checkPwd(user);
		System.out.println("请输入收货地址：");
		String address = in.nextLine();
		user.setAddress(address);
		// 邮箱格式限定
		user = new ManageUserController().checkEmailFormat(user);
		// 用户初始等级1
		user.setLevel(1);
		boolean falg = new UserDaoImpl().insert(user);
		if (falg) {
			System.out.println("注册成功！");
		} else {
			System.err.println("注册失败！");
		}
	}

	/**
	 * 登陆
	 */
	public User login() {
		Scanner in = new Scanner(System.in);
		System.out.println("请输入用户名：");
		String userName = in.nextLine();
		System.out.println("请输入密码：");
		String pwd = in.nextLine();

		User u = new User(userName, pwd, null, null, 0, 1, 0);
		return u;
	}

	/**
	 * 登陆验证
	 */
	private void checkLogin(User u) {
		int i = 0;
		// 判断登陆用户的身份
		user = new UserDaoImpl().selectByUserNameAndPwd(u);
		if (user != null) {
			// 管理员登陆
			if (user.getUserName().equals("admin") && user.getPwd().equals("admin")) {
				System.out.println("管理员登陆成功！");
				while (true) {
					i = new AdminMenu().show();
					if (i != 0) {
						break;
					}
				}
			} else {
				// 检查并开启抢红包游戏
				GrabRadEnvelope.showRadEnv();
				// 用户登陆
				System.out.println(user.getUserName() + "，欢迎光临！");
				while (true) {
					if (new UserMenu().show() != 0) {
						break;
					}
				}
			}
		} else {
			System.err.println("登陆失败！");
		}
	}

	/**
	 * 退出系统
	 */
	public static void exit() {
		System.out.println("欢迎下次再来！");
		System.exit(0);
	}
}
