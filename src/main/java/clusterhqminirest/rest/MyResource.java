package clusterhqminirest.rest;

import clusterhqminirest.AppModule;
import clusterhqminirest.ServiceModule;
import clusterhqminirest.service.MessageService;
import org.joda.time.DateTime;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.factory.Factory;
import restx.security.RestxSession;
import restx.security.RolesAllowed;

import javax.inject.Named;
import javax.xml.ws.Service;

/**
 * Created by dreamer on 03/05/15.
 */
@Component
@RestxResource
public class MyResource
{
    private final MessageService service;
    public MyResource(ServiceModule localService)
    {
        this.service = localService.getRunningService();

    }

    @GET("/prueba1")
    public String prueba1()
    {
        if(service.getUser("user2")!=null)
        {
            return "user2 exists";
        }
        else
        {
            return "user2 doesn't exist";
        }
    }

    @GET("/createUser")
    public String createUser()
    {
        service.subscribeToTopic("topic1","user2");
        return "User 2 createrd";
    }

}