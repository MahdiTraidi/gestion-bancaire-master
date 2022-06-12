package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.model.CardTemplate;

import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("CardTemplate")
public interface CardTemplateApi {


    @PostMapping(value = APP_ROOT + "/card_template/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add a card template", notes = "this methode can add a card template", response = CardTemplate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Card template added "),
            @ApiResponse(code = 400, message = "Card template is invalid")
    })
    ResponseEntity<Object> save(@RequestBody CardTemplate cardTemplate);

    @GetMapping(value = APP_ROOT + "/card_template/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all card templates", notes = "This methode get all card templates ", responseContainer = "List<CardTemplate>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of CardTemplate")
    })
    List<CardTemplate> findAll();

    @DeleteMapping(APP_ROOT + "/card_template/delete/{idCardTemplate}")
    @ApiOperation(value = "Delete card template", notes = "This methode deletes a card template", response = CardTemplate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CardTemplate deleted "),
            @ApiResponse(code = 400, message = "CardTemplate not found")
    })
    ResponseEntity<Object> deleteCardTemplate(@PathVariable("idCardTemplate") long idCardTemplate);

    @GetMapping(APP_ROOT + "/card_template/find/{idCardTemplate}")
    @ApiOperation(value = "Find a card template", notes = "This methode retrieve a card template", response = CardTemplate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CardTemplate retrieved"),
            @ApiResponse(code = 400, message = "CardTemplate not found")
    })
    ResponseEntity<Object> findCardTemplate(@PathVariable("idCardTemplate") long idCardTemplate);
}
