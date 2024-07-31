package servlets;

import java.nio.file.Path;
import java.util.*;

import configs.GenericConfig;
import configs.Graph;
import graph.Topic;
import graph.TopicManagerSingleton;
import server.RequestParser;
import views.HtmlGraphWriter;
import views.HtmlTableWriter;

import java.io.IOException;

public class ConfLoader implements Servlet {
    private final Path configFilesPath;
    private GenericConfig genericConfig;
    private final TopicManagerSingleton.TopicManager topicManager = TopicManagerSingleton.get();

    public ConfLoader(Path configFilesPath) {
        this.configFilesPath = configFilesPath;
        if (!this.configFilesPath.toFile().exists()) {
            this.configFilesPath.toFile().mkdir();
        }
    }

    @Override
    public void handle(RequestParser.RequestInfo ri, java.io.OutputStream toClient) throws IOException {
        if (ri.getContent().length > 0) {
            try {
                System.out.println("config file post request");
                byte[] fileContent = ri.getContent();
                String fileName = UUID.randomUUID().toString();
                Path filePath = this.configFilesPath.resolve(fileName);
                java.nio.file.Files.write(filePath, fileContent);
                System.out.println("File " + fileName + " saved");

                // Create a graph from the config file
                this.createConfig(filePath);
                System.out.println(topicManager.getTopics().size() + " topics created");
                System.out.println();
                System.out.println("Config created Successfully!");
                Graph graph = this.createGraph();
                System.out.println("Graph created Successfully!");

                // Generate the HTML file
                ArrayList<String> html = HtmlGraphWriter.getGraphHTML(graph);
                System.out.println("Html generated for graph");
                /*
                // initalize the table:
                Collection<Topic> tops = TopicManagerSingleton.get().getTopics();
                Map<String, String> topsMap = new HashMap<>();
                for (Topic top:tops) {
                    topsMap.put(top.name, "0.0");
                }

                topsMap.forEach((topicN, topicV) -> {
                    PublishHelper.sendPublishRequest(topicN, topicV);
                });
                */
                /*
                ArrayList<String> tableHtml = HtmlTableWriter.getTableHtml(topicMap);
                System.out.println("Html generated for table");
//                toClient.write("HTTP/1.1 200 OK\r\n".getBytes());
//                toClient.write("Content-Type: text/html\r\n".getBytes());
//                toClient.write("\r\n".getBytes());
                for (String line : tableHtml) {
                    toClient.write(line.getBytes());
                    toClient.write("\n".getBytes());
                }*/
                // Send the response
                toClient.write("HTTP/1.1 200 OK\r\n".getBytes());
                toClient.write("Content-Type: text/html\r\n".getBytes());
                toClient.write("\r\n".getBytes());
                for (String line : html) {
                    toClient.write(line.getBytes());
                    toClient.write("\n".getBytes());
                }

            } catch (Exception e) {
                toClient.write("HTTP/1.1 500 Internal Server Error\r\n".getBytes());
                toClient.write("Content-Type: text/html\r\n".getBytes());
                toClient.write("\r\n".getBytes());
                toClient.write("<html><body><h1>500 Internal Server Error</h1><p>".getBytes());
                toClient.write(e.getMessage().getBytes());
                toClient.write("</p></body></html>".getBytes());
            }
        } else {
            toClient.write("HTTP/1.1 400 Bad Request\r\n".getBytes());
            toClient.write("Content-Type: text/html\r\n".getBytes());
            toClient.write("\r\n".getBytes());
            toClient.write("<html><body><h1>400 Bad Request</h1><p>No content received in the request.</p></body></html>".getBytes());
        }
    }

    @Override
    public void close() throws IOException {
        System.out.println("Closing ConfLoader");
        if (this.genericConfig != null) {
            this.genericConfig.close();
        }
    }

    private void createConfig(Path filePath) {
        if (this.genericConfig != null) {
            this.genericConfig.close();
        }
        this.topicManager.clear();
        this.genericConfig = new GenericConfig();
        genericConfig.setConfFile(filePath.toString());
        genericConfig.create();
    }

    private Graph createGraph() {
        Graph graph = new Graph();
        graph.createFromTopics();
        return graph;
    }
}

