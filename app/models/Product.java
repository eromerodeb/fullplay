package models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Product extends Model {

    private static final Finder<Integer, Product> FIND = new Finder<>(Integer.class, Product.class);
    
	@Id
	private Integer ID;

	@Required
	private String name;

	@Required
	@MaxLength(500)
	@Column(columnDefinition="text")
	private String description;

	@Required
	@ManyToMany(cascade={CascadeType.ALL})
	private List<Supply> supplies = new ArrayList<>();
	
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Supply> getSupplies() {
		return supplies;
	}

	public void setSupplies(List<Supply> supplies) {
		this.supplies = supplies;
	}

	public static Product find(Integer id) {
		return FIND.byId(id);
	}
	
	public static List<Product> findAll() {
		return FIND.all();
	}
	
	public static void save(Product _product) {
		_product.save();
	}
	
	public static void update(Product _product) {
		_product.update();
	}
	
	public static void delete(Product _product) {
		_product.delete();
	}

}
