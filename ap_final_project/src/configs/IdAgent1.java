package configs;

import graph.Agent;
import graph.Message;
import graph.TopicManagerSingleton;

public class IdAgent1 implements Agent {

    private final String name;
    private final String subs;
    private final String pubs;
    private final TopicManagerSingleton.TopicManager topicManager = TopicManagerSingleton.get();
    private Double x;

    public IdAgent1(String[] subs, String[] pubs) {
        this.subs = subs[0];
        this.pubs = pubs[0];
        this.subscribe();
        this.register();
        this.reset();
        this.name = "IdAgent1";
    }

    private void subscribe() {
        this.topicManager.getTopic(this.subs).subscribe(this);
    }

    private void register() {
        this.topicManager.getTopic(this.pubs).addPublisher(this);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void reset() {
        this.x = 0.0;
    }

    @Override
    public void callback(String topic, Message msg) {
        if (Double.isNaN(msg.asDouble)) {
            return;
        }
        this.x = msg.asDouble;
        this.topicManager.getTopic(this.pubs).publish(new Message(this.x));
        this.reset();
    }

    @Override
    public void close() {
        this.topicManager.getTopic(this.subs).unsubscribe(this);
        this.topicManager.getTopic(this.pubs).removePublisher(this);
    }
}
