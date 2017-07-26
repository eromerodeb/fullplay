package models;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Sale extends Model {

	private static final Finder<Integer, Sale> FIND = new Finder<>(Integer.class, Sale.class);
	
	@Id
	private Integer ID;

	@Required
	private String buyer;

	@Required
	@ManyToOne
	private Product product;

	@Min(0)
	@Required
	private Integer amount;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public static Sale find(Integer id) {
		return FIND.byId(id);
	}
	
	public static List<Sale> findAll() {
		return FIND.all();
	}
	
	public static void save(Sale _sale) {
		_sale.save();
	}

}
