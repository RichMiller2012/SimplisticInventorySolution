package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;

@Entity
public class TransactionsTO {

	public TransactionsTO() {
		
	}
	
	public TransactionsTO(int id, List<SoldItemTO> soldItems, Date sellDate, Double total) {
		this.id = id;
		this.soldItems = soldItems;
		this.sellDate = sellDate;
		this.total = total;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = "transaction")
	private List<SoldItemTO> soldItems = new ArrayList<>();
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date sellDate = new Date();
	
	@Column
	private Double total;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<SoldItemTO> getSoldItems() {
		return soldItems;
	}

	public void setSoldItems(List<SoldItemTO> soldItems) {
		this.soldItems = soldItems;
	}

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
