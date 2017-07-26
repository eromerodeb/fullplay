package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Supply;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Util;

public class SupplyCtl extends Controller {
	private static final Form<Supply> supplyForm = Form.form(Supply.class);

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
        Form<Supply> boundForm = supplyForm.bind(json);
		if (boundForm.hasErrors()) {
			ObjectNode response = Util.jsonResponse("Check this field(s):", false);
	        response.put("data", boundForm.errorsAsJson());
			return badRequest(response);
		}
		
        Supply supply;
        Ebean.beginTransaction();
        try	{
        	supply = (Supply) Json.fromJson(json, Supply.class);
        	Supply.save(supply);
        	Ebean.commitTransaction();
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		} finally {
			Ebean.endTransaction();
		}
        
        JsonNode jsonObject = Json.toJson(supply);
        return created(Util.jsonResponse(jsonObject, true));
	}

	public static Result update(Integer id) {
		JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Util.jsonResponse("Expecting Json data", false));
        }
        Form<Supply> boundForm = supplyForm.bind(json);
		if (boundForm.hasErrors()) {
			ObjectNode response = Util.jsonResponse("Check this field(s):", false);
	        response.put("data", boundForm.errorsAsJson());
			return badRequest(response);
		}
		
        Supply supply;
        Ebean.beginTransaction();
        try	{
        	supply = (Supply) Json.fromJson(json, Supply.class);
        	Supply.update(supply);
        	Ebean.commitTransaction();
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		} finally {
			Ebean.endTransaction();
		}
        
        JsonNode jsonObject = Json.toJson(supply);
        return created(Util.jsonResponse(jsonObject, true));
	}

	public static Result delete(Integer id) {
		Supply supply = Supply.find(id);
        if (supply == null) {
			return notFound(Util.jsonResponse("Product not Found", false));
		}
        
        Ebean.beginTransaction();
        try	{
        	Supply.delete(supply);
        	Ebean.commitTransaction();
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		} finally {
			Ebean.endTransaction();
		}
        
        JsonNode jsonObject = Json.toJson(supply);
        return created(Util.jsonResponse(jsonObject, true));
	}
	
}
