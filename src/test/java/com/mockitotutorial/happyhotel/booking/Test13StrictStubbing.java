package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.BDDMockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class Test13StrictStubbing {
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
	void should_InvokePayment_When_Prepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 1, 5), 2,
				false);
		
		lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1");

		//when
		bookingService.makeBooking(bookingRequest);
		// then
//		assertEquals(expected, actual);
	}


}
