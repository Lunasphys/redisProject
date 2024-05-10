package org.example;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.HostAndPort;

import java.util.HashSet;
import java.util.Set;

public class DirectoryApp {
    private final JedisCluster jedisCluster;

    public DirectoryApp(Set<HostAndPort> nodes) {
        this.jedisCluster = new JedisCluster(nodes);
    }

    public void addContact(String contactId, String name, String phone) {
        jedisCluster.hset("contact:" + contactId, "name", name);
        jedisCluster.hset("contact:" + contactId, "phone", phone);
    }

    public String getContact(String contactId) {
        return "Name: " + jedisCluster.hget("contact:" + contactId, "name") +
                ", Phone: " + jedisCluster.hget("contact:" + contactId, "phone");
    }

    public static void main(String[] args) {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        // Add your Redis cluster nodes here
        jedisClusterNodes.add(new HostAndPort("172.18.0.2", 6379));
        jedisClusterNodes.add(new HostAndPort("172.18.0.3", 6379));
        jedisClusterNodes.add(new HostAndPort("172.18.0.4", 6379));
        jedisClusterNodes.add(new HostAndPort("172.18.0.5", 6379));
        jedisClusterNodes.add(new HostAndPort("172.18.0.6", 6379));
        jedisClusterNodes.add(new HostAndPort("172.18.0.7", 6379));

        DirectoryApp app = new DirectoryApp(jedisClusterNodes);
        app.addContact("1", "John Doe", "1234567890");
        System.out.println(app.getContact("1"));
    }
}
