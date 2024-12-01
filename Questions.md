Assignment Overview
Objective: Practice creating Kafka topics, working with partitions, consumer groups, manual assignments, and setting up a dead letter queue.
Requirements: Kafka, Zookeeper (if applicable), and a programming language of choice (e.g., Python, Java, or CLI).
Tasks
Task 1: Create Topics

1. Create two Kafka topics:
   practice-topic with 3 partitions.
   `./kafka-topics.sh --create --topic practice-topic --bootstrap-server broker:29092 --partitions 3`

   dead-letter-topic with 1 partition for handling failed messages.

   `./kafka-topics.sh --create --topic dead-letter-topic --bootstrap-server broker:29092 --partitions 1`

2. Verify the topics using the Kafka CLI.
   Deliverables:
   Commands used to create topics.
   Output from the [kafka-topics.sh](http://kafka-topics.sh/) --describe command.

```bash
/opt/kafka/bin $ ./kafka-topics.sh --create --topic practice-topic --bootstrap-server broker:29092 --partitions 3
Created topic practice-topic.
/opt/kafka/bin $ ./kafka-topics.sh --create --topic dead-letter-topic --bootstrap-server broker:29092 --partitions 1
Created topic dead-letter-topic.

/opt/kafka/bin $ ./kafka-topics.sh --describe --topic dead-letter-topic --bootstrap-server broker:29092
Topic: dead-letter-topic        TopicId: NXsv9yJkTOeFMWoQWN7VEg PartitionCount: 1       ReplicationFactor: 1    Configs: 
        Topic: dead-letter-topic        Partition: 0    Leader: 1       Replicas: 1     Isr: 1  Elr:    LastKnownElr: 

/opt/kafka/bin $ ./kafka-topics.sh --describe --topic practice-topic --bootstrap-server broker:29092
Topic: practice-topic   TopicId: 4gHcFw2iTiKxWGNH6ZehkA PartitionCount: 3       ReplicationFactor: 1    Configs: 
        Topic: practice-topic   Partition: 0    Leader: 1       Replicas: 1     Isr: 1  Elr:    LastKnownElr: 
        Topic: practice-topic   Partition: 1    Leader: 1       Replicas: 1     Isr: 1  Elr:    LastKnownElr: 
        Topic: practice-topic   Partition: 2    Leader: 1       Replicas: 1     Isr: 1  Elr:    LastKnownElr: 

```

Task 2: Produce Messages

1. Write a producer script or use the CLI to send the following messages to practice-topic:
   Key-value pairs like:
   key1:Message for Partition 1
   key2:Message for Partition 2
   key3:Message for Partition 3
   Use keys to control partition assignment.

```java
@Service
public class MessageProducer {

    private final String TOPIC = "practice-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String[] messages) {

        for(String message: messages) {
            String key = message.split(":")[0];
            String value = message.split(":")[1];
            int partition = Integer.parseInt(String.valueOf(key.charAt(key.length() - 1))) - 1;
            this.kafkaTemplate.send(TOPIC, partition, key, message);
        }
    }
}
```

1. Observe which partition each message is sent to.
   Deliverables:
   Producer code or CLI commands.
   Confirmation of messages sent to respective partitions.
   Task 3: Consume Messages Using Consumer Groups

```bash

```

1. Create a consumer group named group-A with 3 consumers.
2. Verify that each consumer is assigned one partition.
3. Add another consumer to group-A and observe the rebalance. Document how partitions are reassigned.
4. Create another consumer group group-B with 2 consumers and observe how partitions are distributed.
   Deliverables:
   Commands or scripts to create consumers.
   Partition assignment before and after rebalance for both groups.
   Task 4: Manual Partition Assignment

```java

```

1. Write a script to manually assign a single consumer to read from Partitions 0 and 1 of practice-topic.
2. Produce new messages to all partitions and confirm that only messages from Partitions 0 and 1 are consumed.
   Deliverables:
   Code or commands for manual assignment.
   Logs showing messages consumed only from specified partitions.
   Task 5: Simulate and Handle Processing Failures
3. Create a consumer script that simulates message processing. Introduce a failure for messages containing the word "error".
4. Implement logic to send failed messages to the dead-letter-topic.
   Deliverables:
   Consumer code with error handling.
   Messages observed in the dead-letter-topic.
   Task 6: Dead Letter Queue Consumer
5. Write a script to consume messages from the dead-letter-topic.
6. Print each message along with metadata (e.g., timestamp, original partition).
   Deliverables:
   Code or commands to consume from the dead-letter queue.
   Logs showing messages with metadata.
   Task 7: Monitoring and Troubleshooting
7. Use the [kafka-consumer-groups.sh](http://kafka-consumer-groups.sh/) command to describe the consumer groups (group-A and group-B).
8. Document the lag for each partition and discuss its significance.
   Deliverables:
   Commands and output showing group details.
   Explanation of partition lag.
   Bonus Task: Custom Partitioning
9. Implement a custom partitioning logic in a producer:
   Messages with keys starting with "A" go to Partition 0.
   Keys starting with "B" go to Partition 1.
   All others go to Partition 2.
10. Verify partition assignment by consuming the messages.