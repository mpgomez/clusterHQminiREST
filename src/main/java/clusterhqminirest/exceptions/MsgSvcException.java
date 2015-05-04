package clusterhqminirest.exceptions;

import restx.WebException;
import restx.http.HttpStatus;

/**
 * Exceptions used in the Message Service.
 * Right now, it is extending WebException, so the REST framework
 * restx can handle them appropiatelly.
 * A possible improvement here would be to make the exceptions for the service
 * independent, and catch them in the REST layer, throwing the appropiate
 * WebException
 *
 * Created by dreamer on 03/05/15.
 */
public class MsgSvcException extends WebException
{

    public MsgSvcException(HttpStatus status, String message) {
        super(status, message);
    }

    @Override
    public String getContentType() {
        return "application/json;UTF-8";
    }

    @Override
    public String getContent() {
        return "{ \"message\": \"" + super.getContent().replace("\"", "\\\"") + "\"}";
    }
}
