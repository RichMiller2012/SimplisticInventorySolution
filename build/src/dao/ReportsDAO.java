package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;

import entity.InventoryTO;
import entity.SoldItemTO;
import entity.TransactionsTO;

public class ReportsDAO {

	private Session session;
	
	public ReportsDAO() {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(InventoryTO.class)
				.addAnnotatedClass(SoldItemTO.class)
				.addAnnotatedClass(TransactionsTO.class)
				.buildSessionFactory();
	
		
		this.session = factory.getCurrentSession();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TransactionsTO> getAllTransactions() {
		Set<TransactionsTO> transactions = new HashSet<>();
		try {
			openSession();
			session.beginTransaction();
			
			transactions = new HashSet<>((ArrayList<TransactionsTO>) session.createCriteria(TransactionsTO.class)
					.addOrder(Order.asc("id")).list());
			session.getTransaction().commit();
		} catch(Exception e) {
			System.out.println("There was an issue fetching all transactions");
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return new ArrayList<>(transactions);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<SoldItemTO> getAllSoldItems() {
		List<SoldItemTO> soldItems = new ArrayList<>();
		try {
			openSession();
			session.beginTransaction();
			soldItems = (ArrayList<SoldItemTO>) session.createCriteria(SoldItemTO.class).list();

		} catch(Exception e) {
			System.out.println("There was an issue fetching all sold items");
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return soldItems;
	}
	
	private void openSession() {
		if(!session.isOpen()) {
			session = session.getSessionFactory().openSession();
		}
	}
	
}
