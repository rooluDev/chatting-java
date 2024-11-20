# 📋 채팅 프로그램

## 📝 프로젝트 개요
이 프로젝트는 채팅 프로그램을 구현하는 것을 목표로 합니다.

이름을 설정하여 채팅방에 입장할 수 있고, 채팅방도 생성할 수 있습니다.

외부 라이브러리를 사용하지 않고 Java 내장 라이브러리만 사용하여 TCP 소켓 연결을 통해 접속합니다.

다양한 메시지 처리는 Command패턴을 사용해 처리합니다

## 🛠 기술 스택
![Java](https://img.shields.io/badge/java-005F0F?style=for-the-badge&logo=java&logoColor=white)

## 📺 화면
  + **이름 입력**

https://github.com/user-attachments/assets/f020c256-1748-4003-a1ab-371595e2af64

  + **채팅방 생성**

https://github.com/user-attachments/assets/79b2be88-8a85-46dd-bb79-50b31871a773

  + **채팅방 리스트 출력**
  
https://github.com/user-attachments/assets/49ab4494-b099-49e5-ba48-c8f094ef8246

  + **채팅방 입장 후 채팅**
  
https://github.com/user-attachments/assets/ec1c2411-51de-440f-be98-3a7ccd50962f

  + **채팅방 나가기**
  
https://github.com/user-attachments/assets/5596323d-6923-45e0-92a4-6426b9e24fb6

## 💡 주요 기능
+ 메시지 처리
  <details>
   <summary>코드 보기(펼치기/접기)</summary>
   
  Server Socket Recevier
  
   ```
   // message 받기
   String receivedMessage = client.getDataInputStream().readUTF();
   // 메시지 파싱
   String receivedCommand = messageParser.parseCommand(receivedMessage);
   String receivedData = messageParser.parseData(receivedMessage);
   CommandType commandType = CommandType.fromString(receivedCommand);
   // 커맨드 실행
   Command command = commandFactory.createCommand(client, commandType);
   command.execute(receivedData);
   ```
   
  Command Factory
  
    ```
    /**
     * commandType에 맞는 Command 생성
     *
     * @param client data를 보낸 client
     * @param commandType commandType
     * @return command
     */
    public Command createCommand(Client client, CommandType commandType) {
        switch (commandType) {
            case CREATE_ROOM -> {
                return new CreateRoomCommand(client);
            }
    ...
    ```
  Command Interface
    ```
    /**
     * Command Interface
     *
     * @param <T> data Type
     */
    public interface Command<T> {
        void execute(T data);
    }
    ```
  [Server Socket Receiver전체 코드](https://github.com/rooluDev/chatting-java/blob/main/server/src/socket/SocketReceiver.java)
  [Command Factory 전체 코드](https://github.com/rooluDev/chatting-java/blob/main/server/src/factory/CommandFactory.java)
  
  </details>
  
+ 객체 데이터 전송
  <details>
   <summary>코드 보기(펼치기/접기)</summary>
   
   Room Class

  ```
    /**
     * Room Dto
     */
     public class Room implements Serializable {
       private static final long serialVersionUID = 1L;
       ...
   ```

  객체화 메소드

  ```
    private byte[] serializeRooms(List<Room> rooms) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(rooms);
        objectOutputStream.flush();

        return byteArrayOutputStream.toByteArray();
    }
    ...
  ```
  
  데이터 보내기

  ```
    dataOutputStream.writeUTF("ROOM_LIST");
    dataOutputStream.writeInt(roomsByteArray.length);
    dataOutputStream.write(roomsByteArray);

    ...
  ```
  [RoomListCommand 전체 코드](https://github.com/rooluDev/chatting-java/blob/main/server/src/command/RoomListCommand.java)
  </details>

+ 채팅방과 참여자 저장소 관리
  <details>
   <summary>코드 보기(펼치기/접기)</summary>
   
   RoomRepository 멤버변수
   ```
    // Room 관리 repo
    private final static ConcurrentHashMap<Long, Room> store = new ConcurrentHashMap<>();
    // 참여자 관리 repo
    private final static ConcurrentHashMap<Room, Set<Client>> participantMap = new ConcurrentHashMap<>();
    // client room mapping repo
    private final static ConcurrentHashMap<Client, Room> clientRoomMap = new ConcurrentHashMap<>();
   ...

   ```

  [RoomRepository 전체 코드](https://github.com/rooluDev/chatting-java/blob/main/server/src/repository/RoomRepository.java)
  </details>

+ Thread 생명주기 관리
  <details>
   <summary>코드 보기(펼치기/접기)</summary>
    
    채팅방 입장 시 Main wait
   
    ```
    public synchronized void createRoom() throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("채팅방 이름 입력: ");
        String roomName = scanner.nextLine();
        sendMessage("CREATE_ROOM " + roomName);

        this.wait();
    }
   ```

   채팅방 나갈 시 Thread stop & notify
   
   ```
   public synchronized void notifyToMain(){
        this.notify();
    }
   ```

  [ChattingService 전체 코드](https://github.com/rooluDev/chatting-java/blob/main/client/src/service/ChattingService.java)
  </details>
  

