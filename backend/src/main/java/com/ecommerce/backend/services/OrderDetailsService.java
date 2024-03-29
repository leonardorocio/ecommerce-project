package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.OrderDetailsMapper;
import com.ecommerce.backend.mapper.PatchMapper;
import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.payload.OrderDetailsRequestBody;
import com.ecommerce.backend.repository.OrderDetailsRepository;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderDetailsMapper orderDetailsMapper;

    public List<OrderDetails> getOrdersDetails() {
        return orderDetailsRepository.findAll();
    }

    public OrderDetails getOrderDetailsById(int id) {
        return orderDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum item de pedido disponível com esse ID"));
    }
    @Transactional(rollbackOn = Exception.class)
    public OrderDetails postOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody) {
        OrderDetails orderDetails = orderDetailsMapper.mapToOrderDetails(orderDetailsRequestBody);
        return orderDetailsRepository.save(orderDetails);
    }

    @Transactional(rollbackOn = Exception.class)
    public OrderDetails updateOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody, int id) {
        OrderDetails originalOrderDetails = getOrderDetailsById(id);
        OrderDetails updatedOrderDetails = orderDetailsMapper.mapToUpdateOrderDetails(orderDetailsRequestBody);
        updatedOrderDetails.setId(originalOrderDetails.getId());
        return orderDetailsRepository.save(updatedOrderDetails);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteOrderDetails(int id) {
        OrderDetails orderDetailsToDelete = getOrderDetailsById(id);
        orderDetailsRepository.delete(orderDetailsToDelete);
    }
}
