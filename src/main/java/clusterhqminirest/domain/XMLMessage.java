package clusterhqminirest.domain;

/**
 * Created by dreamer on 03/05/15.
 */
public class XMLMessage {

    private String message;

    public  XMLMessage(String message)
    {
        setMessage(message);
    }
    public String getMessage() {
        return message;
    }

    public XMLMessage setMessage(final String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
