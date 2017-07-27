package controllers;

import java.util.Iterator;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Product;
import models.Sale;
import models.Supply;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.Util;

@Security.Authenticated(Secured.class)
public class SaleCtl extends Controller {
	private static final Form<Sale> saleForm = Form.form(Sale.class);

	public static Result list() {
		return ok(Json.toJson(Sale.findAll()));
	}
	
	public static Result get(Integer id) {
		Sale sale = Sale.find(id);
		if (sale == null) {
			return notFound(Util.jsonResponse("Sale not Found", false));
		}

        JsonNode jsonObject = Json.toJson(sale);
		return ok(Util.jsonResponse(jsonObject, true));
	}
	
	public static Result add() {
    	JsonNode json = request().body().asJson();
        if (json == null){
            return badRequest(Util.jsonResponse("Expecting Json data", false));
        }
		Form<Sale> boundForm = saleForm.bind(json);
		if (boundForm.hasErrors()) {
			ObjectNode response = Util.jsonResponse("Check this field(s):", false);
	        response.put("data", boundForm.errorsAsJson());
			return badRequest(response);
		}

		Sale sale;
		Ebean.beginTransaction();
        try	{
        	sale = (Sale) Json.fromJson(json, Sale.class);
        	
        	for (Iterator<Supply> iterator = sale.getProduct().getSupplies().iterator(); iterator.hasNext();) {
				Supply supply = (Supply) iterator.next();
				supply.setStock( supply.getStock() - sale.getAmount() );
				Supply.update(supply);
			}

        	Sale.save(sale);
        	Ebean.commitTransaction();
        } catch (Exception e) {
			return badRequest(Util.jsonResponse(e.getMessage(), false));
		} finally {
			Ebean.endTransaction();
		}
        
        JsonNode jsonObject = Json.toJson(sale);
        return created(Util.jsonResponse(jsonObject, true));
	}

}
