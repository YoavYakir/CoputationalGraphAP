package configs;

import graph.Agent;
import graph.Message;
import graph.TopicManagerSingleton;

import java.util.HashMap;
import java.util.Map;

public class SumAgent implements Agent {

    private String name;
    private String[] subs;
    private String[] pubs;
    private final TopicManagerSingleton.TopicManager topicManager = TopicManagerSingleton.get();
    private Double x;
    private Double y;

    public SumAgent(String[] subs, String[] pubs) {
        this.subs = subs;
        this.pubs = pubs;
        this.subscribe();
        this.register();
        this.reset();
        this.name = "SumAgent";
    }

    private void subscribe() {
        this.topicManager.getTopic(this.subs[0]).subscribe(this);
        this.topicManager.getTopic(this.subs[1]).subscribe(this);
    }

    private void register() {
        this.topicManager.getTopic(this.pubs[0]).addPublisher(this);
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void reset() {
        this.x = 0.0;
        this.y = 0.0;
    }

    @Override
    public void callback(String topic, Message msg) {
        if (Double.isNaN(msg.asDouble)) {
            return;
        }
        if (topic.equals(this.subs[0])) {
            this.x = msg.asDouble;
        } else if (topic.equals(this.subs[1])) {
            this.y = msg.asDouble;
        }
        if (this.x != 0.0 && this.y != 0.0) {
            this.topicManager.getTopic(this.pubs[0]).publish(new Message(this.x + this.y));
            this.reset();
        }
    }


    @Override
    public void close() {
        for (String sub : this.subs) {
            this.topicManager.getTopic(sub).unsubscribe(this);
        }
        for (String pub : this.pubs) {
            this.topicManager.getTopic(pub).removePublisher(this);
        }
    }
}