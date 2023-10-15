package com.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Installer {
    public static void createTopics() throws InterruptedException, ExecutionException {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try(AdminClient adminClient = AdminClient.create(props))
        {
            NewTopic newTopic = new NewTopic("my-input-topic", 1, (short) 1);

            if (!adminClient.listTopics().names().get().contains("my-input-topic")) {
                adminClient.createTopics(Collections.singleton(newTopic));
                System.out.println("Topic created successfully.");
            } else {
                System.out.println("Topic already exists.");
            }
        }
    }
}