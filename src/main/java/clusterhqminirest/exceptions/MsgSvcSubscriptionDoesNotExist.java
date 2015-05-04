package clusterhqminirest.exceptions;

import restx.http.HttpStatus;

/**
 * Subtype of the message service exception that will be thrown when the user doesn't exist
 * Created by dreamer on 03/05/15.
 */
public class MsgSvcSubscriptionDoesNotExist extends MsgSvcException
{
    public MsgSvcSubscriptionDoesNotExist(String user, String topic)
    {
        super(HttpStatus.NOT_FOUND, "User "+ user + " not subscribed to " + topic);
    }

    public MsgSvcSubscriptionDoesNotExist()
    {
        super(HttpStatus.NOT_FOUND, "The subscription does not exist.");
    }

}
