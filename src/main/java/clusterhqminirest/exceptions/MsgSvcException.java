package clusterhqminirest.exceptions;

import restx.WebException;
import restx.exceptions.ErrorCode;
import restx.exceptions.ErrorField;
import restx.http.HttpStatus;

/**
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
//    public MsgSvcException(String message)
//    {
//        super(message);
//    }
//    public MsgSvcException(Throwable cause)
//    {
//        super(cause);
//    }
//    public MsgSvcException(String message, Throwable cause)
//    {
//        super(message,cause);
//    }
//    public MsgSvcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
//    {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
//
//    public static class MsgSvcRules
//    {
//        @ErrorCode(code = "MSGSVC-001", description = "user must exist", status=HttpStatus.BAD_REQUEST)
//        public static enum UserRef {
//            @ErrorField("user key") USER
//        }
//        @ErrorCode(code = "MSGSVC-002", description = "user must exist")
//        public static enum TopicRef {
//            @ErrorField("topic key") TOPIC
//        }
//        @ErrorCode(code = "MSGSVC-003", description = "The user must be subscribed to the topic")
//        public static enum ValidSubscriptionRef {
//            @ErrorField("user key") USER,
//            @ErrorField("topic key") TOPIC
//        }
//        @ErrorCode(code = "MSGSVC-004", description = "There are no messages left")
//        public static enum NoMessagesLeftRef {
//            @ErrorField("user key") USER,
//            @ErrorField("topic key") TOPIC
//        }
//    }
}
