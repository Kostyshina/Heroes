package katsapov.heroes.data.network;

public class ApiException extends RuntimeException {

    private Response response;

    public ApiException(Response response, Throwable cause) {
        super(response.getMessage(), cause);
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
