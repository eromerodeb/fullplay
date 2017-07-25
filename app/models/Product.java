package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.data.validation.Constraints.MaxLength;
import play.db.ebean.Model;

@Entity
public class Product extends Model {

    private static final Finder<Integer, Product> FIND = new Finder<>(Integer.class, Product.class);
    
	@Id
	private Integer ID;

	private String name;
	
	@MaxLength(500)
	private String description;

	@ManyToMany
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
