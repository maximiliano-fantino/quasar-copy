package quasar.rest.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quasar.rest.Exceptions.NotFoundException;
import quasar.rest.dto.PositionDTO;
import quasar.rest.models.Position;
import quasar.rest.models.Satelites;

import java.util.Arrays;
import java.util.List;


@Component
public class SatelliteUtils {

    @Autowired
    private ValidateUtils validateUtils;

    public PositionDTO getLocations(float x0 , float y0 , float r0 , float x1 , float y1 ,
                                    float r1 , float x2 , float y2 , float r2 ) throws NotFoundException {

        PositionDTO positionDTO = new PositionDTO();

        float a, dx, dy, d, h, rx, ry;
        float point2x, point2y;

        dx = x1 - x0;
        dy = y1 - y0;
        d = (float) Math.hypot(dx, dy);

        //if the distance of the points is greater than the sum of them, they never touch so there not intersection
        if (d > (r0 + r1)) {
            positionDTO.setError(Boolean.TRUE);
        }

        //if the distance of the points is lower than the longitude of them, they never touch so there not intersection
        if (d < Math.abs(r0-r1)) {
            positionDTO.setError(Boolean.TRUE);
        }

        /* 'point 2' is the point where the line through the circle
         * intersection points crosses the line between the circle
         * centers.
         */
        /* Determine the distance from point 0 to point 2. */
        a = (float) (((r0 * r0) - (r1 * r1) + (d * d)) / (2.0 * d));

        /* Determine the coordinates of point 2. */
        point2x = x0 + (dx * a / d);
        point2y = y0 + (dy * a / d);

        /* Determine the distance from point 2 to either of the
         * intersection points.
         */
        h = (float) Math.sqrt((r0 * r0) - (a * a));

        /* Now determine the offsets of the intersection points from
         * point 2.
         */
        rx = -dy * (h / d);
        ry = dx * (h / d);

        float intersectionPoint1x = point2x + rx;
        float intersectionPoint2x = point2x - rx;
        float intersectionPoint1y = point2y + ry;
        float intersectionPoint2y = point2y - ry;

        //first intersection
        dx = intersectionPoint1x - x2;
        dy = intersectionPoint1y - y2;
        float d1 = (float) Math.hypot(dy, dx);

        //second intersection
        dx = intersectionPoint2x - x2;
        dy = intersectionPoint2y - y2;
        float d2 = (float) Math.hypot(dy, dx);

        if (Math.abs(d1-r2) < Constants.EPSILON) {
            positionDTO.setPosition(new Position(intersectionPoint1x, intersectionPoint1y));
            return positionDTO;
        } else if (Math.abs(d2-r2) < Constants.EPSILON) {
            positionDTO.setPosition(new Position(intersectionPoint2x, intersectionPoint2y));
            return positionDTO;
        }

        positionDTO.setError(Boolean.TRUE);

        return positionDTO;
    }

    public String getMessage(String[] messagesKe, String[] messagesSw, String[] messagesSa) throws NotFoundException{

        String[] finalMessage = new String[messagesKe.length];

        try {
                for(int i = 0; i < finalMessage.length ; i++){

                    if(!messagesKe[i].isEmpty()){
                        finalMessage[i] = messagesKe[i];

                    } else if(!messagesSw[i].isEmpty()){
                        finalMessage[i] = messagesSw[i];

                    } else if(!messagesSa[i].isEmpty()){
                        finalMessage[i] = messagesSa[i];
                    }
                }
        } catch (Exception e){

        }

        if(!validateUtils.messageIsValid(Arrays.asList(finalMessage))){
            return null;
        }

        return String.join(Constants.DELIMITER, finalMessage);
    }


    public void modifyDataSatellites(List<Satelites> satelitesDataList, Satelites satelite){

        //if satelite list data isn't empty and the satellite is already
        if(!satelitesDataList.isEmpty() && validateUtils.satelliteExistInList(satelitesDataList, satelite)){
            for(Satelites sat : satelitesDataList){
                if(sat.getName().equals(satelite.getName())){
                    sat.setDistance(satelite.getDistance());
                    sat.setMessage(satelite.getMessage());
                }
            }
        } else {

            satelitesDataList.add(satelite);
        }
    }

}
