package com.watermyplants.demo.controllers;

import com.watermyplants.demo.models.ErrorDetail;
import com.watermyplants.demo.models.Plant;
import com.watermyplants.demo.services.PlantService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController
{
    private static final Logger logger = LoggerFactory.getLogger(RolesController.class);

    @Autowired
    PlantService plantService;



    //-------------------------------------------------------------------------------------------
    //localhost:2019/plants/plants
    //gets all plants


    @ApiOperation(value = "Returns All Plants",
        response = Plant.class,
        responseContainer = "List")
    @ApiImplicitParams({

        @ApiImplicitParam(name = "page",
            dataType = "integr",
            paramType = "query",
            value = "Results page you want to retrieve (0..N)"), @ApiImplicitParam(name = "size",
        dataType = "integer",
        paramType = "query",
        value = "Number of records per page."), @ApiImplicitParam(name = "sort",
        allowMultiple = true,
        dataType = "string",
        paramType = "query",
        value = "Sorting criteria in the format: property(,asc|desc). "
            + "Default sort order is ascending. "
            + "Multiple sort criteria are supported.")})
    @GetMapping(value = "/plants",
        produces = {"application/json"})
    public ResponseEntity<?> listAllPlants(HttpServletRequest request)
    {
        logger.trace(request.getMethod()
            .toUpperCase() + " " + request.getRequestURI() + " accessed");
        List<Plant> allPlants = plantService.findAll();
        return new ResponseEntity<>(allPlants, HttpStatus.OK);
    }



    //-------------------------------------------------------------------------------------------
    //localhost:2019/plants/plant/{plantId}
    //gets a specific plant by its id

    @ApiOperation(value = "Gets a specific plant by the id number", response = Plant.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Plant Found", response = Plant.class),
        @ApiResponse(code = 404, message = "Plant Not Found", response = ErrorDetail.class)
    })
    @GetMapping(value = "/plant/{plantId}",
        produces = {"application/json"})
    public  ResponseEntity<?> getPlantById(HttpServletRequest request,
                                           @PathVariable Long plantId)
    {
        logger.trace(request.getMethod()
            .toUpperCase() + " " + request.getRequestURI() + " accessed");
        Plant p = plantService.findPlantById(plantId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }



    //----------------------------------------------------------------------------------------------------
    //localhost:2019/plants/userName/{userName}
    //gets plants by the username

    @ApiOperation(value = "Finds A Plant By The Username of whom owns it", response = Plant.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Plant Found", response = Plant.class),
        @ApiResponse(code = 404, message = "Plant Not Found", response = ErrorDetail.class)
    })
    @GetMapping(value = "/userName/{userName}",
        produces = {"application/json"})
    public ResponseEntity<?> findPlantByUserName(HttpServletRequest request,
                                                 @PathVariable String userName)
    {
        logger.trace(request.getMethod()
            .toUpperCase() + " " + request.getRequestURI() + " accessed");
        List<Plant> thePlants = plantService.findPlantByUserName(userName);
        return new ResponseEntity<>(thePlants, HttpStatus.OK);
    }

    //----------------------------------------------------------------------------------------------------
    //localhost:2019/plants/userId/{userId}
    //gets plants by the username

    @ApiOperation(value = "Finds A Plant By The userId of whom owns it", response = Plant.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Plant Found", response = Plant.class),
        @ApiResponse(code = 404, message = "Plant Not Found", response = ErrorDetail.class)
    })
    @GetMapping(value = "/userId/{userid}",
        produces = {"application/json"})
    public ResponseEntity<?> findPlantByUserId(HttpServletRequest request,
                                               @PathVariable long userid)
    {
        logger.trace(request.getMethod()
            .toUpperCase() + " " + request.getRequestURI() + " accessed");
        List<Plant> thePlants = plantService.findPlantByUserId(userid);
        return new ResponseEntity<>(thePlants, HttpStatus.OK);
    }



    //----------------------------------------------------------------------------------------------------
    //localhost:2019/plants/plant
    //POST adding a new plant

    @ApiOperation(value = "Creates A New Plant", notes = "Newly Created Plant id will be added to database",
        response = void.class)

    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Plant Successfully Added", response = void.class),
        @ApiResponse(code = 500, message = "Error Creating Plant", response = ErrorDetail.class)
    })
    @PostMapping(value = "/plant")
    public ResponseEntity<?> addNewPlant(HttpServletRequest request, @Valid
    @RequestBody
        Plant newPlant) throws URISyntaxException
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        newPlant = plantService.save(newPlant);

        ReturnPlant newPlant1 = new ReturnPlant(newPlant.getPlantid(), newPlant.getSpecies(), newPlant.getName(), newPlant.getLocation(), newPlant.getSchedule());


        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPlantURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{plantid}").buildAndExpand(newPlant.getPlantid()).toUri();
        responseHeaders.setLocation(newPlantURI);

        return new ResponseEntity<>(newPlant1, HttpStatus.CREATED);
    }


    //-----------------------------------------------------------------------------------------------
    //localhost:2019/plants/plant/{id}
    //DELETE

    @ApiOperation(value = "Delete A Plant Based on Id", response = void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Plant Successfully Deleted", response = void.class)
    })
    @DeleteMapping("/plant/{id}")
    public ResponseEntity<?> deletePlantById(HttpServletRequest request,
                                             @PathVariable
                                                 long id)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        plantService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
