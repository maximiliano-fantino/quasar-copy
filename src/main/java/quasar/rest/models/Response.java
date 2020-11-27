package quasar.rest.models;

public class Response {

    private Position position;
    private String message;

    public Response(Position position, String message) {
        this.position = position;
        this.message = message;
    }

}
