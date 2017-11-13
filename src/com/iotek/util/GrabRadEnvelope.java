package com.iotek.util;

import java.util.List;

import com.iotek.bean.RadEnvelope;
import com.iotek.bean.UserGotRadEnv;
import com.iotek.biz.MainMenuController;
import com.iotek.db.dao.impl.RadEnvelopDaoImpl;
import com.iotek.db.dao.impl.UserDaoImpl;
import com.iotek.db.dao.impl.UserGotREDaoImpl;

public class GrabRadEnvelope {
	/**
	 * 检测红包状态并显示活动
	 */
	public static void showRadEnv() {
		// 如果红包余额为0，则不开启活动
		RadEnvelope re = new RadEnvelopDaoImpl().selectById(1);
		List<UserGotRadEnv> list = new UserGotREDaoImpl().selectByRid(1);
		if (re.getBalance() < 1) {
			return;
		} else {
			for (UserGotRadEnv ugr : list) {
				if (ugr.getUserName().equals(
						MainMenuController.user.getUserName())) {
					return;
				}
			}
			System.out.println("$$$$$$$$$$$$$$$$$$");
			System.out.println("$===恭喜进入抢红包===$");
			System.out.println("$$$$$$$$$$$$$$$$$$");
			while (true) {
				System.out.println("【1-开始，0-退出】");
				int i = FilterInputMismatch.nextInt();
				if (i == 0) {
					return;
				} else if (i == 1) {
					startGame(re);
					return;
				} else {
					continue;
				}
			}
		}
	}

	/**
	 * 开始游戏
	 * 
	 * @param re
	 *            红包
	 */
	public static void startGame(RadEnvelope re) {
		int factor = 0;
		System.out.println("完成以下游戏就可以获得抢红包加成哦！");
		System.out.println("游戏解释：进行5道计算题，将根据正确率和耗时来计算倍率！");
		System.out.println("【建议先把输入法切换成英文输入模式】");
		while (true) {
			System.out.println("【1-赶快开始游戏吧！，0-不玩游戏直接抢红包！】");
			int i = FilterInputMismatch.nextInt();
			if (i == 0) {
				factor = 1;
				break;
			} else if (i == 1) {
				factor = arithmeticGame();
				System.out.println("恭喜您！获得抢红包金额加成" + factor + "倍！！！");
				System.out.println("快去抢红包吧！！！");
				break;
			} else {
				System.out.println("无此选项！");
				continue;
			}
		}
		startGrab(re, factor);
	}

	/**
	 * 开始抢红包
	 * 
	 * @param re
	 *            红包
	 * @param factor
	 *            加成系数
	 */
	public static void startGrab(RadEnvelope re, int factor) {
		double radEnvelop = 0;
		double beGrab = 0;
		double userBalance = 0;
		while (true) {
			System.out.println("【1-开始抢红包！，0-我是土豪才不稀罕！】");
			int j = FilterInputMismatch.nextInt();
			if (j == 0) {
				return;
			} else if (j == 1) {
				try {
					System.out.println("我抢。。。");
					Thread.sleep(1000);
					System.out.println("我再抢。。。");
					Thread.sleep(1000);
					// 根据系数加成抢到的金额
					radEnvelop = re.getSumMoney()
							* (Math.random() * 0.005 + 0.005) * factor;
					// 若金额大于红包余额，就把余额赋值给抢到的金额
					if (re.getBalance() < radEnvelop) {
						radEnvelop = re.getBalance();
					}
					radEnvelop = NumFormat.formatDouble(radEnvelop);
					// 修改红包里面被抢金额
					beGrab = re.getBeGrab();
					beGrab += radEnvelop;
					re.setBeGrab(beGrab);
					re.culBalance();
					new RadEnvelopDaoImpl().update(re);
					// 把金额充值到用户账户
					userBalance = MainMenuController.user.getBalance();
					userBalance += radEnvelop;
					MainMenuController.user.setBalance(userBalance);
					new UserDaoImpl().update(MainMenuController.user);
					// 新增抢到红包的用户信息
					UserGotRadEnv ugr = new UserGotRadEnv(
							MainMenuController.user.getUserName(),
							GetDate.getNowTime(), 1);
					new UserGotREDaoImpl().insert(ugr);
					System.out.println("恭喜你!!!抢到了一个" + radEnvelop + "元的红包！");
					System.out.println("系统已帮您将" + radEnvelop + "元转入您的账号！");
					System.out.println("感谢您长久以来对天狗商城的支持！");
					return;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("无此选项！");
				continue;
			}
		}
	}

	/**
	 * 充值红包
	 */
	public void rechargeRadEnv() {
		List<UserGotRadEnv> ugr = new UserGotREDaoImpl().selectByRid(1);
		for (UserGotRadEnv userGotRadEnv : ugr) {
			new UserGotREDaoImpl().delete(userGotRadEnv);
		}
		System.out.println("已开启抢红包游戏！");
		double adminBalance = MainMenuController.user.getBalance();
		while (true) {
			System.out.println("请输入放入红包的金额：");
			double sumMoney = FilterInputMismatch.nextDouble();
			if (adminBalance < sumMoney) {
				System.out.println("输入金额不可大于您的账户余额！");
				continue;
			} else {
				// 从管理员账号删除余额
				adminBalance -= sumMoney;
				MainMenuController.user.setBalance(adminBalance);
				// 把这些钱充到指定红包里面去
				RadEnvelope re = new RadEnvelopDaoImpl().selectById(1);
				double sumMoney2 = re.getSumMoney();
				sumMoney2 += sumMoney;
				re.setSumMoney(sumMoney2);
				re.culBalance();
				// 更新红包信息
				if (new RadEnvelopDaoImpl().update(re)) {
					System.out.println("已从您账户中扣除" + sumMoney + "元！");
					System.out.println("红包充值成功！");
					return;
				} else {
					System.out.println("红包充值失败！");
					return;
				}
			}
		}
	}

	/**
	 * 给出5道数学运算题，通过算术结果返回抢红包的加成系数
	 * 
	 * @return 加成系数
	 */
	private static int arithmeticGame() {
		int a = 0;
		int res = 0;
		int rightCount = 0;
		int wrongCount = 0;
		long startTime = 0L;
		long endTime = 0L;
		long gameTime = 0L;
		System.out.println("READY...");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("GO!!!");
		startTime = System.currentTimeMillis();
		// 总共答题5道
		while (rightCount + wrongCount < 5) {
			res = RandomGen.arithmeticGen();
			a = FilterInputMismatch.nextInt();
			if (a == res) {
				rightCount++;
			} else {
				wrongCount++;
			}
		}
		endTime = System.currentTimeMillis();
		gameTime = endTime - startTime;
		System.out.println("总共回答5题：答对 " + rightCount + " 题，答错 " + wrongCount
				+ " 题！");
		System.out.println("总游戏时间为：" + gameTime / 1000.0 + "秒");

		return culFactor(wrongCount, gameTime);
	}

	/**
	 * 计算加成系数
	 * 
	 * @param wrongCount
	 *            错误数
	 * @param gameTime
	 *            游戏时间
	 * @return 加成系数
	 */
	private static int culFactor(int wrongCount, long gameTime) {
		int factor = 1;
		// 根据时间返回加成系数
		if (gameTime <= 7000) {
			factor += 6;
		} else if (gameTime > 7000 && gameTime <= 12000) {
			factor += 4;
		} else if (gameTime > 12000 && gameTime <= 17000) {
			factor += 3;
		} else if (gameTime > 17000 && gameTime <= 22000) {
			factor += 2;
		} else if (gameTime > 22000) {
			factor += 1;
		}

		// 根据错误题目返回加成系数
		switch (wrongCount) {
		case 0:
			factor += 5;
			break;
		case 1:
			factor += 3;
			break;
		case 2:
			factor += 2;
			break;
		case 3:
			factor += 1;
			break;
		case 4:
			factor += 0;
			break;
		default:
			factor -= 1;
			break;
		}
		return factor;
	}

}
