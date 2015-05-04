package test.service;

import clusterhqminirest.domain.Topic;
import clusterhqminirest.domain.User;
import clusterhqminirest.exceptions.*;
import clusterhqminirest.service.MessageService;
import junit.framework.TestCase;

/**
 * Created by dreamer on 04/05/15.
 */
public class MessageServiceTest extends TestCase
{
    private MessageService service;
    private String topic1 = "cats";
    private String topic2 = "flags";
    private String user1 = "Sarge";
    private String user2 = "Donut";
    private String user3 = "Tucker";
    private String topic1_message1 = "I want a kitty";
    private String topic1_message2 = "The reds got a kitty";
    private String topic2_message1= "We got their flag";
    private String topic2_message2 = "We lost the flag again";
    private String topic1_message3 = "I like cats!";


    /**
     * Helper function to setup a service with some data
     * to the test it. This is how the service will be informed:

         Topic 1:
            - user 1 : topic1_message1, topic1_message2
            - user 2 :                , topic1_message2
         Topic 2:
            - user1 : topic2_message1, topic2_message2
            - user2 : topic2_message1, topic2_message2
            - user3 :                , topic2_message2
     */
    private void setupService()
    {
        service = new MessageService();
        service.subscribeToTopic( topic1, user1);
        service.subscribeToTopic( topic2, user1);
        service.subscribeToTopic( topic2, user2);
        service.sendMessage(topic1, topic1_message1);
        service.sendMessage(topic2, topic2_message1);
        service.subscribeToTopic(topic1, user2);
        service.subscribeToTopic(topic2, user3);
        service.sendMessage(topic1, topic1_message2);
        service.sendMessage(topic2, topic2_message2);
    }

    private void setupService( MessageService srv)
    {
        srv.subscribeToTopic( topic1, user1);
        srv.subscribeToTopic( topic2, user1);
        srv.subscribeToTopic( topic2, user2);
        srv.sendMessage(topic1, topic1_message1);
        srv.sendMessage(topic2, topic2_message1);
        srv.subscribeToTopic(topic1, user2);
        srv.subscribeToTopic(topic1, user3);
        srv.sendMessage(topic1, topic1_message2);
        srv.sendMessage(topic2, topic2_message2);
    }


    public void testGetUser_positive() throws Exception
    {
        setupService();
        User expectedUser = new User(user1);
        assertEquals(expectedUser, service.getUser(user1));
        expectedUser = new User(user3);
        assertEquals(expectedUser, service.getUser(user3));
    }

    public void testGetUser_negative() throws Exception
    {
        setupService();
        assertNull(service.getUser("Caboose"));
    }

    public void testGetTopic_positive() throws Exception
    {
        setupService();
        Topic expectedTopic = new Topic(topic1);
        assertEquals(expectedTopic, service.getTopic(topic1));
        expectedTopic = new Topic(topic2);
        assertEquals(expectedTopic, service.getTopic(topic2));
    }

    public void testSubscribeToTopic() throws Exception
    {
        setupService();
        assertNull(service.getTopic("Pink"));
    }

    public void testUnsubscribeToTopic_positive() throws Exception
    {
        MessageService srv = new MessageService();
        setupService(srv);
        boolean exceptionThrown = false;
        try{
            srv.unsubscribeToTopic(topic1, user1);
        }
        catch (MsgSvcException ex)
        {
            assertTrue("Unexpected exception: " + ex.getClass(), false);
        }
        //Now we check that if we try to unsubscribe the user again, we get the error thrown
        try
        {
            srv.unsubscribeToTopic(topic1, user1);
        } catch (MsgSvcException ex)
        {
            assertTrue(ex instanceof MsgSvcSubscriptionDoesNotExist);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    public void testUnsubscribeToTopic_negative_noTopic() throws Exception
    {
        setupService();
        boolean exceptionThrown = false;
        try
        {
            service.unsubscribeToTopic("Pink", user1);
        }
        catch (MsgSvcException ex)
        {
            assertTrue("Got exception type " + ex.getClass(), ex instanceof MsgSvcTopicDoesNotExist);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    public void testUnsubscribeToTopic_negative_noUser() throws Exception
    {
        setupService();
        boolean exceptionThrown = false;
        try
        {
            service.unsubscribeToTopic(topic1, "Caboose");
        }
        catch (MsgSvcException ex)
        {
            //TODO fix that
            assertTrue("Got exception type " + ex.getClass(),ex instanceof MsgSvcSubscriptionDoesNotExist);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    public void testUnsubscribeToTopic_negative_noSubscription() throws Exception
    {
        setupService();
        boolean exceptionThrown = false;
        try
        {
            service.unsubscribeToTopic(topic1, user3);
        } catch (MsgSvcException ex)
        {
            assertTrue("Got exception type " + ex.getClass(), ex instanceof MsgSvcSubscriptionDoesNotExist);
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    public void testGetMessage_negative_noTopic() throws Exception
    {
        setupService();
        boolean exceptionThrown = false;
        try
        {
            service.getMessage("Pink", user1);
        }
        catch (MsgSvcException ex)
        {
            assertTrue("Got exception type " + ex.getClass(), ex instanceof MsgSvcTopicDoesNotExist);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    public void testGetMessage_negative_noUser() throws Exception
    {
        setupService();
        boolean exceptionThrown = false;
        try
        {
            service.getMessage(topic1, "Caboose");
        }
        catch (MsgSvcException ex)
        {
            assertTrue("Got exception type " + ex.getClass(),ex instanceof MgsSvcUserDoesNotExistException);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    public void testGetMessage_negative_noSubscription() throws Exception
    {
        setupService();
        boolean exceptionThrown = false;
        try
        {
            service.getMessage(topic1, user3);
        } catch (MsgSvcException ex)
        {
            assertTrue("Got exception type " + ex.getClass(), ex instanceof MsgSvcSubscriptionDoesNotExist);
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    public void testSendMessage_negative_noTopic() throws Exception
    {
        setupService();
        boolean exceptionThrown = false;
        try
        {
            service.sendMessage("Pink", "This message won't go anywhere");
        }
        catch (MsgSvcException ex)
        {
            exceptionThrown = true;
        }
        assertFalse(exceptionThrown);
    }


    public void testGetSendMessage_positive() throws Exception
    {
        setupService();
        boolean exceptionThrown = false;
        try
        {
            service.sendMessage(topic1, topic1_message3 );
            assertEquals(service.getMessage(topic1, user1), topic1_message1);
            assertEquals(service.getMessage(topic1, user1), topic1_message2);
            assertEquals(service.getMessage(topic1, user1), topic1_message3);

        }
        catch (MsgSvcException ex)
        {
            exceptionThrown = true;
        }
        assertFalse(exceptionThrown);
        exceptionThrown = false;
        try
        {
            service.getMessage(topic1, user1);
        }
        catch (MsgSvcException ex)
        {
            exceptionThrown = true;
            assertTrue("Got exception type " + ex.getClass(),ex instanceof MsgSvcMessageQueueEmpty);
        }

    }

}