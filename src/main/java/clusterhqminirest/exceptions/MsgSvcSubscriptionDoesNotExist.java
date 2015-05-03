package clusterhqminirest.exceptions;

import clusterhqminirest.domain.User;
import restx.http.HttpStatus;

/**
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
        super(HttpStatus.NOT_FOUND, "User not subscribed to topic");
    }

}
