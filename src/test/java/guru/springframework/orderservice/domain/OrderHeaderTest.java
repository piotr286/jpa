package guru.springframework.orderservice.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderHeaderTest {

    @Test
    void testEquals() {
        OrderHeader o1 = new OrderHeader();
        o1.setId(1L);

        OrderHeader o2 = new OrderHeader();
        o2.setId(1L);

        assertEquals(o1, o2);
    }

    @Test
    void testNotEquals() {
        OrderHeader o1 = new OrderHeader();
        o1.setId(1L);

        OrderHeader o2 = new OrderHeader();
        o2.setId(3L);

        assertNotEquals(o1, o2);
    }
}