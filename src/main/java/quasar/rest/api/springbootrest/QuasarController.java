package quasar.rest.api.springbootrest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quasar.rest.Exceptions.NotFoundException;
import quasar.rest.models.Satelites;
import quasar.rest.models.SatelitesList;
import quasar.rest.models.ThreadSafeSatellites;
import quasar.rest.services.SatellitesService;
import quasar.rest.utils.Constants;

import java.lang.reflect.Type;
import java.util.List;

@RestController
public class QuasarController {

    @Autowired
    private SatellitesService satellitesService;
    //Thread safe Singleton
    private ThreadSafeSatellites satellitesDataInstance;


    @RequestMapping("/")
    private String home(){
        return "Home";
    }

    @PostMapping(path = "/topsecret", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLocationAndMessage(@RequestBody SatelitesList requestJson) throws NotFoundException {

        List<Satelites> satelitesList;
        try {
            JSONObject jsonObject = new JSONObject(requestJson);
            JSONArray jArray = jsonObject.getJSONArray(Constants.SATELLITES);
            Type listType = new TypeToken<List<Satelites>>() {}.getType();
            satelitesList = new Gson().fromJson(String.valueOf(jArray), listType);

        } catch (Exception e){
            throw new NotFoundException();
        }

        return satellitesService.setSatellitesDataAndSearchSpaceship(satelitesList, satellitesDataInstance.getInstance());
    }

    @PostMapping(path = "/topsecret_split/{satellite_name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void getLocationAndMessage(@RequestBody Satelites requestJson, @PathVariable("satellite_name") String  satellite_name) throws NotFoundException {

        Satelites satelite;
        try {
            JSONObject jsonObject = new JSONObject(requestJson);
            Type type = new TypeToken<Satelites>() {}.getType();
            satelite = new Gson().fromJson(String.valueOf(jsonObject), type);
            satelite.setName(satellite_name);
        } catch (Exception e){
            throw new NotFoundException();
        }

        satellitesService.saveSatelliteData(satelite, satellitesDataInstance.getInstance());
    }

    @GetMapping(path = "/topsecret_split", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDataSat() throws NotFoundException {

        return satellitesService.getSpaceshipData(satellitesDataInstance.getInstance());
    }

}
