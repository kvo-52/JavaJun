package gb.lesson05;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManager implements Runnable {
    public static final String SEPARATOR = "&&";
    private final Socket socket;
    public static ConcurrentHashMap<String, ClientManager> clients = new ConcurrentHashMap<>();

    private String name;

    private String socketClient;

    private BufferedReader bufferedReader;

    private BufferedWriter bufferedWriter;

    public ClientManager(Socket socket) {
        this.socket = socket;
        try {
            socketClient = socket.getInetAddress().toString().split("/")[1] + ":" + socket.getPort();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            name = bufferedReader.readLine();
            if (name.equalsIgnoreCase("all")) {
                sendMessage("клиент с именем @" + name + " зарезервирован как служебный, используйте другое имя");
                sendMessage("#DISCONNECT");
                throw new Exception("попыдка подключения с зарезервированным именем");
            }
            if (nameIsExist(name)) {
                sendMessage("клиент с именем @" + name + " уже подключен к серверу, используйте другое имя");
                sendMessage("#DISCONNECT");
                throw new Exception("подключения с уже имеющимся именем");
            }
            broadcastMessage("*** Подключился @" + name);
            clients.put(socketClient, this);
            sendMessage(name + ", добро пожаловать в чат, напишите #help для справки");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            closeEverithing(socket, bufferedWriter, bufferedReader);
        }
    }

    private boolean nameIsExist(String name) {
        for (Map.Entry<String, ClientManager> entry : clients.entrySet()) {
            if (name.equals(entry.getValue().getName())) return true;
        }
        return false;
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();

                messageProcessing(messageFromClient);

                System.out.printf("Клиент %s: %s\n", name, messageFromClient);

            } catch (Exception e) {
                closeEverithing(socket, bufferedWriter, bufferedReader);
                break;
            }
        }

    }

    /** Обработка входящих сообщений
     * @param messageFromClient
     * @throws Exception
     */
    private void messageProcessing(String messageFromClient) throws Exception {
        if (messageFromClient != null) {
            char firsChar = messageFromClient.toCharArray()[0];
            if ('#' == firsChar) {//пришла команда
                if ("#who".equalsIgnoreCase(messageFromClient)) {
                    // пока одна команда #who - кто есть на сервере
                    sendMessage(whoIsOnline());
                } else if ("#help".equalsIgnoreCase(messageFromClient)) {
                    String text = Files.readString(Paths.get(Objects.requireNonNull(getClass()
                                    .getClassLoader()
                                    .getResource("help.txt"))
                            .toURI()));
                    sendMessage(text);

                } else if ("#exit".equalsIgnoreCase(messageFromClient)) {
                    sendMessage("Удачи!");
                    sendMessage("#DISCONNECT");
                    throw new Exception("отключение по команде #exit");
                } else {
                    sendMessage("*** команда не распознана.Отправьте #help - для получения справки");
                }

            } else if ('@' == firsChar) { //пришло адресное или общее сообщение
                /* разбор пришедшего сообщения
                 *   протокол:
                 *   `Text_string` или `@All, Text_String` - сообщение для всех
                 *   `@Name, Text_String` - частное сообщение
                 */
                String[] splitMessage = messageFromClient.substring(1).split(" ", 2);
                if (!splitMessage[0].equalsIgnoreCase("all")) {
                    String toClient = splitMessage[0];
                    if (nameIsExist(toClient)) {
                        String msg = splitMessage[1];
                        privateMessage(toClient, msg);
                    } else {
                        sendMessage("*** клиента @" + toClient + " нет на сервере");
                    }
                }
            } else {// в крайнем случае считаем, что пришло общее сообщение
                broadcastMessage("   @" + name + ": " + messageFromClient);
            }

        }
    }

    /** Отправка личных сообщений
     * @param toClient  - имя адресата
     * @param msg - текст сообщения
     */
    private void privateMessage(String toClient, String msg) {
        for (Map.Entry<String, ClientManager> entry : clients.entrySet()) {
            if (toClient.equals(entry.getValue().getName())) {
                entry.getValue().sendMessage(">>> @" + name + ":  " + msg);
                break;
            }
        }
    }

    /** Возвращает список клиентов подключенных к серверу (в виде текста для отправки)
     * @return
     */
    private static String whoIsOnline() {
        StringBuffer msg = new StringBuffer("*** Сейчас онлайн:\n");
        clients.forEach((key, val) -> {
            msg.append("*** @").append(val.getName()).append("\n");
        });
        return msg.toString();
    }

    private void broadcastMessage(String message) {
        clients.forEach((key, val) -> {
            if (!socketClient.equals(key))
                val.sendMessage(message);
        });
    }

    public void sendMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverithing(socket, bufferedWriter, bufferedReader);
        }

    }

    private void closeEverithing(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        removeClient();
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeClient() {
        if (clients.remove(this.socketClient) != null) {
            broadcastMessage("*** " + this.name + " покинул чат");
            System.out.println("Клиент " + this.name + " покинул чат");
        }
        clients.forEach((k, v) -> System.out.println(k + " | " + v.getName()));
    }


    public String getName() {
        return name;
    }

    public String getSocketClient() {
        return socketClient;
    }

}
