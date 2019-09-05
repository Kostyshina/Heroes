package katsapov.heroes.data;

import katsapov.heroes.data.entitiy.Response;

public class ApiException extends RuntimeException {

    private Response response;

    ApiException(Response response, Throwable cause) {
        super(response.getMessage(), cause);
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
