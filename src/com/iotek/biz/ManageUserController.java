package com.iotek.biz;

import java.util.List;
import java.util.Scanner;

import com.iotek.bean.User;
import com.iotek.db.dao.impl.UserDaoImpl;
import com.iotek.util.CheckEmail;
import com.iotek.util.FilterInputMismatch;

/**
 * 管理用户的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class ManageUserController {
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
			// 显示所有用户信息
			list();
			break;
		case 2:
			// 修改用户密码
			update(0);
			break;
		case 3:
			// 修改用户等级
			update(1);
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

	/**
	 * 显示用户信息的方法
	 */
	private void list() {
		List<User> list = new UserDaoImpl().selectAll();
		System.out.println("用户ID\t用户名\t用户密码\t收货地址\t邮箱\t\t经验值\t等级\t余额");
		for (User user : list) {
			System.out.println(user);
		}
	}

	/**
	 * 更改用户信息
	 * 
	 * @param i
	 *            由此决定更新哪个字段，0-修改密码，1-修改等级，2-地址和邮箱
	 * @return true or false
	 */
	public void update(int i) {
		User user = null;
		Scanner in = new Scanner(System.in);

		// 管理员只需要通过用户名就能获取该用户信息
		System.out.println("请输入用户名：");
		String userName = in.nextLine();
		user = new User(userName, null, null, null, 0, 0, 0);
		user = new UserDaoImpl().selectByUserName(user);
		if (user == null) {
			System.err.println("该用户不存在！");
		} else {
			user = updateUser(i, user);
		}
		if (user != null) {
			boolean flag = new UserDaoImpl().update(user);
			if (flag) {
				// System.out.println("更新成功！");
			} else {
				System.out.println("更新失败！");
			}
		}
	}

	/**
	 * 更新用户信息的实际操作方法
	 * 
	 * @param i
	 *            由此决定更新哪个字段，0-修改密码，1-修改等级，2-地址和邮箱
	 * @param user
	 *            要更新的用户对象
	 * @return User 更新后的用户对象
	 */
	public User updateUser(int i, User user) {
		Scanner in = new Scanner(System.in);
		switch (i) {
		case 0:
			// 调用注册时的密码检测
			user = checkPwd(user);
			break;
		case 1:
			// 更改用户经验值，达到更改用户等级的目的
			System.out.println("请输入用户经验值：");
			System.out.println("【1-1000，2-5000，3-20000，4-50000,5-100000】");
			int exp = FilterInputMismatch.nextInt();
			user.setExp(exp);
			user.calcLevel();
			break;
		case 2:
			// 用户更改自己地址和邮箱
			System.out.println("请输入新的收货地址：");
			String address = in.nextLine();
			user.setAddress(address);
			user = checkEmailFormat(user);
			break;
		default:
			break;
		}
		return user;
	}

	/**
	 * 判断邮箱格式
	 * 
	 * @param user
	 * @return user
	 */
	public User checkEmailFormat(User user) {
		Scanner in = new Scanner(System.in);
		// 邮箱格式判断
		while (true) {
			System.out.println("请输入正确的邮箱格式：");
			System.out.println("例如：123@123.com.cn或123@123.com");
			String email = in.nextLine();
			// 调用工具类里面的方法
			if (CheckEmail.checkEmail(email)) {
				user.setEmail(email);
				return user;
			} else {
				continue;
			}
		}
	}

	/**
	 * 密码的二次输入验证
	 * 
	 * @param user
	 * @return user
	 */
	public User checkPwd(User user) {
		Scanner in = new Scanner(System.in);
		// 密码二次确认
		while (true) {
			System.out.println("请输入新密码：");
			String pwd = in.nextLine();
			System.out.println("请再次确认密码：");
			String pwd2 = in.nextLine();
			if (pwd2.equals(pwd)) {
				user.setPwd(pwd);
				return user;
			} else {
				System.out.println("密码不匹配！请重新输入！");
				continue;
			}
		}
	}

	/**
	 * 检测用户重名
	 * 
	 * @param user
	 * @return user
	 */
	public User checkUserName(User user) {
		Scanner in = new Scanner(System.in);
		// 用户名重名判断
		while (true) {
			String userName;
			while (true) {
				System.out.println("请输入用户名：");
				userName = in.nextLine();
				// 用户名输入不能带空格
				if (checkBlank(userName)) {
					break;
				} else {
					System.out.println("注册用户名不能带空格！");
					continue;
				}
			}
			user.setUserName(userName);
			// 查询数据库判断用户名是否已经存在
			if (new UserDaoImpl().selectByUserName(user) != null) {
				System.out.println("改用户名已存在请重新输入！");
				continue;
			} else {
				return user;
			}
		}
	}

	/**
	 * 限制输入字符串内容带空格
	 * 
	 * @param str
	 *            原字符串
	 * @return true or false
	 */
	public boolean checkBlank(String str) {
		// 解析字符串内是否带空格
		char[] c = str.toCharArray();
		for (char d : c) {
			if (d == ' ') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据当前用户等级返回折扣系数
	 * 
	 * @return 折扣系数
	 */
	public static double checkUserLevel() {
		// 根据用户等级返回折扣系数
		switch (MainMenuController.user.getLevel()) {
		case 1:
			return 1;
		case 2:
			return 0.95;
		case 3:
			return 0.9;
		case 4:
			return 0.85;
		case 5:
			return 0.8;
		default:
			return 1;
		}
	}
}
