package entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="sold_item")
public class SoldItemTO {

	public SoldItemTO() {
		
	}
	
	public SoldItemTO(int id, InventoryTO soldItem, int quantity, TransactionsTO transaction) {
		this.id = id;
		this.soldItem = soldItem;
		this.quantity = quantity;
		this.transaction = transaction;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn
	private InventoryTO soldItem;
	
	@Column(name="quantity")
	private int quantity;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private TransactionsTO transaction;
		
	public TransactionsTO getTransaction() {
		return transaction;
	}

	public void setTransaction(TransactionsTO transaction) {
		this.transaction = transaction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InventoryTO getSoldItem() {
		return soldItem;
	}

	public void setSoldItem(InventoryTO soldItem) {
		this.soldItem = soldItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void incrementQuantity() {
		this.quantity++;
	}
}
