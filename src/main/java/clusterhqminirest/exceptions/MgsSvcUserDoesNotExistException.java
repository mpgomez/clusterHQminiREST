package clusterhqminirest.exceptions;

import restx.http.HttpStatus;

/**
 * Subtype of the message service exception that will be thrown when the user doesn't exist
 * Created by dreamer on 03/05/15.
 */
public class MgsSvcUserDoesNotExistException extends MsgSvcException
{
    public MgsSvcUserDoesNotExistException()
    {
        super(HttpStatus.NOT_FOUND, "The subscription does not exist.");
    }

    public MgsSvcUserDoesNotExistException(String user)
    {
        super(HttpStatus.NOT_FOUND, "The user" + user + " does not exist.");
    }

}
