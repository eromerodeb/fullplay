package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import models.Product;
import models.Supply;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Util;

public class SupplyCtl extends Controller {

	public static Result list() {
		return ok(Json.toJson(Supply.findAll()));
	}
	
	public static Result get(Integer id) {
		Supply supply = Supply.find(id);
		if (supply == null) {
			return notFound(Util.jsonResponse("Supply not Found", false));
		}

        JsonNode jsonObject = Json.toJson(supply);
		return ok(Util.jsonResponse(jsonObject, true));
	}
	
	public static Result add() {
    	JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Util.jsonResponse("Expecting Json data", false));
        }
        
        Supply supply;
        try	{
        	supply = (Supply) Json.fromJson(json, Supply.class);
        	Supply.save(supply);
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		}
        
        JsonNode jsonObject = Json.toJson(supply);
        return created(Util.jsonResponse(jsonObject, true));
	}

	public static Result update(Integer id) {
		JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Util.jsonResponse("Expecting Json data", false));
        }
        
        Supply supply;
        try	{
        	supply = (Supply) Json.fromJson(json, Supply.class);
        	Supply.update(supply);
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		}
        
        JsonNode jsonObject = Json.toJson(supply);
        return created(Util.jsonResponse(jsonObject, true));
	}

	public static Result delete(Integer id) {
		Supply supply = Supply.find(id);
        if (supply == null) {
			return notFound(Util.jsonResponse("Product not Found", false));
		}
        
        try	{
        	Supply.delete(supply);
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		}
        
        JsonNode jsonObject = Json.toJson(supply);
        return created(Util.jsonResponse(jsonObject, true));
	}
	
}
