package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;

class Test10ArgumentCaptors {
	private BookingService bookingService;
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;
	private ArgumentCaptor<Double> doubleCaptor;
	
	@BeforeEach
	void setup() {
		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
		this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
	}
	

	@Test
	void should_PayCorrectPrice_WhenInputOk() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 1, 5), 2,
				true);

		//when
		bookingService.makeBooking(bookingRequest);
		// then
		verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleCaptor.capture());
		double captureArgument = doubleCaptor.getValue();
		
		assertEquals(400.0, captureArgument);
		
		System.out.println("the value of CaptureArgument: " + captureArgument);
//		assertEquals(expected, actual);
	}
	
	@Test
	void should_PayCorrectPrices_WhenMultipleCalls() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 1, 5), 2,
				true);
		BookingRequest bookingRequest2 = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 1, 02), 2,
				true);
		
		List<Double> expectedValues = Arrays.asList(400.0, 100.0);
		
		//when
		bookingService.makeBooking(bookingRequest);
		bookingService.makeBooking(bookingRequest2);
		
		// then
		verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
		List<Double> captureArguments = doubleCaptor.getAllValues();
		
		assertEquals(expectedValues, captureArguments);
		
		System.out.println("the value of CaptureArgument 2: " + captureArguments);
//		assertEquals(expected, actual);
	}


}
