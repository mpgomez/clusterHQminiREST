package clusterhqminirest.exceptions;

import restx.http.HttpStatus;

/**
 * Created by dreamer on 03/05/15.
 */
public class MsgSvcTopicDoesNotExist extends MsgSvcException
{
    public MsgSvcTopicDoesNotExist()
    {
        super(HttpStatus.NOT_FOUND, "The subscription does not exist.");
    }

    public MsgSvcTopicDoesNotExist(String topic)
    {
        super(HttpStatus.NOT_FOUND, "The topic " + topic + " does not exist.");
    }
}
