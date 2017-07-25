package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import models.Product;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Util;

public class ProductCtl extends Controller {

	public static Result list() {
		return ok(Json.toJson(Product.findAll()));
	}
	
	public static Result get(Integer id) {
		return ok();
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

	
}
