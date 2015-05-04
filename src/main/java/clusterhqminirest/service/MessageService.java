package clusterhqminirest.service;

import clusterhqminirest.domain.Topic;
import clusterhqminirest.domain.User;
import clusterhqminirest.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by dreamer on 29/04/15.
 */
public class MessageService {
    final Map<String, User> usersMap ;
    final Map<String, Topic> topicsMap ;
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public MessageService()
    {
        usersMap = new HashMap<>();
        topicsMap = new HashMap<>();
        logger.info("Instantiating MessageService");
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
     * Function used to subscribe an user to a certain topic
     * @param topic string containing the topic we want the user to be subscribed to
     * @param user string containing the name of the user
     */
    public void subscribeToTopic(String topic, String user)
    {
        //Add the user to the topic - create the topic if it doesn't exist
        if(topicsMap.containsKey(topic))
        {
            topicsMap.get(topic).addUser(user);
            logger.info("subscribeToTopic:Topic "+ topic + " already exists. Subscribing user "+user);
        }
        else
        {
            Topic newTopic =  new Topic(topic);
            newTopic.addUser(user);
            topicsMap.put(topic, newTopic);
            logger.info("subscribeToTopic:Creating topic " + topic + " and subscribing user " + user);

        }
        //Add the topic to the user - create the user if it doesn't
        if(usersMap.containsKey(user))
        {
            usersMap.get(user).addTopic(topic);
            logger.info("subscribeToTopic:User " + user + " already exists. Subscribing to topic " + topic);
        }
        else
        {
            User newUser = new User(user);
            newUser.addTopic(topic);
            usersMap.put(user, newUser);
            logger.info("subscribeToTopic:Creating user " + user + " and subscribing to topic " + topic);
        }
    }

    /**
     * Function used to unsubscribe an user to a certain topic
     * @param topic string containing the topic we want the user to be unsubscribed from
     * @param user string containing the name of the user
     * @throws MsgSvcException will be thrown if the user does not exist, or the topic
     *          does not exist, or if there is no subscription from the user to the given topic
     */
    public void unsubscribeToTopic(String topic, String user) throws MsgSvcException
    {
        if(topicsMap.containsKey(topic))
        {
            if( !topicsMap.get(topic).unsubscribeUser(user))
            {
                logger.error("unsubscribeToTopic:User "+ user+ " is not subscribed to the topic "+topic);
                throw new MsgSvcSubscriptionDoesNotExist();
            }
        }
        else
        {
            throw new MsgSvcTopicDoesNotExist();
        }
        if(usersMap.containsKey(user))
        {
            if(!usersMap.get(user).removeTopic(topic))
            {
                logger.error("unsubscribeToTopic:Topic "+ topic + " is not in the subscriptions list for user " + user);
                throw new MsgSvcSubscriptionDoesNotExist();
            }
        }
        else
        {
            throw new MgsSvcUserDoesNotExistException();
        }
    }

    /**
     * Function used to retrieve the next message in the queue about certain topic,
     * from the given user queue. Note that, once the next message is retrieved, it will be
     * removed from the queue, and will no longer be available
     * @param topic String containing the name of the topic we want the next message to be about
     * @param user String containing the name of the user we want the message from.
     * @return the next message in the queue (it will be removed from the queue and will no loger be accesible)
     * @throws MsgSvcException will be thrown if the user does not exist, or the topic
     *          does not exist, or if there is no subscription from the user to the given topic,
     *          or if the message queue is empty
     */
    public String getMessage(String topic, String user) throws MsgSvcException
    {
        String nextMessage;
        //Check topic
        Topic currentTopic = topicsMap.get(topic);
        if(currentTopic == null)
        {
            logger.error("getMessage:Topic " + topic + " does not exist");
            throw new MsgSvcTopicDoesNotExist();
        }
        //Check user
        User currentUser = usersMap.get(user);
        if(currentUser == null )
        {
            logger.info("getMessage:User " + user + " does not exist");
            throw new MgsSvcUserDoesNotExistException();
        }
        //Check subscription
        if(!currentTopic.getUsers().contains(user))
        {
            logger.error("getMessage:User " + user + " is not subscribed to topic " + topic);
            throw new MsgSvcSubscriptionDoesNotExist();
        }
        if(!currentUser.isSuscribed(topic))
        {
            logger.error("getMessage:User " + user + " is not subscribed to topic " + topic + " (validation II)");
            throw new MsgSvcSubscriptionDoesNotExist();
        }
        nextMessage = currentUser.popNextMessage(topic);
        if(nextMessage == null)
        {
            //TODO: this shouldn't be an exception: any return value of the family 200
            //is a success.
            logger.warn("getMessage:Messages queue for user " + user + " and topic "+ topic + " is empty");
            throw new MsgSvcMessageQueueEmpty();
        }
        return nextMessage;
    }

    public void sendMessage (String topic, String message)
    {
        Topic currentTopic = this.getTopic(topic);
        logger.info("sendMessage:Broadcasting messages for topic "+ topic);
        if(currentTopic != null)
        {
            for( String user: currentTopic.getUsers())
            {
                logger.info("sendMessage:Sending message to user "+ user);
                this.getUser(user).addMessage(topic,message);
            }
        }
        //TODO throw an exception if the topic doesn't exist - but the spec says 200 always, so do nothing
    }

}
