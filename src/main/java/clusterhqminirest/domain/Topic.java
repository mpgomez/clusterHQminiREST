package clusterhqminirest.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a topic.
 * It also contains a list of user subscribed to it,
 * and methods to subscribe/unsubscribe a user from the topic
 * Created by dreamer on 29/04/15.
 */
public class Topic
{
    private String topicName = "";
    private Set<String> users = null;

    public Topic(String topicName)
    {
        setTopicName(topicName);
        users = new HashSet<>();
    }

    public Topic()
    {
        users = new HashSet<>();
    }

    public void addUser(String user)
    {
        users.add(user);
    }

    public void addUser(User user)
    {
        users.add(user.getUserName());
    }

    public boolean unsubscribeUser(String user)
    {
        return users.remove(user);
    }

    public boolean unsubscribeUser(User user)
    {
        return unsubscribeUser(user.getUserName());
    }


    public String getTopicName()
    {
        return this.topicName;
    }

    private void setTopicName(String topicName)
    {
        this.topicName = topicName;
    }

    public Set<String> getUsers()
    {
        return this.users;
    }

    @Override
    public int hashCode()
    {
        return this.getTopicName().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Topic other = (Topic) obj;
        if (other.getTopicName().equals(this.getTopicName()))
            return true;
        else
            return false;
    }

}
