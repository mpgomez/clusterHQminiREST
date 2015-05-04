package clusterhqminirest.exceptions;

import restx.http.HttpStatus;

/**
 * Subtype of the message service exception that will be thrown when the queue message is empty.
 * Note that here we are setting a 204, but the error handling and dealing with HTTP codes in the
 * restx framework is a bit dodgy, so exceptions are being used here as a workaround to return the appropiate HTTP status
 * - Workaround works (ish; the message is lost), but it makes the tests fail, as it throws an exception. 204 is an error
 * from the family of the 200, meaning that everything was OK, so when that is inside an exception,
 * the framework complains

 * Created by dreamer on 03/05/15.
 */
public class MsgSvcMessageQueueEmpty extends MsgSvcException
{
    public MsgSvcMessageQueueEmpty()
    {
        super(HttpStatus.NO_CONTENT, "There are no messages available for this topic on this user.");
    }

    public MsgSvcMessageQueueEmpty(String topic, String user)
    {
        super(HttpStatus.NO_CONTENT, "The are no more messages left for the topic " + topic + " in the user " + user + " queue");
    }
}
