package clusterhqminirest.rest;

import clusterhqminirest.ServiceModule;
import clusterhqminirest.domain.Message;
import clusterhqminirest.service.MessageService;
import restx.RestxResponse;
import restx.annotations.DELETE;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;

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

    @POST("/miniREST/{topic}/{username}")
    public String subscribeToTopic(String topic, String username)
    {
        service.subscribeToTopic(topic,username);
        return "Subscription succeeded.";
    }

    @GET("/miniREST/{topic}/{username}")
    public String getNextMessage(String topic, String username)
    {
        return service.getMessage(topic, username);
    }

    @DELETE("/miniREST/{topic}/{username}")
    public String unsubscribeToTopic(String topic, String username)
    {
        service.unsubscribeToTopic(topic, username);
        return "";
    }

    @POST("/miniREST/{topic}/")
    public String subscrubeToTopic(String topic, Message msg)
    {
        service.sendMessage(topic,msg.getValue());
        return "";
    }

}