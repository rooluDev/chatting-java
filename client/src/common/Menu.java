package common;

import dto.Room;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Menu
 */
public class Menu {

    /**
     * 메인에 사용하는 메뉴
     *
     * @return 선택한 번호
     */
    public static int printMainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("메뉴를 선택하세요");
        System.out.println("1. 채팅방 생성하기");
        System.out.println("2. 채팅방 들어가기");
        System.out.println("3. 종료");
        System.out.print("입력: ");

        int menu = 0;
        try {
            menu = scanner.nextInt();
        } catch (InputMismatchException e) {
            // 입력 버퍼 비우기
            scanner.nextLine();
            menu = 0;
        }
        return menu;
    }

    /**
     * Room List에 사용하는 메뉴
     *
     * @param rooms 채팅방 리스트
     * @return 선택한 방 번호
     */
    public static int printRoomListMenu(List<Room> rooms) {
        int menu = 1;
        for (Room room : rooms) {
            System.out.println(menu++ + ". " + room.getRoomName());
        }

        System.out.print("입장할 방 번호를 입력하세요: ");
        Scanner scanner = new Scanner(System.in);
        try {
            menu = scanner.nextInt();
        } catch (InputMismatchException e) {
            // 버퍼 비우기
            scanner.nextLine();
            menu = 0;
        }
        return menu;
    }
}
