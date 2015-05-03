package clusterhqminirest.service;

import clusterhqminirest.domain.Topic;
import clusterhqminirest.domain.User;
import restx.factory.Component;

import java.util.*;

/**
 * Created by dreamer on 29/04/15.
 */
public class MessageService {
    final Map<String, User> usersMap ;
    final Map<String, Topic> topicsMap ;

    /**
     *
     */
    public MessageService()
    {
        usersMap = new HashMap<>();
        topicsMap = new HashMap<>();
        System.out.println("\n\n\n\n\n INSTANTIATING MESSAGE SERVICE \n\n\n\n\n");

    }

    /**
     *
     * @param userName
     * @return
     */
    public User getUser(String userName)
    {
        return usersMap.get(userName);
    }

    /**
     *
     * @param topicName
     * @return
     */
    public Topic getTopic(String topicName)
    {
        return topicsMap.get(topicName);
    }

    /**
     *
     * @param topic
     * @param user
     */
    public void subscribeToTopic(String topic, String user)
    {
        //Add the user to the topic - create the topic if it doesn't exist
        if(topicsMap.containsKey(topic))
        {
            topicsMap.get(topic).addUser(user);
        }
        else
        {
            Topic newTopic =  new Topic(topic);
            newTopic.addUser(user);
            topicsMap.put(topic,newTopic);

        }
        //Add the topic to the user - create the user if it doesn't
        if(usersMap.containsKey(user))
        {
            usersMap.get(user).addTopic(topic);
        }
        else
        {
            User newUser = new User(user);
            newUser.addTopic(topic);
            usersMap.put(user, newUser);
        }
    }

    //TODO throw excepction instead of using a return value?

    /**
     *
     * @param topic
     * @param user
     * @return
     */
    public int unsuscribeToTopic(String topic, String user)
    {
        if(topicsMap.containsKey(topic))
        {
            if( !topicsMap.get(topic).unsuscribeUser(user))
            {
                return -1;
            }
        }
        if(usersMap.containsKey(user))
        {
            if(!usersMap.get(user).removeTopic(topic))
            {
                return -2;
            }
        }
        return 0;
    }

    /**
     *
     * @param topic
     * @param user
     * @return
     */
    public String getMessage(String topic, String user)
    {

        //Sanity check
        Topic currentTopic = topicsMap.get(topic);
        if(currentTopic == null)
        {
            // TODO throw exceptionreturn -1;
        }
        if(!currentTopic.getUsers().contains(user))
        {
            // TODO throw exceptionreturn -2;
        }

        //Get message
        User currentUser = usersMap.get(user);
        if(currentUser == null )
        {
            // TODO throw exceptionreturn -3;
        }
        if(!currentUser.isSuscribed(topic))
        {
            // TODO throw exception return -4
        }
        return currentUser.popNextMessage(topic);

    }

}
