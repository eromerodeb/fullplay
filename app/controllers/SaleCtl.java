package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import models.Supply;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Util;

public class SaleCtl extends Controller {
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

}
