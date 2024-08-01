# Java HTTP Server with Computational Graph Visualization

## Project Overview

This project implements a Java-based HTTP server that allows users to upload configurations for agents and the topics they subscribe to and allowed to publish to. The server provides a GUI that visualizes the relationships between agents and topics, representing them with different shapes. Additionally, the application supports publishing messages to topics, where agents subscribed to those topics can receive the messages, perform calculations, and publish results to their subscribed topics.
Once the desired agent-topic calculation was completed, it is possible to download the results into a csv file.

## Features

- **Agent-Topic Configuration**: Upload configurations to define agents and the topics they subscribe to and publish.
- **Graph Visualization**: A dynamic graphical UI that displays agents and topics with distinct shapes (e.g., rectangular for topics and circular for agents).
- **Message Publishing and Processing**:
    - Publish messages to specific topics.
    - Agents subscribed to these topics can receive messages, perform calculations, and publish the results.
- **Topic Monitoring**: A UI table displaying topics and the latest messages published to them.
- **Export data**: A download button that exports a csv of the current graph.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A web browser for accessing the UI
- An IDE such as [Eclipse](https://www.eclipse.org/downloads/) or [IntelliJ IDEA](https://www.jetbrains.com/idea/)

### Installation and Running

1. **Clone the Repository**

   ```bash
   git clone https://github.com/YoavYakir/CoputationalGraphAP.git

2. **Open the Project in an IDE**

   Open the project in your preferred IDE:
- **Eclipse**:
    - Download and install Eclipse from [here](https://www.eclipse.org/downloads/).
    - Open Eclipse and go to `File > Open Projects from File System`.
    - Browse to the `ap_final_project` directory and click `Finish` to import the project.
    - Navigate to the `src` directory and locate the `Main.java` file.
    - Right-click on `Main.java` and select `Run As > Java Application`.

- **IntelliJ IDEA**:
    - Download and install IntelliJ IDEA from [here](https://www.jetbrains.com/idea/).
    - Open IntelliJ IDEA and select `Open` from the welcome screen.
    - Navigate to the `ap_final_project` directory and open it.
    - IntelliJ IDEA will configure the project.
    - Locate the `Main.java` file in the `src` directory.
    - Right-click on `Main.java` and select `Run 'Main'`.


### Configuration

Upload your agent and topic configuration files via the provided API endpoint. Ensure your configuration follows the specified format, detailing each agent and the topics they subscribe to and publish. Below is an example of a configuration file:

**Example Configuration (simple.conf):**

```plaintext
configs.PlusAgent
A,B
C
configs.IncAgent
C
D
```
For more advanced configs check the `ap_final_project/example_conf_files` directory
### Usage

1. **Access the UI**

   Open your web browser and navigate to `http://localhost:8080/app/index.html` to access the graphical interface.

2. **Upload Configuration**

   Upload a configuration file through the "Deploy Configuration" section. Once the file is uploaded, the UI will display a graph showing the relationships between agents and topics.

3. **Publish Messages**

   Use the provided interface to publish messages to specific topics. Subscribed agents will automatically process and publish results.

4. **Monitor Topics**

   View the table in the UI to see a list of topics and the latest messages published.

## Screenshots

### Initial Interface

<img width="959" alt="Landing Page Layout" src="https://github.com/user-attachments/assets/d9522aa1-4d39-4839-8990-8eab35b21f49">

### After Uploading Configuration

<img width="959" alt="Configuration is deployed" src="https://github.com/user-attachments/assets/8b6caf43-ddfa-4df3-84e6-b9f94a64293b">

### After Publishing Messages

<img width="959" alt="Calculation Net is completed" src="https://github.com/user-attachments/assets/943cc2f8-5b8e-4b08-bc06-5ecaa43a56a9">

## Project Structure

The project is structured as follows:

```css
ap_final_project/
├── example_conf_files/
├── html_files/
├── screenshots/
└── src/
    ├── configs/
    ├── graph/
    ├── server/
    ├── servlets/
    ├── test/
    ├── views/
    └── Main.java
```
