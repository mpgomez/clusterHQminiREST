package clusterhqminirest.rest;

import clusterhqminirest.ServiceModule;
import clusterhqminirest.domain.Message;
import clusterhqminirest.service.MessageService;
import restx.RestxRequest;
import restx.RestxResponse;
import restx.WebException;
import restx.annotations.DELETE;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.security.PermitAll;

import java.io.IOException;

/**
 * Created by dreamer on 03/05/15.
 */
@Component
@RestxResource
public class MessageResource
{
    private final MessageService service;
    public MessageResource(ServiceModule localService)
    {
        this.service = localService.getRunningService();

    }

    @POST("/miniREST/{topic}/{username}")
    @PermitAll
    public String subscribeToTopic(String topic, String username)
    {
        service.subscribeToTopic(topic,username);
        return "Subscription succeeded.";
    }

    @GET("/miniREST/{topic}/{username}")
    @PermitAll
    public String getNextMessage(String topic, String username)
    {
        return service.getMessage(topic, username);
    }

    @DELETE("/miniREST/{topic}/{username}")
    @PermitAll
    public String unsubscribeToTopic(String topic, String username)
    {
        service.unsubscribeToTopic(topic, username);
        return "Unsubscribe succeeded.";
    }

    @POST("/miniREST/{topic}")
    @PermitAll
    public String publishMessage(String topic, Message msg)
    {
        service.sendMessage(topic,msg.getValue());
        return "Publish succeeded.";
    }

}