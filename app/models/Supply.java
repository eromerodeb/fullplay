package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Supply extends Model {

    private static final Finder<Integer, Supply> FIND = new Finder<>(Integer.class, Supply.class);
	
	@Id
	private Integer ID;

	@Required
	private String name;
	
	@Min(0)
	@Required
	private Double stock;
	
	public Supply(Integer id) {
		this.setID(id);
	}
	
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

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) throws Exception {
//		if (stock < 0) {
//			throw new Exception("Not remain supply: " + getName() );
//		}
		this.stock = stock;
	}

	public static Supply find(Integer id) {
		return FIND.byId(id);
	}
	
	public static List<Supply> findAll() {
		return FIND.all();
	}
	
	public static void save(Supply _supply) {
		_supply.save();
	}
	
	public static void update(Supply _supply) {
		_supply.update();
	}
	
	public static void delete(Supply _supply) {
		_supply.delete();
	}

}
