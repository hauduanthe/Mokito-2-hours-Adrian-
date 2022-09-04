package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class Test09MockingVoidMethods {
	
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
	}

	@Test
	void should_ThrowExeption_When_MailNotReady() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 1, 5), 2, false);
		
		doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(Mockito.any());
		
		//when
		Executable executable = () -> bookingService.makeBooking(bookingRequest);
		// then
		assertThrows(BusinessException.class, executable);
	}
	
	@Test
	void should_NotThrowExeption_When_MailNotReady() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 1, 5), 2, false);
		// can comment this line b/x doNothing() is default action.
		doNothing().when(mailSenderMock).sendBookingConfirmation(Mockito.any());
		
		//when
		bookingService.makeBooking(bookingRequest);
		// then
		// no exception throws
	}

}
