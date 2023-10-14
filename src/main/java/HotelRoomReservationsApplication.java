import view.ConsoleView;
import view.MainView;

public class HotelRoomReservationsApplication {
	private static final ConsoleView consoleView = new MainView();
	public static void main(String[] args) {
		consoleView.showItemMenu(consoleView);
	}

}
