package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManager entityManager;

    Product product;

    @BeforeEach
    void setUp() {
        Product newProduct = new Product();
        newProduct.setProductStatus(ProductStatus.NEW);
        newProduct.setDescription("New Product");
        product = productRepository.save(newProduct);

    }

    @Test
    void testSaveOrderWithLine() {
        OrderHeader orderHeader = new OrderHeader();

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        Address newAddress = new Address();
        newAddress.setCity("New City");
        newAddress.setState("New State");
        newAddress.setAddress("New Address");
        newAddress.setZipCode("11-111");
        Customer customer = new Customer();
        customer.setCustomerName("AAA");
        customer.setAddress(newAddress);
        customer.setPhone("+48111111111");
        customer.setEmail("aaa@aaa.com");
        Customer savedCustomer = customerRepository.save(customer);

        orderHeader.addOrderLine(orderLine);
//        savedCustomer.addOrderHeader(orderHeader);
        orderHeader.setCustomer(savedCustomer);
//        customer.setOrders(Set.of(orderHeader));

        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("me");
        orderHeader.setOrderApproval(orderApproval);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        entityManager.flush();
        entityManager.clear();

        savedOrder = orderHeaderRepository.findById(savedOrder.getId()).get();

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderLines());
        assertEquals(savedOrder.getOrderLines().size(), 1);
        assertEquals(savedOrder.getCustomer().getCustomerName(), "AAA");
        assertEquals(savedOrder.getCustomer().getOrders().size(), 1);
    }

    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        Address newAddress = new Address();
        newAddress.setCity("New City");
        newAddress.setState("New State");
        newAddress.setAddress("New Address");
        newAddress.setZipCode("11-111");
        Customer customer = new Customer();
        customer.setCustomerName("AAA");
        customer.setAddress(newAddress);
        customer.setPhone("+48111111111");
        customer.setEmail("aaa@aaa.com");
        Customer savedCustomer = customerRepository.save(customer);

        savedCustomer.addOrderHeader(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }

    @Test
    void testDeleteCascade() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("AAA");
        orderHeader.setCustomer(customerRepository.save(customer));

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("me");
        orderHeader.setOrderApproval(orderApproval);

        orderHeader.addOrderLine(orderLine);
        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        System.out.println("Order saved and flushed");

        orderHeaderRepository.deleteById(savedOrder.getId());
        orderHeaderRepository.flush();

        assertThrows(EntityNotFoundException.class, () -> {
            OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());
            assertNull(fetchedOrder);
        });
    }
}