package command;

import common.Menu;
import dto.Room;
import service.ChattingService;

import java.io.IOException;
import java.util.List;

/**
 * Room List Command
 */
public class RoomListCommand implements Command<List<Room>> {

    private final ChattingService chattingService;

    public RoomListCommand(ChattingService chattingService) {
        this.chattingService = chattingService;
    }

    @Override
    public void execute(List<Room> rooms) {
        if (rooms.size() == 0) {
            System.out.println("생성된 채팅방이 없습니다.");
            chattingService.notifyToMain();
            return;
        }

        boolean success = false;
        while (!success) {
            try {
                int selectedRoomIndex = Menu.printRoomListMenu(rooms);
                Room room = rooms.get(selectedRoomIndex - 1);
                chattingService.joinRoom(room.getRoomId());
                success = true;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("올바른 방 번호를 입력하세요.");
            } catch (IOException e) {
                System.out.println("서버 에러");
            }
        }
    }
}
