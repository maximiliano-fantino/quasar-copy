package quasar.rest.dto;

import quasar.rest.models.Position;

public class PositionDTO {

    private Position position;
    private Boolean error = Boolean.FALSE;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
