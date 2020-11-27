package quasar.rest.utils;

import org.springframework.stereotype.Component;
import quasar.rest.models.Satelites;

import java.util.List;

@Component
public class ValidateUtils {

    public Boolean validateSatellitesValid(List<Satelites> list){

        if(list.isEmpty() || validateSatelliteNames(list) || validateMessagesLength(list)){
           return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public Boolean messageIsValid(List<String> objects) {
        return objects.stream().allMatch(nef -> nef != null && !nef.trim().isEmpty());
    }

    public Boolean validateSatelliteNames(List<Satelites> list){

            for(Satelites sat : list){
                if(Constants.KENOBI_NAME.equals(sat.getName()) || Constants.SKYWALKER_NAME.equals(sat.getName())
                    || Constants.SATO_NAME.equals(sat.getName())){
                    return Boolean.TRUE;
                }
            }

        return Boolean.FALSE;
    }

    public Boolean validateSatelliteName(Satelites satellite){

            if(Constants.KENOBI_NAME.equals(satellite.getName()) || Constants.SKYWALKER_NAME.equals(satellite.getName())
                    || Constants.SATO_NAME.equals(satellite.getName())){
                return Boolean.TRUE;
            }

        return Boolean.FALSE;
    }

    public Boolean satelliteExistInList(List<Satelites> list, Satelites satellite){

        for(Satelites sat : list){
            if(sat.getName().equals(satellite.getName())){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public Boolean validateMessagesLength(List<Satelites> list){

        Integer len = list.get(0).getMessage().length;

        for(Satelites sat : list){
            if(sat.getMessage().length != len){
                return  Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public Boolean validateMessageValid(List<Satelites> list){
        for(Satelites sat: list){
            if(sat.getMessage() == null){
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
