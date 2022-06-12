package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("creditSimilateur")
public interface CreditSimilateurApi {

    @GetMapping(value = APP_ROOT + "/creditsimilateur/vehiclecreditsimilateur", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Simulate vehicle credit", notes = "Simulate vehicle credit ", responseContainer = "Map<Integer, Double>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Map of repayments / Void list")
    })
    Map<Integer, Double> vehicleCredit(@RequestParam("vehicleAmout") double vehicleAmout, @RequestParam("vehicleFiscalPower") Integer vehicleFiscalPower, @RequestParam("selfFinancing") double selfFinancing, @RequestParam("repaymentPeriod") Integer repaymentPeriod);

    @GetMapping(value = APP_ROOT + "/creditsimilateur/vehiclecreditsimilateurforadmin", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Simulate vehicle credit", notes = "Similate vehicle credit ", responseContainer = "Map<Integer, Double>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Map of repayments / Void list")
    })
    Map<Integer, Double> vehicleCredit(@RequestParam("vehicleAmout") double vehicleAmout,@RequestParam("vehicleFiscalPower") Integer vehicleFiscalPower,@RequestParam("selfFinancing") double selfFinancing,@RequestParam("repaymentPeriod") Integer repaymentPeriod,@RequestParam("rate") double rate);


    @GetMapping(value = APP_ROOT + "/creditsimilateur/personalcreditsimilateur", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Simulate personal credit", notes = "Simulate personal credit ", responseContainer = "Map<Integer, Double>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Map of repayments / Void list")
    })
    Map<Integer, Double> personalCredit(@RequestParam("creditAmout") double creditAmout,@RequestParam("repaymentPeriod") Integer repaymentPeriod);


    @GetMapping(value = APP_ROOT + "/creditsimilateur/personalcreditsimilateurforadmin", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Simulate personal credit", notes = "Simulate personal Credit ", responseContainer = "Map<Integer, Double>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Map of repayments / Void list")
    })
    Map<Integer, Double> personalCredit(@RequestParam("creditAmout") double creditAmout,@RequestParam("repaymentPeriod") Integer repaymentPeriod,@RequestParam("rate") double rate);

    @GetMapping(value = APP_ROOT + "/creditsimilateur/bi/useroverview/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "User Score Over View to validate credit", notes = "User Score Over View to validate credit ", responseContainer = "long")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Map of details / Void list")
    })
    Map<String, Integer> getUserUserOverView(@PathVariable("id") Integer id);

}
