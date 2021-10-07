package Storages;

public class Message {
    String messageBody;
    String topic;
    public Message(String message, String topic)
    {
        this.messageBody = message;
        this.topic = topic;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }


    public String getMessageBody() {
        return messageBody;
    }


    public String getTopic() { return topic;}
}

