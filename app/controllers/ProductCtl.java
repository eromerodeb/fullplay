package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Product;
import models.Supply;
import play.Logger;
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
		Form<Product> boundForm = productForm.bind(json);
		if (boundForm.hasErrors()) {
			ObjectNode response = Util.jsonResponse("Check this field(s):", false);
	        response.put("data", boundForm.errorsAsJson());
			return badRequest(response);
		}
        
        Product product;
        Ebean.beginTransaction();
        try	{
        	product = (Product) Json.fromJson(json, Product.class);
        	Product.save(product);
        	Ebean.commitTransaction();
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		} finally {
			Ebean.endTransaction();
		}
        
        JsonNode jsonObject = Json.toJson(product);
        return created(Util.jsonResponse(jsonObject, true));
	}
	
	public static Result update(Integer id) {
		JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Util.jsonResponse("Expecting Json data", false));
        }
        Form<Product> boundForm = productForm.bind(json);
		if (boundForm.hasErrors()) {
			ObjectNode response = Util.jsonResponse("Check this field(s):", false);
	        response.put("data", boundForm.errorsAsJson());
			return badRequest(response);
		}
		
        Product product;
        product = Product.find(id);
        if (product == null) {
			return notFound(Util.jsonResponse("Product not Found", false));
		}
        
        Ebean.beginTransaction();
        try	{
        	product = (Product) Json.fromJson(json, Product.class);
        	List<Supply> supplies = new ArrayList<Supply>();
        	for (Iterator<Supply> iterator = product.getSupplies().iterator(); iterator.hasNext();) {
				Supply supply = (Supply) iterator.next();
				supplies.add(Supply.find(supply.getID()));
			}
        	product.setSupplies(supplies);
        	Product.update(product);
        	Ebean.commitTransaction();
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		} finally {
			Ebean.endTransaction();
		}
        
        JsonNode jsonObject = Json.toJson(product);
        return created(Util.jsonResponse(jsonObject, true));
	}

	public static Result delete(Integer id) {
		Product product = Product.find(id);
        if (product == null) {
			return notFound(Util.jsonResponse("Product not Found", false));
		}
        
        Ebean.beginTransaction();
        try	{
        	Product.delete(product);
        	Ebean.commitTransaction();
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		} finally {
			Ebean.endTransaction();
		}
        
        ObjectNode response = Util.jsonResponse("Product deleted.", true);
        response.put("data", id);
        return created(response);
	}
	
}
