package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import models.Product;
import models.Supply;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Util;

public class ProductCtl extends Controller {
	private static final Form<Product> productForm = Form.form(Product.class);

	public static Result list() {
		return ok(Json.toJson(Product.findAll()));
	}
	
	public static Result get(Integer id) {
		Product product = Product.find(id);
		if (product == null) {
			return notFound(Util.jsonResponse("Product not Found", false));
		}

		JsonNode jsonObject = Json.toJson(product);
		return ok(Util.jsonResponse(jsonObject, true));
	}
	
	public static Result add() {
    	JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Util.jsonResponse("Expecting Json data", false));
        }
        
        Product product;
        try	{
        	product = (Product) Json.fromJson(json, Product.class);
        	Product.save(product);
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		}
        
        JsonNode jsonObject = Json.toJson(product);
        return created(Util.jsonResponse(jsonObject, true));
	}
	
	public static Result update(Integer id) {
//		Form<Product> boundForm = productForm.bindFromRequest();
//		Product product = boundForm.get();

		JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Util.jsonResponse("Expecting Json data", false));
        }
        
        Product product;
        product = Product.find(id);
        if (product == null) {
			return notFound(Util.jsonResponse("Product not Found", false));
		}
        
        try	{
        	Product product_r = (Product) Json.fromJson(json, Product.class);
        	List<Supply> supplies = new ArrayList<Supply>();
        	for (Iterator<Supply> iterator = product_r.getSupplies().iterator(); iterator.hasNext();) {
				Supply supply = (Supply) iterator.next();
				supplies.add(Supply.find(supply.getID()));
			}
        	product.setSupplies(supplies);
        	Product.update(product);
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		}
        
        JsonNode jsonObject = Json.toJson(product);
        return created(Util.jsonResponse(jsonObject, true));
	}

	public static Result delete(Integer id) {
		Product product = Product.find(id);
        if (product == null) {
			return notFound(Util.jsonResponse("Product not Found", false));
		}
        
        try	{
        	Product.delete(product);
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		}
        
        JsonNode jsonObject = Json.toJson(product);
        return created(Util.jsonResponse(jsonObject, true));
	}
	
}
