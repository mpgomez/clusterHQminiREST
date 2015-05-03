package clusterhqminirest.domain;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by dreamer on 29/04/15.
 */
public class User {
    private String userName = "";
    private Map<String,Queue<String>> messages = null;


    public  User() {
        this.setUserName(userName);
        messages = new HashMap<>();
    }

    public User(String user)
    {
        this.userName = user;
        messages = new HashMap<>();
    }

    public void addTopic( String topic )
    {
        //If the topic doesn't exist in the list, add it
        if(this.getMessages(topic) == null)
        {
            messages.put(topic, new LinkedList<String>() );
        }
    }

    public boolean removeTopic( String topic )
    {
        if(messages.containsKey(topic))
        {
            messages.remove(topic);
            return true;
        }
        else
        {
            return false;
        }

    }

    public boolean isSuscribed( String topic)
    {
        return messages.containsKey(topic);
    }



    private Queue<String> getMessages(String topic) {
        return this.messages.get(topic);
    }

    private Queue<String> getMessages(Topic topic) {
        return this.getMessages(topic.getTopicName());
    }

    public Queue<String> popMessages(String topic)
    {
        Queue<String> currentMessages = getMessages(topic);
        this.messages.clear();
        return currentMessages;
    }

    public Queue<String> popMessages(Topic topic)
    {
        return popMessages(topic.getTopicName());
    }

    public String popNextMessage(Topic topic)
    {
        return messages.get(topic).peek();
    }
    public String popNextMessage(String topic)
    {
        return messages.get(topic).poll();
    }

    public void addMessage(String topic, String message) {
        this.messages.get(topic).add(message);
    }

    public void addMessage(Topic topic, String message) {
        this.addMessage(topic.getTopicName(), message);
    }

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int hashCode() {
        return this.getUserName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final User other = (User) obj;
        if (other.getUserName().equals(this.getUserName()) )
            return true;
        else
            return false;
    }

}
