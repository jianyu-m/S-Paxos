package lsr.paxos.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.Random;

import lsr.paxos.ReplicationException;
import lsr.paxos.client.Client;

public class MapClient {
    private Client client;
    static long total = 100;
    Random rnd = new Random(1212);
    public long getRandom() {
        return rnd.nextLong() % 10000;
    }
    public void run() throws IOException, ReplicationException {
        client = new Client();
        client.connect();

        int i = 0;
        Long key, value;
        while (i < total) {
            String line;
            key = getRandom();
            value = getRandom();
            if (i % 100 == 0) {
                System.out.println("sending " + i + " requests");
            }
            System.out.println("key "+key+" value "+value);
            MapServiceCommand command = new MapServiceCommand(key, value);
            byte[] response = client.execute(command.toByteArray());
//            ByteBuffer buffer = ByteBuffer.wrap(response);
//            Long previousValue = buffer.getLong();
//            System.out.println(String.format("Previous value for %d was %d", key, previousValue));
            i++;
        }
    }

    private static void instructions() {
        System.out.println("Provide key-value pair of integers to insert to hash map");
        System.out.println("<key> <value>");
    }

    public static void main(String[] args) throws IOException, ReplicationException {
//        instructions();
        MapClient client = new MapClient();
        total = Long.parseLong(args[0]);
        client.run();
    }
}
