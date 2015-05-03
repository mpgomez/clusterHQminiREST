package clusterhqminirest.exceptions;

import restx.http.HttpStatus;

/**
 * Created by dreamer on 03/05/15.
 */
public class MgsSvcUserDoesNotExistException extends MsgSvcException
{
    public MgsSvcUserDoesNotExistException()
    {
        super(HttpStatus.NOT_FOUND, "User does not exist");
    }

}
