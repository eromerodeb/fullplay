package models;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Min;
import play.db.ebean.Model;

@Entity
public class Sale extends Model {
	
	@Id
	private Integer ID;
	
	private String buyer;
	
	@Nonnull
	@ManyToOne
	private Product product;
	
	@Min(0)
	private Integer amount;
}
