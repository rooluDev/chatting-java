# 📋 채팅 프로그램

---

## 📝 프로젝트 개요

이 프로젝트는 **채팅 프로그램**을 구현하는 것을 목표로 합니다.

- 이름을 설정하여 채팅방에 입장할 수 있고, 채팅방도 생성할 수 있습니다.
- 외부 라이브러리를 사용하지 않고, Java 내장 라이브러리만 사용하여 TCP 소켓 연결을 통해 접속합니다.
- 다양한 메시지 처리는 **Command 패턴**을 사용해 처리합니다.

---

## 🛠 기술 스택

![Java](https://img.shields.io/badge/java-005F0F?style=for-the-badge&logo=java&logoColor=white)

---

## 📺 화면

- **이름 입력**  

https://github.com/user-attachments/assets/f020c256-1748-4003-a1ab-371595e2af64

- **채팅방 생성**  

https://github.com/user-attachments/assets/79b2be88-8a85-46dd-bb79-50b31871a773

- **채팅방 리스트 출력**    

https://github.com/user-attachments/assets/49ab4494-b099-49e5-ba48-c8f094ef8246

- **채팅방 입장 후 채팅** 

https://github.com/user-attachments/assets/ec1c2411-51de-440f-be98-3a7ccd50962f

- **채팅방 나가기**  

https://github.com/user-attachments/assets/5596323d-6923-45e0-92a4-6426b9e24fb6

---

## 💡 주안점

### 1. 객체 데이터 전송

프로젝트 초반에는 채팅방 리스트를 단순히 문자열로 이어붙여 전송했지만,  
데이터가 많아지고 구조가 복잡해질수록 파싱 로직이 길어지고 유지보수가 어려워지는 문제가 있었습니다.  
이를 해결하기 위해 **Room 객체 자체를 직렬화해 전송**하고, 클라이언트에서 역직렬화를 통해 동일한 객체 구조로 재사용할 수 있도록 개선했습니다.

<details>
  <summary>코드 보기 (펼치기/접기)</summary>

Room Class

    /**
     * Room Dto
     */
    public class Room implements Serializable {
      private static final long serialVersionUID = 1L;
      ...
    }

 객체화 메소드
  
      private byte[] serializeRooms(List<Room> rooms) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(rooms);
        objectOutputStream.flush();
      
        return byteArrayOutputStream.toByteArray();
      }

데이터 보내기
  
      dataOutputStream.writeUTF("ROOM_LIST");
      dataOutputStream.writeInt(roomsByteArray.length);
      dataOutputStream.write(roomsByteArray);
  

  

  [RoomListCommand 전체 코드](https://github.com/rooluDev/chatting-java/blob/main/server/src/command/RoomListCommand.java)
  </details>

---

### 2. 메시지 처리

  초반에는 모든 메시지를 if-else 문으로 분기하여 처리했으나,  
  **메시지 유형이 늘어날수록 유지보수성이 떨어지고 코드 가독성이 악화되는 문제**가 발생했습니다.  
  이를 해결하기 위해 **Command 패턴을 적용하여 메시지 형식을 Command + Data로 표준화**했고,  
  **Factory 패턴을 도입해 새로운 메시지 유형이 추가되더라도 Command 객체만 생성하면 확장이 가능**하도록 개선했습니다.

  <details>
   <summary>코드 보기(펼치기/접기)</summary>
   
  Server Socket Recevier
  
     // message 받기
     String receivedMessage = client.getDataInputStream().readUTF();
     // 메시지 파싱
     String receivedCommand = messageParser.parseCommand(receivedMessage);
     String receivedData = messageParser.parseData(receivedMessage);
     CommandType commandType = CommandType.fromString(receivedCommand);
     // 커맨드 실행
     Command command = commandFactory.createCommand(client, commandType);
     command.execute(receivedData);
   
   
  Command Factory
  
    
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
      
    
  
  Command Interface
  
    
      /**
       * Command Interface
       *
       * @param <T> data Type
       */
      public interface Command<T> {
          void execute(T data);
      }
    
  [Server Socket Receiver전체 코드](https://github.com/rooluDev/chatting-java/blob/main/server/src/socket/SocketReceiver.java)
  
  [Command Factory 전체 코드](https://github.com/rooluDev/chatting-java/blob/main/server/src/factory/CommandFactory.java)
  
  </details>

---
  
### 3. Thread 생명주기 관리
  
  초기에는 채팅방 입장·퇴장 시 Main Thread와 채팅 스레드 간 제어가 원활하지 않아  
  **메시지 대기 상태에서 화면이 멈추거나, 채팅방 종료 후 스레드가 정상적으로 종료되지 않는 문제**가 발생했습니다.  
  이를 해결하기 위해 **wait()/notify() 메커니즘을 활용**하여 Main Thread와 채팅 스레드 간 동기화를 구현했습니다.  
  채팅방 입장 시 Main Thread는 `wait()`로 대기하고,  
  퇴장 시 채팅 스레드가 `notify()`를 호출해 안정적으로 스레드를 종료할 수 있도록 개선했습니다.
  
  <details>
   <summary>코드 보기(펼치기/접기)</summary>
    
  채팅방 입장 시 Main wait
    
        public synchronized void createRoom() throws InterruptedException, IOException {
            Scanner scanner = new Scanner(System.in);
            System.out.print("채팅방 이름 입력: ");
            String roomName = scanner.nextLine();
            sendMessage("CREATE_ROOM " + roomName);
    
            this.wait();
        }
   

   채팅방 나갈 시 Thread stop & notify
   
       public synchronized void notifyToMain(){
            this.notify();
        }
   

  [ChattingService 전체 코드](https://github.com/rooluDev/chatting-java/blob/main/client/src/service/ChattingService.java)
  
  </details>

  
---

### 4. 채팅방과 참여자 저장소 관리

초기에는 채팅방과 참여자 정보를 단순한 컬렉션(Map, List)으로 관리했지만,  
**다중 클라이언트가 동시에 접근할 때 데이터 충돌과 동기화 문제가 발생**했습니다.  
이를 해결하기 위해 **ConcurrentHashMap을 사용하여 Thread-Safe한 데이터 저장소**를 구성했고,  
또한 **참여자 ↔ 채팅방 간 빠른 탐색이 어렵다는 문제를 보완하기 위해 Mapping Table을 별도로 구성**하여  
원하는 데이터를 효율적으로 조회할 수 있도록 개선했습니다.

  <details>
   <summary>코드 보기(펼치기/접기)</summary>
   
   RoomRepository 멤버변수
   
        // Room 관리 repo
        private final static ConcurrentHashMap<Long, Room> store = new ConcurrentHashMap<>();
        // 참여자 관리 repo
        private final static ConcurrentHashMap<Room, Set<Client>> participantMap = new ConcurrentHashMap<>();
        // client room mapping repo
        private final static ConcurrentHashMap<Client, Room> clientRoomMap = new ConcurrentHashMap<>();
       ...

  [RoomRepository 전체 코드](https://github.com/rooluDev/chatting-java/blob/main/server/src/repository/RoomRepository.java)
  </details>


