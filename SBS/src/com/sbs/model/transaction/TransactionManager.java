package com.sbs.model.transaction;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import antlr.debug.NewLineEvent;

import com.sbs.model.notification.Notification;
import com.sbs.model.user.SessionFactoryUtil;
import com.sbs.model.user.User;
import com.sbs.model.user.UserManager;

public class TransactionManager {

	private static Session session;
	private static void createSession(){
		session = SessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
	}
	
	public static void createTransaction(Transaction t) {
		createSession();
		session.save(t);
		session.getTransaction().commit();
		session.close();
	}
	
	public static void askAuthorization() {
		/** Send Request **/
		
	}
	
	public static List<Transaction> getAllTransactions() {
		createSession();
		String hql = "from Transaction as t where 1=1";
		Query query = session.createQuery(hql);
		// query.setString("strID", strID);
		List<Transaction> list = query.list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	public static List<Transaction> getExtTransactions(String userId) {
		createSession();
		
		String hql = "select t.txnId, t.txnType, t.txnDate, t.fromUserId, t.toUserId, t.amount, t.balance from Transaction t where "
				+ "(t.txnType = 'CREDIT' and t.toUserId = :userId) "
				+ "or (t.txnType = 'DEBIT' and t.fromUserId = :userId) "
				+ "or (t.txnType = 'TRANSFER' and (t.fromUserId = :userId or t.toUserId = :userId))"
				+ "or (t.txnType = 'PAYMENT_REQUESTED' and (t.fromUserId = :userId or t.toUserId = :userId))"
				+ "or (t.txnType = 'PAYMENT_GIVEN' and (t.fromUserId = :userId or t.toUserId = :userId))";
		Query query = session.createQuery(hql);
		query.setString("userId", userId);
		List<Transaction> list = query.list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	public static List<Transaction> getIntTransactions(String userId) {
		// The active user
		User u = UserManager.queryUser(userId);
		List<User> sameDeptUsers = UserManager.getUsersByDept(u.getDeptID());
		List<Transaction> tlist = new ArrayList<Transaction>();
		
		// For Regular Internal User
		if (u.getRoleID() == 4) {
			createSession();
			String hql = "from Transaction t where t.txnType not in ('DEBIT', 'CREDIT', 'TRANSFER')";
			Query query = session.createQuery(hql);
			//query.setString("userId", "kdvyas");
			tlist = query.list();
			List<Transaction> newTlist = new ArrayList<Transaction>();
			
			// Select transactions which are of the same department including user
			for (Transaction t : tlist) {
				for (User sameDeptUser : sameDeptUsers) {
					if (sameDeptUser.getUserID().equals(t.getFromUserId()) 
							|| sameDeptUser.getUserID().equals(t.getToUserId())) {
						newTlist.add(t);
					}
				}
			}
			
			
			//hql = "from Notification n where n.requesterId = :requesterId and ( n.approvedDate = null or (day(current_date()) - day(n.approvedDate)) < n.timeoutDays)";
			hql = "from Notification n where n.requesterId = :requesterId";
			query = session.createQuery(hql);
			query.setString("requesterId", "requester");
			List<Notification> nlist = query.list();
			session.getTransaction().commit();
			session.close();
			for (Transaction t : newTlist) {
				t.setAmount(0);
				t.setBalance(0);
				t.setFromAccountId(null);
				t.setToAccountId(null);
				
				// Show fields for self-transaction
				if (u.getUserID().equals(t.getFromUserId()) || u.getUserID().equals(t.getToUserId())) {
					t.setStatus("X"); // Self Transaction
				} else {
					
					// Get status based on notifications
					String fromUserStatus = ""; // no notification 
					String toUserStatus = "";  // no notification
					if (t.getFromUserId() != null && !t.getFromUserId().equals("")) {
						for (Notification n : nlist) {
							if (n.getKey1() == t.getTxnId() && n.getUserId().equals(t.getFromUserId())) {
								//t.setStatus(n.getStatus());
								fromUserStatus = n.getStatus();
							}
						}
					} else {
						fromUserStatus = "X"; // fromUser not exists
					}
					if (t.getToUserId() != null && !"".equals(t.getToUserId())) {
						for (Notification n : nlist) {
							if (n.getKey1() == t.getTxnId() && n.getUserId().equals(t.getToUserId())) {
								//t.setStatus(n.getStatus());
								toUserStatus = n.getStatus();
							}
						}
					} else {
						toUserStatus = "X"; // toUser not exists
					}
					
					if (("A".equals(fromUserStatus) && "A".equals(toUserStatus)) 
							|| ("A".equals(fromUserStatus) && "X".equals(toUserStatus))
							|| ("X".equals(fromUserStatus) && "A".equals(toUserStatus))) {
						t.setStatus("A");
					} else if ("D".equals(fromUserStatus) || "D".equals(toUserStatus)) {
						t.setStatus("D");
					} else if ("R".equals(fromUserStatus) || "R".equals(toUserStatus)){
						t.setStatus("R");
					} 
					
					if (!"A".equals(t.getStatus())) {
						t.setFromUserId("");
						t.setToUserId("");
					}
				}
				
			}
		} 
		// For Department manager of Corporate Official
		// Can see all External Transactions and Internal Transactions fo department
		else if (u.getRoleID() == 5 || u.getRoleID() == 6) {
			createSession();
			String hql = "from Transaction t where 1 = 1";
			Query query = session.createQuery(hql);
			//query.setString("userId", "kdvyas");
			tlist = query.list();
			
			List<Transaction> newTlist = new ArrayList<Transaction>();
			// Select transactions which are of the same department including user or external txn
			for (Transaction t : tlist) {
				if (TransactionType.CREDIT.getTxnTypeCode().equals(t.getTxnType()) ||
						TransactionType.DEBIT.getTxnTypeCode().equals(t.getTxnType()) ||
						TransactionType.TRANSFER.getTxnTypeCode().equals(t.getTxnType())) {
					newTlist.add(t);
				} else {
					for (User sameDeptUser : sameDeptUsers) {
						if (sameDeptUser.getUserID().equals(t.getFromUserId())
								|| sameDeptUser.getUserID().equals(t.getToUserId())) {
							newTlist.add(t);
						}
					}
				}
			}
			
			
			//hql = "from Notification n where n.requesterId = :requesterId and ( n.approvedDate = null or (day(current_date()) - day(n.approvedDate)) < n.timeoutDays)";
			hql = "from Notification n where n.requesterId = :requesterId";
			query = session.createQuery(hql);
			query.setString("requesterId", userId);
			List<Notification> nlist = query.list();
			session.getTransaction().commit();
			session.close();
			for (Transaction t : newTlist) {
				t.setAmount(0);
				t.setBalance(0);
				t.setFromAccountId(null);
				t.setToAccountId(null);
				
				// For External Transaction get status based on Notification
				if (TransactionType.CREDIT.getTxnTypeCode().equals(t.getTxnType()) ||
						TransactionType.DEBIT.getTxnTypeCode().equals(t.getTxnType()) ||
						TransactionType.TRANSFER.getTxnTypeCode().equals(t.getTxnType())) {
					// Else Get status from Notification Status
					String fromUserStatus = ""; // no notification 
					String toUserStatus = "";  // no notification
					if (t.getFromUserId() != null && !"".equals(t.getFromUserId())) {
						for (Notification n : nlist) {
							if (n.getKey1() == t.getTxnId() && n.getUserId().equals(t.getFromUserId())) {
								//t.setStatus(n.getStatus());
								fromUserStatus = n.getStatus();
							}
						}
					} else {
						fromUserStatus = "X"; // fromUser not exists
					}
					if (t.getToUserId() != null && !"".equals(t.getToUserId())) {
						for (Notification n : nlist) {
							if (n.getKey1() == t.getTxnId() && n.getUserId().equals(t.getToUserId())) {
								//t.setStatus(n.getStatus());
								toUserStatus = n.getStatus();
							}
						}
					} else {
						toUserStatus = "X"; // toUser not exists
					}
					
					if (("A".equals(fromUserStatus) && "A".equals(toUserStatus)) 
							|| ("A".equals(fromUserStatus) && "X".equals(toUserStatus))
							|| ("X".equals(fromUserStatus) && "A".equals(toUserStatus))) {
						t.setStatus("A");
					} else if ("D".equals(fromUserStatus) || "D".equals(toUserStatus)) {
						t.setStatus("D");
					} else if ("R".equals(fromUserStatus) || "R".equals(toUserStatus)){
						t.setStatus("R");
					} 
					
					if (!"A".equals(t.getStatus())) {
						t.setFromUserId("");
						t.setToUserId("");
					}
					
				} else {
					t.setStatus("S"); // Department Transaction
				}
			}
		}
		return tlist;
	}
	
	public static List<Transaction> getSystemLog() {
		createSession();
		
		String hql = "select t.txnId, t.txnType, t.txnDate, t.details from Transaction t where t.txnType not in ('DEBIT', 'CREDIT', 'TRANSFER')";
		Query query = session.createQuery(hql);
		List<Transaction> list = query.list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	public static Transaction getTransaction(int txnId) {
		createSession();
		String hql = "from Transaction t where txnId = :txnId";
		Query query = session.createQuery(hql);
		query.setInteger("txnId", txnId);
		List<Transaction> tList = query.list();
		session.getTransaction().commit();
		session.close();
		if (tList.size() > 0) {
			return tList.get(0);
		} else return null;
	}
}
