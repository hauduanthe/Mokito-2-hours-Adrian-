package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class Test11Annotations {
	@InjectMocks
	private BookingService bookingService;

	@Mock
	private PaymentService paymentServiceMock;
	@Mock
	private RoomService roomServiceMock;
	@Mock
	private BookingDAO bookingDAOMock;
	@Mock
	private MailSender mailSenderMock;
	@Captor
	private ArgumentCaptor<Double> doubleCaptor;
	
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
