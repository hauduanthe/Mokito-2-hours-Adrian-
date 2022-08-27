package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

class Test07VerifyingBehaviours {
	
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
	void should_InvokePayment_When_Prepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 1, 5), 2,
				true);

		//when
		bookingService.makeBooking(bookingRequest);
		// then
		verify(paymentServiceMock).pay(bookingRequest, 400.0);
		verifyNoMoreInteractions(paymentServiceMock);
//		assertEquals(expected, actual);
	}
	@Test
	void should_NotInvokePayment_When_NotPrepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 1, 5), 2,
				false);
		
		//when
		bookingService.makeBooking(bookingRequest);
//		double actual = bookingService.calculatePrice(bookingRequest);
		
		
		// then
		verify(paymentServiceMock, never()).pay(Mockito.any(), anyDouble());
//		assertEquals(expected, actual);
	}

}
