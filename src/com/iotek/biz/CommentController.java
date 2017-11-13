package com.iotek.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.iotek.bean.Comment;
import com.iotek.bean.Goods;
import com.iotek.bean.OrderDetail;
import com.iotek.db.dao.impl.CommentDaoImpl;
import com.iotek.util.FilterInputMismatch;
import com.iotek.util.GetDate;

/**
 * 评论的逻辑类
 * 
 * @author kd
 * @version 1.0
 * @since JDK 1.7
 * 
 */
public class CommentController {
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
			// 显示未评论的订单项
			listNotCommentGoods();
			break;
		case 2:
			// 显示历史评论
			listComment(new CommentDaoImpl().selectByUserName());
			break;
		case 3:
			// 更新评论
			updateMyComment();
			break;
		case 4:
			// 删除评论
			deleteMyComment();
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
	 * 删除评论
	 */
	public void deleteMyComment() {
		// 返回当前用户的所有评论
		List<Comment> list = new CommentDaoImpl().selectByUserName();
		listComment(list);
		if (list.size() == 0) {
			return;
		}
		System.out.println("输入要删除评论的序号：");
		int i = ShoppingCartController.checkIndex(list.size());
		Comment comm = list.get(i - 1);
		if (new CommentDaoImpl().delete(comm)) {
			System.out.println("删除成功！");
			return;
		} else {
			System.out.println("删除失败！");
			return;
		}
	}

	/**
	 * 更改我的评论
	 */
	public void updateMyComment() {
		Scanner in = new Scanner(System.in);
		// 返回当前用户的所有评论
		List<Comment> list = new CommentDaoImpl().selectByUserName();
		listComment(list);
		if (list.size() == 0) {
			return;
		}
		System.out.println("输入要修改订单项的序号：");
		int i = ShoppingCartController.checkIndex(list.size());
		Comment comm = list.get(i - 1);
		System.out.println("修改你的评论吧！");
		String content = in.nextLine();
		comm.setContent(content);
		if (new CommentDaoImpl().update(comm)) {
			System.out.println("修改评论成功！");
			return;
		} else {
			System.out.println("修改评论失败！");
			return;
		}
	}

	/**
	 * 显示未评价的订单
	 */
	public void listNotCommentGoods() {
		// 显示用户完成的订单项
		List<OrderDetail> od = new OrderController().getMyODByStatus(5);
		List<OrderDetail> od2 = new ArrayList<OrderDetail>();
		// 加到od2里面去，后面过滤信息时有用
		od2.addAll(od);
		// 返回用户的评论
		List<Comment> list = new CommentDaoImpl().selectByUserName();
		// 遍历用户完成的订单和评论列表，过滤出未评价的订单项
		for (OrderDetail odd : od) {
			int gid = odd.getGid();
			int oid = odd.getId();
			for (Comment comm : list) {
				if (comm.getGid() == gid && comm.getOid() == oid) {
					// 一旦存在就从od2中移除
					od2.remove(odd);
					break;
				}
			}
		}
		System.out.println("我还未评价的订单：");
		if (od2.size() != 0) {
			new ManageOrderController().listOrderDetail(od2);
			// 开始评论商品
			commentGoods(od2);
		} else {
			System.out.println("暂无可评价订单！");
		}
	}

	/**
	 * 评论商品
	 * 
	 * @param od
	 *            订单项集
	 */
	public void commentGoods(List<OrderDetail> od) {
		System.out.println("请输入订单项序号进行评论：");
		int i = ShoppingCartController.checkIndex(od.size());
		OrderDetail odd = od.get(i - 1);
		comment(odd);
	}

	/**
	 * 新增评论
	 */
	public void insertComment(OrderDetail od) {
		System.out.println("快去评论吧~");
		while (true) {
			System.out.println("【1-现在评论，0-稍后再评】");
			int i = FilterInputMismatch.nextInt();
			if (i == 0) {
				System.out.println("稍后可在【我要评论】中追加评论！");
				return;
			} else if (i == 1) {
				comment(od);
				return;
			} else {
				System.out.println("无此选项！");
				continue;
			}
		}
	}

	/**
	 * 开始评论
	 * 
	 * @param od
	 *            订单项
	 */
	private void comment(OrderDetail od) {
		Scanner in = new Scanner(System.in);
		System.out.println("请输入你对该商品的评论：");
		String content = in.nextLine();
		// 新建评论对象，用户名添加为当前登陆用户的用户名
		Comment c = new Comment(od.getGid(),
				MainMenuController.user.getUserName(), od.getId(), content,
				GetDate.getNowTime());
		boolean flag = new CommentDaoImpl().insert(c);
		if (flag) {
			System.out.println("感谢您的评论！");
			return;
		} else {
			System.out.println("评论失败！");
			return;
		}
	}

	/**
	 * 显示评论
	 */
	public void listComment(List<Comment> c) {
		Goods good = null;
		int count = 0;
		System.out.println("用户评论：");
		System.out.println("序号\t订单项ID\t用户名\t商品名\t评论内容\t评论时间");
		if (c.size() != 0) {
			for (Comment comm : c) {
				good = ManageGoodsController.getGood(comm.getGid());
				System.out.println((++count) + "\t" + comm.getOid() + "\t"
						+ comm.getUserName() + "\t" + good.getName() + "\t"
						+ comm.getContent() + "\t" + comm.getDate());
			}
		} else {
			System.out.println("暂无评论！");
		}
	}
}
