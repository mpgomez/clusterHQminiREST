package clusterhqminirest.service;

import clusterhqminirest.domain.Topic;
import clusterhqminirest.domain.User;
import clusterhqminirest.exceptions.MgsSvcUserDoesNotExistException;
import clusterhqminirest.exceptions.MsgSvcException;
import clusterhqminirest.exceptions.MsgSvcSubscriptionDoesNotExist;
import clusterhqminirest.exceptions.MsgSvcTopicDoesNotExist;

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
    public void unsubscribeToTopic(String topic, String user) throws MsgSvcException
    {
        if(topicsMap.containsKey(topic))
        {
            if( !topicsMap.get(topic).unsubscribeUser(user))
            {
                throw new MsgSvcSubscriptionDoesNotExist(user, topic);
            }
        }
        if(usersMap.containsKey(user))
        {
            if(!usersMap.get(user).removeTopic(topic))
            {
                throw new MsgSvcSubscriptionDoesNotExist(user, topic);
            }
        }
    }

    /**
     *
     * @param topic
     * @param user
     * @return
     */
    public String getMessage(String topic, String user) throws MsgSvcException
    {

        //Sanity check
        Topic currentTopic = topicsMap.get(topic);
        if(currentTopic == null)
        {
            throw new MsgSvcTopicDoesNotExist();
        }
        if(!currentTopic.getUsers().contains(user))
        {
            throw new MgsSvcUserDoesNotExistException();
        }

        //Get message
        User currentUser = usersMap.get(user);
        if(currentUser == null )
        {
            throw new MgsSvcUserDoesNotExistException();
        }
        if(!currentUser.isSuscribed(topic))
        {
            throw new MsgSvcSubscriptionDoesNotExist(user, topic);
        }
        return currentUser.popNextMessage(topic);
    }

    public void sendMessage (String topic, String message)
    {
        Topic currentTopic = this.getTopic(topic);
        if(currentTopic != null)
        {
            for( String user: currentTopic.getUsers())
            {
                this.getUser(user).addMessage(topic,message);
            }
        }
    }

}
