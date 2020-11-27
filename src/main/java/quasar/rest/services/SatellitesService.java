package quasar.rest.services;

import org.springframework.http.ResponseEntity;
import quasar.rest.Exceptions.NotFoundException;
import quasar.rest.dto.PositionDTO;
import quasar.rest.models.Satelites;

import java.util.List;

public interface SatellitesService {

    ResponseEntity<String> setSatellitesDataAndSearchSpaceship(List<Satelites> requestList, List<Satelites> satellitesDataInstance) throws NotFoundException;
    ResponseEntity<String> getSpaceshipData(List<Satelites> satellitesDataInstance);
    PositionDTO getFinalPosition(List<Satelites> list) throws NotFoundException;
    String getFinalMessage(List<Satelites> list) throws NotFoundException;
    Boolean saveSatelliteData(Satelites satelite, List<Satelites> list) throws NotFoundException;
}
