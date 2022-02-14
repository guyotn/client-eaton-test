package mock;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MockUnitTest {
	private Mock mock;
	
	private void testGetRandom(int lowerBound, int upperBound, Mock mock) {
		int res;
		
		for (int i=0; i<10; i++) {
			res = mock.getRandom();
			assertEquals((mock.getMax()+mock.getMin())/2.0, res, (mock.getMax()-mock.getMin())/2.0);
		}
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		mock = new Mock();
	}

	@Test
	public void testBasicGetRandom() {
		int res;
		for (int i=0; i<10; i++) {
			res = mock.getRandom();
			assertEquals((mock.getMax()+mock.getMin())/2.0, res, (mock.getMax()-mock.getMin())/2.0);
		}
	}
	
	@Test
	public void testLowerBoundOk() {
		assertEquals(0, mock.getMin());
		mock.setMin(100);
		assertEquals(100, mock.getMin());
		testGetRandom(100, 1000, mock);
	}
	
	@Test
	public void testLowerBoundNok() {
		try {
		mock.setMin(2000);
		fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, mock.getMin());
		}
		try {
			mock.setMin(1001);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, mock.getMin());
		}
	}
	
	@Test
	public void testUpperBoundOk() {
		assertEquals(1000, mock.getMax());
		mock.setMax(100);
		assertEquals(100, mock.getMax());
		testGetRandom(0, 100, mock);
	}
	
	@Test
	public void testUpperBoundNok() {
		try {
			mock.setMin(100);
			mock.setMax(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(1000, mock.getMax());
		}
		try {
			mock.setMax(99);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(1000, mock.getMax());
		}
	}
	
	@Test
	public void testSetMinMax() {
		mock.setMinMax(12, 20);
		assertEquals(12, mock.getMin());
		assertEquals(20, mock.getMax());
		testGetRandom(12, 20, mock);
		try {
			mock.setMinMax(13,12);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(12, mock.getMin());
			assertEquals(20, mock.getMax());
		}
	}
	
	@Test
	public void testParameterizeConstructor() {
		Mock parameterizeMock = new Mock(18, 500);
		assertEquals(18, parameterizeMock.getMin());
		assertEquals(500, parameterizeMock.getMax());
		testGetRandom(18, 500, parameterizeMock);
	}
	
}
