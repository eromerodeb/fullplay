package controllers;

import play.api.data.Form;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;

import models.Product;
import play.*;
import play.mvc.*;
import play.mvc.Http.RequestBody;
import utils.Util;
import views.html.*;

public class Application extends Controller {

	public static class RC {
		public String r;
		public String t;
		public Integer y;
	}
	
    public static Result index() {
    	return ok(index.render("Your new application is ready."));
    }

    public static Result json() {
    	JsonNode json = request().body().asJson();
    	if (json == null){
            return badRequest(utils.Util.jsonResponse("Expecting Json data", false));
    	}
    	
//    	RequestBody body = request().body();
    	
//    	RC rc = request().body().as(RC.class);

//    	Json.toJson(rc);
    	
    	return ok("smaple");
    }
    
//
//    public static Result update() {
//    	JsonNode json = request().body().asJson();
//        if (json == null){
//            return badRequest(Util.jsonResponse("Expecting Json data", false));
//        }
//        Product product;
//        try	{
//        	product = (Product) Json.fromJson(json, Product.class);
//        	Product.save(product);
//        } catch (Exception e) {
//			return badRequest(e.getMessage());
//		}
//        JsonNode jsonObject = Json.toJson(product);
//        return created(Util.jsonResponse(jsonObject, true));
//	}

}
