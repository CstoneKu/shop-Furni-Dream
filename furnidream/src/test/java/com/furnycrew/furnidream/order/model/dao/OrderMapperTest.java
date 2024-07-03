package com.furnycrew.furnidream.order.model.dao;

import com.furnycrew.furnidream.common.search.SearchCriteria;
import com.furnycrew.furnidream.order.model.dto.OrderDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;

    @DisplayName("모든 주문 조회")
    @Test
    public void test1() {
        //given
        //when
//        List<OrderDto> orders = orderMapper.findAllOrder();
        List<OrderDto> orders = orderMapper.findOrdersByDateTime(new SearchCriteria(), 0, 10);
        //then
        assertThat(orders)
                .isNotNull()
                .isNotEmpty()
                .hasSize(10)
                .allSatisfy((order)->{
                    assertThat(order.getOrderCode()).isNotZero();
                    assertThat(order.getCustomerDto()).isNotNull();
                    assertThat(order.getCreatedAt()).isNotNull();
                    assertThat(order.getPhone()).isNotNull();
                    assertThat(order.getShippingAddress()).isNotNull();
                    assertThat(order.getOrderStatus()).isBetween(1, 7);
                    assertThat(order.getOrderProductList()).isNotNull().isNotEmpty();
                });
    }
    @Disabled
    @DisplayName("일별 주문 조회")
    @Test
    public void test2() {
        //given
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setName("day");
        LocalDateTime localDateTime =  LocalDateTime.of(2023,6, 13, 0, 0);
        searchCriteria.setValue(localDateTime);
        //when
        List<OrderDto> orders = orderMapper.findOrdersByDateTime(searchCriteria, 1, 10);
        //then

        assertThat(orders)
                .isNotNull()
                .isNotEmpty()
                .hasSize(10)
                .allSatisfy((order)->{
                    assertThat(order.getOrderCode()).isNotZero();
                    assertThat(order.getCustomerDto()).isNotNull();
                    assertThat(order.getCreatedAt().toLocalDate()).isEqualTo(localDateTime.toLocalDate());
                    assertThat(order.getPhone()).isNotNull();
                    assertThat(order.getShippingAddress()).isNotNull();
                    assertThat(order.getOrderStatus()).isBetween(1, 7);
                    assertThat(order.getOrderProductList()).isNotNull().isNotEmpty();
                });
    }
    @Disabled
    @DisplayName("월별 주문 조회")
    @Test
    public void test3() {
        //given
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setName("month");
        LocalDateTime localDateTime =  LocalDateTime.of(2023,6, 13, 0, 0);
        searchCriteria.setValue(localDateTime);
        //when
        List<OrderDto> orders = orderMapper.findOrdersByDateTime(searchCriteria, 1, 10);
        //then

        assertThat(orders)
                .isNotNull()
                .isNotEmpty()
                .hasSize(10)
                .allSatisfy((order)->{
                    assertThat(order.getOrderCode()).isNotZero();
                    assertThat(order.getCustomerDto()).isNotNull();
                    assertThat(order.getCreatedAt().getYear()).isEqualTo(localDateTime.getYear());
                    assertThat(order.getCreatedAt().getMonth()).isEqualTo(localDateTime.getMonth());
                    assertThat(order.getPhone()).isNotNull();
                    assertThat(order.getShippingAddress()).isNotNull();
                    assertThat(order.getOrderStatus()).isBetween(1, 7);
                    assertThat(order.getOrderProductList()).isNotNull().isNotEmpty();
                });
    }

    @Disabled
    @DisplayName("연도별 주문 조회")
    @Test
    public void test4() {
        //given
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setName("year");
        LocalDateTime localDateTime =  LocalDateTime.of(2024,6, 13, 0, 0);
        searchCriteria.setValue(localDateTime);
        //when
        List<OrderDto> orders = orderMapper.findOrdersByDateTime(searchCriteria , 1, 10);
        //then
        System.out.println(orders);
        assertThat(orders)
                .isNotNull()
                .isNotEmpty()
                .hasSize(10)
                .allSatisfy((order)->{
                    assertThat(order.getOrderCode()).isNotZero();
                    assertThat(order.getCustomerDto()).isNotNull();
                    assertThat(order.getCreatedAt().getYear()).isEqualTo(localDateTime.getYear());
                    assertThat(order.getPhone()).isNotNull();
                    assertThat(order.getShippingAddress()).isNotNull();
                    assertThat(order.getOrderStatus()).isBetween(1, 7);
                    assertThat(order.getOrderProductList()).isNotNull().isNotEmpty();
                });
    }

    @DisplayName("전체 주문 갯수 조회")
    @Test
    public void test5() {
        //given
        //when
//        List<OrderDto> orders = orderMapper.findAllOrder();
        int numOfOrder = orderMapper.countOrderByDateTime(new SearchCriteria());
        //then
        assertThat(numOfOrder).isNotZero();
    }

    @DisplayName("주문 상세조회")
    @Test
    public void test6() {
        //given
        //when
        OrderDto orderDto = orderMapper.getOrderDetail(new SearchCriteria("orderCode", 5, null, null));
        //then
        assertThat(orderDto).isNotNull();
    }
}