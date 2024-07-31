package views;

import configs.Graph;
import configs.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HtmlGraphWriterCyto {

    public static String removeLastCommaAndNewline(String str) {
        str = str.trim(); // Remove leading and trailing whitespace
        if (str.endsWith(",\n")) {
            str = str.substring(0, str.length() - 2); // Remove the last comma and newline
        } else if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1); // Remove the last comma if there's no newline
        } else if (str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1); // Remove the last newline if there's no comma
        }
        return str;
    }

    public static ArrayList<String> getGraphHTML(Graph graph) throws IOException {
        String filePath = "html_files/graph.html";
        ArrayList<String> html = new ArrayList<>();
        ArrayList<String> nodes = new ArrayList<>();
        ArrayList<String> edges = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        try {
            for (Node node : graph) {
                String nodeName = node.getName().substring(1);
//                String nodeFormatted = "{\n data: { id: '" + nodeName + "', label: '" + nodeName + "' }\n},\n";
                String nodeFormatted = "{data: { id: '" + nodeName + "', label: '" + nodeName + "' }},";
//                String nodeFormatted = "{\n data: { id: '" + nodeName + "' }\n },\n";
                sb.append(nodeFormatted);
                html.add(nodeFormatted);

            }

            for (Node node : graph) {
                String fromNodeName = node.getName().substring(1);
                for (Node edge : node.getEdges()) {
                    String toNodeName = edge.getName().substring(1); // Remove the initial 'T' or 'A'
                    String edgeFormatted = "{data: {id: '" + fromNodeName +"-"+ toNodeName + "', source: '" + fromNodeName + "', target: '" + toNodeName + "' }},";
//                    String edgeFormatted = "{\ndata: {id: '" + fromNodeName +"-"+ toNodeName + "', source: '" + fromNodeName + "', target: '" + toNodeName + "' }\n},\n";
                    sb.append(edgeFormatted);
                    html.add(edgeFormatted);
                }
            }

            String elementsJs = sb.toString();
            String elementsJsClean = removeLastCommaAndNewline(elementsJs);
            String htmlContent = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            htmlContent = htmlContent.replace("// Nodes and edges will be inserted here by Java code", elementsJsClean);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(htmlContent);
            } catch (Exception e){
                System.out.println(String.format("Couldn't write to file: '%s'",filePath));
                e.printStackTrace();
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading the template HTML file", e);
        }
        return html;
    }
}
