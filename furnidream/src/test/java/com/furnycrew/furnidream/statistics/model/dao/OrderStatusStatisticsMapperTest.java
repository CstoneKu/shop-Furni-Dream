package com.furnycrew.furnidream.statistics.model.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.furnycrew.furnidream.statistics.model.dto.OrderStatusStatisticsDto;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderStatusStatisticsMapperTest {

    @Autowired
    private OrderStatusStatisticsMapper orderStatusStatisticsMapper;

    @Disabled
    @DisplayName("현재 모든 주문 상태 가져오기")
    @Test
    void findAllOrderStatus() {
        List<OrderStatusStatisticsDto> result = orderStatusStatisticsMapper.findAllOrderStatus();

        assertThat(result.size()).isNotNull().isEqualTo(6);
//        for (OrderSalesStatisticsDto orderSalesStatisticsDto : result) {
//            System.out.println(orderSalesStatisticsDto);
//        }
    }

}