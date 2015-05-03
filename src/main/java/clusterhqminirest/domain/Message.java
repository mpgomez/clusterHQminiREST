package clusterhqminirest.domain;

import javax.validation.constraints.NotNull;

/**
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
