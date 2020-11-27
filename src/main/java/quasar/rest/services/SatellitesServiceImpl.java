package quasar.rest.services;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import quasar.rest.Exceptions.MissingDataException;
import quasar.rest.Exceptions.NotFoundException;
import quasar.rest.dto.PositionDTO;
import quasar.rest.models.Response;
import quasar.rest.models.Satelites;
import quasar.rest.utils.Constants;
import quasar.rest.utils.SatelliteUtils;
import quasar.rest.utils.ValidateUtils;

import java.util.List;

@Service
public class SatellitesServiceImpl implements SatellitesService{

    @Autowired
    private SatelliteUtils satelliteUtils;

    @Autowired
    private ValidateUtils validateUtils;

    @Override
    public ResponseEntity<String> setSatellitesDataAndSearchSpaceship(List<Satelites> requestList, List<Satelites> satellitesDataInstance) throws NotFoundException {

        for(Satelites sat : requestList){
            saveSatelliteData(sat, satellitesDataInstance);
        }
        PositionDTO positionDTO = getFinalPosition(satellitesDataInstance);

        if(positionDTO.getError()){
            throw new NotFoundException();
        }

        Response response = new Response(positionDTO.getPosition(), getFinalMessage(satellitesDataInstance));
        return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.OK);
    }

    @Override
    public Boolean saveSatelliteData(Satelites satelite, List<Satelites> list) throws NotFoundException{

        try {
            //Create or modify satellites data
            if(validateUtils.validateSatelliteName(satelite)){
                satelliteUtils.modifyDataSatellites(list, satelite);
            }

        } catch (Exception e){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @Override
    public ResponseEntity<String> getSpaceshipData(List<Satelites> satellitesDataInstance){

        Response response;
        PositionDTO positionDTO;
        String messageResponse;

        try {

            messageResponse = getFinalMessage(satellitesDataInstance);
            positionDTO = getFinalPosition(satellitesDataInstance);

            if(positionDTO.getError() || messageResponse == null){
                throw new MissingDataException();
            }

            response = new Response(positionDTO.getPosition(), messageResponse);

        } catch (MissingDataException e){
            throw new MissingDataException();
        }

        return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.OK);
    }

    @Override
    public String getFinalMessage(List<Satelites> list) throws NotFoundException{

        if(validateUtils.validateMessageValid(list)){
            String[] msg1 = list.get(0).getMessage();
            String[] msg2 = list.get(1).getMessage();
            String[] msg3 = list.get(2).getMessage();

            return satelliteUtils.getMessage(msg1, msg2, msg3);
        }

        return null;
    }

    @Override
    public PositionDTO getFinalPosition(List<Satelites> list) throws NotFoundException{

        float r0 = 0, r1 = 0, r2 = 0;

        for(Satelites listAux : list){
            if(Constants.KENOBI_NAME.equals(listAux.getName())){
                r0 = listAux.getDistance();
            } else if (Constants.SKYWALKER_NAME.equals(listAux.getName())){
                r1 = listAux.getDistance();
            } else if (Constants.SATO_NAME.equals(listAux.getName())){
                r2 = listAux.getDistance();
            }
        }

        PositionDTO response = satelliteUtils.getLocations(Constants.X_KENOBI, Constants.Y_KENOBI,r0, Constants.X_SKYWALKER,
                            Constants.Y_SKYWALKER, r1, Constants.X_SATO, Constants.Y_SATO, r2);

        return response;
    }




}



