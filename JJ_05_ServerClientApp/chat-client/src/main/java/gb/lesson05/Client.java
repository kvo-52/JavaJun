package gb.lesson05;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    public static final String SEPARATOR = "&&";
    private int connectionAttemptCounter = 20; // число попыток подключения к серверу (4 попытки в сек)
    private final String name; // имя клиента
    private final int port; // порт сервера
    private final String serverIP; // адрес сервера

    private final Socket socket;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;


    public Client(String serverIp, int port, String userName) {
        this.socket = new Socket();
        this.serverIP = serverIp;
        this.port = port;
        this.name = userName;

    }

    /**
     * Соединение с сервером c попытками подключения
     *
     * @return Socket socket или null если соединение не установлено
     * @throws IOException          - ошибка ввода вывода
     * @throws InterruptedException - ручное прерывание с клавиатуры
     */
    public Socket connect() throws IOException, InterruptedException {
        InetSocketAddress inetAddress = new InetSocketAddress(serverIP, port);
        while (connectionAttemptCounter-- > 0) {
            try {
                socket.connect(inetAddress);
            } catch (SocketException ignored) {

            }
            if (socket.isConnected()) {
                System.out.println("Connected!!!");
                break;
            }
            Thread.sleep(250);
            System.out.print(".");
        }
        if (!socket.isConnected())
            return null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
        return socket;
    }

    private void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Слушатель входящих сообщений от сервера
     */
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (socket.isConnected()) {
                    try {
                        message = bufferedReader.readLine();
                        if (message != null) {
                            if("#DISCONNECT".equals(message)){
                                throw new IOException("принудительное отключение");
                            }
                            System.out.println(message);
                        }
                    } catch (IOException e) {
                        closeEverything(socket, bufferedWriter, bufferedReader);
                        System.exit(1);
                    }
                }
            }
        }).start();
    }

    /**
     * Отправка сообщений на сервер
     */
    public void sendMessage() {
        try {
            bufferedWriter.write(name);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = scanner.nextLine();
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
            System.exit(1);
        }
    }
}
