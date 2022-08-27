package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;

class Test04MultipleThenReturnCalls {
	
	private BookingService bookingService;

	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;
	
	@BeforeEach
	void setup() {
		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
		
		System.out.println("List returned: " + roomServiceMock.getAvailableRooms());
		System.out.println("Object returned: " + roomServiceMock.findAvailableRoomId(null));
		System.out.println("List returned: " + roomServiceMock.getRoomCount());
	}
	
	@Test
	void should_CountAvailablePlaces_When_CalledMultipleTimes() {
		// given
//		doReturn(Collections.singletonList(new Room("Room 1", 5))).when(roomServiceMock).getAvailableRooms();
		when(this.roomServiceMock.getAvailableRooms())
				.thenReturn(Collections.singletonList(new Room("Room 1", 5)))
				.thenReturn(Collections.emptyList())
				.thenReturn(Arrays.asList(new Room("Rom1", 3), new Room("Room2", 3)));
		int expectedFirstCall = 5;
		int expectedSecondCall = 0;
		int expectedThirdCall = 6;
		// when
		int actualFirst = bookingService.getAvailablePlaceCount();
		int actualSecond = bookingService.getAvailablePlaceCount();
		int actualThird = bookingService.getAvailablePlaceCount();
		// then
		assertAll(() -> assertEquals(expectedFirstCall, actualFirst),
				() -> assertEquals(expectedSecondCall, actualSecond),
				() -> assertEquals(expectedThirdCall, actualThird));
	}

}
