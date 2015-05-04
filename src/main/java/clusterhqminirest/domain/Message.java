package clusterhqminirest.domain;

import javax.validation.constraints.NotNull;

/**
 * This class represent a message. It will be used to post a new message
 * Created by dreamer on 03/05/15.
 */
public class Message
{
    @NotNull
    String value;
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
}
