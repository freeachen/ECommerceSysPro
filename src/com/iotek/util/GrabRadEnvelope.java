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
	 * �����״̬����ʾ�
	 */
	public static void showRadEnv() {
		// ���������Ϊ0���򲻿����
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
			System.out.println("$===��ϲ���������===$");
			System.out.println("$$$$$$$$$$$$$$$$$$");
			while (true) {
				System.out.println("��1-��ʼ��0-�˳���");
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
	 * ��ʼ��Ϸ
	 * 
	 * @param re
	 *            ���
	 */
	public static void startGame(RadEnvelope re) {
		int factor = 0;
		System.out.println("���������Ϸ�Ϳ��Ի��������ӳ�Ŷ��");
		System.out.println("��Ϸ���ͣ�����5�������⣬��������ȷ�ʺͺ�ʱ�����㱶�ʣ�");
		System.out.println("�������Ȱ����뷨�л���Ӣ������ģʽ��");
		while (true) {
			System.out.println("��1-�Ͽ쿪ʼ��Ϸ�ɣ���0-������Ϸֱ�����������");
			int i = FilterInputMismatch.nextInt();
			if (i == 0) {
				factor = 1;
				break;
			} else if (i == 1) {
				factor = arithmeticGame();
				System.out.println("��ϲ���������������ӳ�" + factor + "��������");
				System.out.println("��ȥ������ɣ�����");
				break;
			} else {
				System.out.println("�޴�ѡ�");
				continue;
			}
		}
		startGrab(re, factor);
	}

	/**
	 * ��ʼ�����
	 * 
	 * @param re
	 *            ���
	 * @param factor
	 *            �ӳ�ϵ��
	 */
	public static void startGrab(RadEnvelope re, int factor) {
		double radEnvelop = 0;
		double beGrab = 0;
		double userBalance = 0;
		while (true) {
			System.out.println("��1-��ʼ���������0-���������Ų�ϡ������");
			int j = FilterInputMismatch.nextInt();
			if (j == 0) {
				return;
			} else if (j == 1) {
				try {
					System.out.println("����������");
					Thread.sleep(1000);
					System.out.println("������������");
					Thread.sleep(1000);
					// ����ϵ���ӳ������Ľ��
					radEnvelop = re.getSumMoney()
							* (Math.random() * 0.005 + 0.005) * factor;
					// �������ں�����Ͱ���ֵ�������Ľ��
					if (re.getBalance() < radEnvelop) {
						radEnvelop = re.getBalance();
					}
					radEnvelop = NumFormat.formatDouble(radEnvelop);
					// �޸ĺ�����汻�����
					beGrab = re.getBeGrab();
					beGrab += radEnvelop;
					re.setBeGrab(beGrab);
					re.culBalance();
					new RadEnvelopDaoImpl().update(re);
					// �ѽ���ֵ���û��˻�
					userBalance = MainMenuController.user.getBalance();
					userBalance += radEnvelop;
					MainMenuController.user.setBalance(userBalance);
					new UserDaoImpl().update(MainMenuController.user);
					// ��������������û���Ϣ
					UserGotRadEnv ugr = new UserGotRadEnv(
							MainMenuController.user.getUserName(),
							GetDate.getNowTime(), 1);
					new UserGotREDaoImpl().insert(ugr);
					System.out.println("��ϲ��!!!������һ��" + radEnvelop + "Ԫ�ĺ����");
					System.out.println("ϵͳ�Ѱ�����" + radEnvelop + "Ԫת�������˺ţ�");
					System.out.println("��л�������������칷�̳ǵ�֧�֣�");
					return;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("�޴�ѡ�");
				continue;
			}
		}
	}

	/**
	 * ��ֵ���
	 */
	public void rechargeRadEnv() {
		List<UserGotRadEnv> ugr = new UserGotREDaoImpl().selectByRid(1);
		for (UserGotRadEnv userGotRadEnv : ugr) {
			new UserGotREDaoImpl().delete(userGotRadEnv);
		}
		System.out.println("�ѿ����������Ϸ��");
		double adminBalance = MainMenuController.user.getBalance();
		while (true) {
			System.out.println("������������Ľ�");
			double sumMoney = FilterInputMismatch.nextDouble();
			if (adminBalance < sumMoney) {
				System.out.println("������ɴ��������˻���");
				continue;
			} else {
				// �ӹ���Ա�˺�ɾ�����
				adminBalance -= sumMoney;
				MainMenuController.user.setBalance(adminBalance);
				// ����ЩǮ�䵽ָ���������ȥ
				RadEnvelope re = new RadEnvelopDaoImpl().selectById(1);
				double sumMoney2 = re.getSumMoney();
				sumMoney2 += sumMoney;
				re.setSumMoney(sumMoney2);
				re.culBalance();
				// ���º����Ϣ
				if (new RadEnvelopDaoImpl().update(re)) {
					System.out.println("�Ѵ����˻��п۳�" + sumMoney + "Ԫ��");
					System.out.println("�����ֵ�ɹ���");
					return;
				} else {
					System.out.println("�����ֵʧ�ܣ�");
					return;
				}
			}
		}
	}

	/**
	 * ����5����ѧ�����⣬ͨ�������������������ļӳ�ϵ��
	 * 
	 * @return �ӳ�ϵ��
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
		// �ܹ�����5��
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
		System.out.println("�ܹ��ش�5�⣺��� " + rightCount + " �⣬��� " + wrongCount
				+ " �⣡");
		System.out.println("����Ϸʱ��Ϊ��" + gameTime / 1000.0 + "��");

		return culFactor(wrongCount, gameTime);
	}

	/**
	 * ����ӳ�ϵ��
	 * 
	 * @param wrongCount
	 *            ������
	 * @param gameTime
	 *            ��Ϸʱ��
	 * @return �ӳ�ϵ��
	 */
	private static int culFactor(int wrongCount, long gameTime) {
		int factor = 1;
		// ����ʱ�䷵�ؼӳ�ϵ��
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

		// ���ݴ�����Ŀ���ؼӳ�ϵ��
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
