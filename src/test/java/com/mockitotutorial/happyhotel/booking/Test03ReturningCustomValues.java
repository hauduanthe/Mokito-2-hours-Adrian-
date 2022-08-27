package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;

class Test03ReturningCustomValues {
	
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
	void should_CountAvailablePlaces_When_OneRoomsAvailable() {
		// given
		doReturn(Collections.singletonList(new Room("Room 1", 5))).when(roomServiceMock).getAvailableRooms();
		int expected = 5;
		// when
		int actual = bookingService.getAvailablePlaceCount();
		// then
		assertEquals(expected, actual);
	}

	@Test
	void should_CountAvailablePlaces_When_MultipleRoomsAvailable() {
		// given
		List<Room> rooms = Arrays.asList(new Room("Room1", 2), new Room("Room2", 5));
//		doReturn(rooms).when(this.roomServiceMock).getAvailableRooms();
		when(this.roomServiceMock.getAvailableRooms()).thenReturn(rooms);
		
		int expected = 7;
		
		// when
		int actual = bookingService.getAvailablePlaceCount();
		// then
		assertEquals(expected, actual);
	}

}
