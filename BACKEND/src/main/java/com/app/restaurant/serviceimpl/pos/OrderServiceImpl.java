package com.app.restaurant.serviceimpl.pos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.pos.OrderDTO;
import com.app.restaurant.dto.pos.OrderRequestDTO;
import com.app.restaurant.dto.pos.OrderResponseDTO;
import com.app.restaurant.enums.OrderStatus;
import com.app.restaurant.enums.OrderType;
import com.app.restaurant.enums.PaymentMethod;
import com.app.restaurant.exception.ResourceNotFoundException;
import com.app.restaurant.model.master.Item;
import com.app.restaurant.model.master.ItemAddon;
import com.app.restaurant.model.master.ItemVariation;
import com.app.restaurant.model.pos.Customer;
import com.app.restaurant.model.pos.Order;
import com.app.restaurant.model.pos.OrderItem;
import com.app.restaurant.model.pos.OrderItemAddon;
import com.app.restaurant.repository.master.AddonsRepository;
import com.app.restaurant.repository.master.ItemRepository;
import com.app.restaurant.repository.pos.CustomerRepository;
import com.app.restaurant.repository.pos.OrderRepository;
import com.app.restaurant.service.pos.OrderService;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
    private final AddonsRepository addonsRepository;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        System.out.println("Creating a new order with DTO: " + dto);
        log.info("Creating a new order");
        Order order = new Order();
        populateOrderFromDTO(order, dto);
        Order savedOrder = orderRepository.save(order);
        log.info(MessageConstants.CREATED_SUCCESSFULLY);
        return toResponseDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrder(Integer orderId, OrderRequestDTO dto) {
        log.info("Updating order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));
        if (order.getStatus() != OrderStatus.HELD) {
            throw new IllegalStateException("Only held orders can be updated");
        }
        populateOrderFromDTO(order, dto);
        Order savedOrder = orderRepository.save(order);
        log.info(MessageConstants.UPDATED_SUCCESSFULLY);
        return toResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(Integer orderId) {
        log.info("Fetching order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));
        log.info(MessageConstants.FETCHED_SUCCESSFULLY);
        return toResponseDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getHeldOrders() {
        log.info("Fetching all held orders");
        List<Order> heldOrders = orderRepository.findByStatus(OrderStatus.HELD);
        List<OrderResponseDTO> dtos = heldOrders.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        log.info(MessageConstants.FETCHED_SUCCESSFULLY);
        return dtos;
    }

    @Override
    @Transactional
    public OrderResponseDTO finalizeOrder(Integer orderId, OrderRequestDTO dto) {
        log.info("Finalizing order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));
        if (order.getStatus() != OrderStatus.HELD) {
            throw new IllegalStateException("Only held orders can be finalized");
        }
        if (dto.getPaymentMethod() == null) {
            throw new IllegalArgumentException("Payment method is required to finalize order");
        }
        populateOrderFromDTO(order, dto);
        order.setStatus(OrderStatus.COMPLETED);
        Order savedOrder = orderRepository.save(order);
        log.info("Order finalized successfully");
        return toResponseDTO(savedOrder);
    }

    @Override
    @Transactional
    public String cancelOrder(Integer orderId) {
        log.info("Cancelling order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RECORD_NOT_FOUND));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        log.info("Order cancelled successfully");
        return "Order cancelled successfully";
    }

    @Override
    public Page<OrderDTO> getAllOrder(String search, Boolean unpaged,
            Pageable pageable) {

        Specification<Order> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (search != null && !search.trim().isEmpty()) {
                String searchLower = "%" + search.toLowerCase() + "%";
                List<Predicate> predicates = new ArrayList<>();

                // Customer name (LIKE, case-insensitive, left join)
                Predicate customerPredicate = cb.like(
                        cb.lower(root.join("customer", JoinType.LEFT).get("name")),
                        searchLower);
                predicates.add(cb.and(cb.isNotNull(root.get("customer")), customerPredicate));

                // Order date (parse search to LocalDate)
                try {
                    LocalDate searchDate = LocalDate.parse(search, DateTimeFormatter.ISO_LOCAL_DATE);
                    predicates.add(cb.equal(root.get("orderDate"), searchDate));
                } catch (DateTimeParseException e) {
                    log.debug("Search term {} is not a valid date (yyyy-MM-dd)", search);
                }

                // Order status (partial match on enum names)
                for (OrderStatus status : OrderStatus.values()) {
                    if (status.name().toLowerCase().contains(search.toLowerCase())) {
                        predicates.add(cb.equal(root.get("status"), status));
                    }
                }

                // Order type (partial match on enum names)
                for (OrderType orderType : OrderType.values()) {
                    if (orderType.name().toLowerCase().contains(search.toLowerCase())) {
                        predicates.add(cb.equal(root.get("orderType"), orderType));
                    }
                }

                // Combine predicates with OR
                predicate = cb.or(predicates.toArray(new Predicate[0]));
            }
            return predicate;
        };

        if (Boolean.TRUE.equals(unpaged)) {
            List<Order> all = orderRepository.findAll(spec);
            List<OrderDTO> dtos = all.stream().map(this::toOrderDTo).toList();
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return new PageImpl<>(dtos);
        } else {
            Page<Order> page = orderRepository.findAll(spec, pageable);
            log.info(MessageConstants.FETCHED_SUCCESSFULLY);
            return page.map(this::toOrderDTo);
        }

    }

    private void populateOrderFromDTO(Order order, OrderRequestDTO dto) {
        // Set customer (optional)
        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Customer not found with ID: " + dto.getCustomerId()));
            order.setCustomer(customer);
        } else {
            order.setCustomer(null);
        }

        // Set order fields
        order.setOrderDate(LocalDate.now());
        order.setOrderTime(LocalTime.now());
        order.setStatus(dto.getStatus() != null ? dto.getStatus() : OrderStatus.COMPLETED);
        order.setOrderType(dto.getOrderType() != null ? dto.getOrderType() : OrderType.PICKUP);
        order.setSubtotal(dto.getSubtotal() != null ? dto.getSubtotal() : BigDecimal.ZERO);
        order.setTotalQuantity(dto.getTotalQuantity() != null ? dto.getTotalQuantity() : BigDecimal.ZERO);
        order.setDiscountPercent(dto.getDiscountPercent() != null ? dto.getDiscountPercent() : BigDecimal.ZERO);
        order.setDiscountAmount(dto.getDiscountAmount() != null ? dto.getDiscountAmount() : BigDecimal.ZERO);
        order.setDeliveryCharge(dto.getDeliveryCharge() != null ? dto.getDeliveryCharge() : BigDecimal.ZERO);
        order.setContainerCharge(dto.getContainerCharge() != null ? dto.getContainerCharge() : BigDecimal.ZERO);
        order.setTaxPercent(dto.getTaxPercent() != null ? dto.getTaxPercent() : BigDecimal.ZERO);
        order.setTaxAmount(dto.getTaxAmount() != null ? dto.getTaxAmount() : BigDecimal.ZERO);
        order.setRoundOff(dto.getRoundOff() != null ? dto.getRoundOff() : BigDecimal.ZERO);
        order.setFinalTotal(dto.getFinalTotal() != null ? dto.getFinalTotal() : BigDecimal.ZERO);
        order.setTableNumber(dto.getTableNumber() != null ? dto.getTableNumber() : "");
        order.setPaymentMethod(dto.getPaymentMethod() != null ? dto.getPaymentMethod() : PaymentMethod.CASH);

        // Validate and set order items
        if (dto.getOrderItems() == null || dto.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderRequestDTO.OrderItemDTO itemDTO : dto.getOrderItems()) {
            if (itemDTO.getQuantity() == null || itemDTO.getQuantity() <= 0) {
                throw new IllegalArgumentException("Item quantity must be greater than zero");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(itemDTO.getQuantity());

            // Validate and set item
            if (itemDTO.getItemId() != null) {
                Item itemEntity = itemRepository.findById(itemDTO.getItemId())
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Item not found with ID: " + itemDTO.getItemId()));
                if (!itemEntity.isStatus()) {
                    throw new IllegalArgumentException("Item with ID " + itemDTO.getItemId() + " is inactive");
                }
                orderItem.setItem(itemEntity);
            } else {
                throw new IllegalArgumentException("Item ID is required");
            }

            // Validate and set variation
            if (itemDTO.getVariationId() != null) {
                ItemVariation variation = orderItem.getItem().getItemVariations().stream()
                        .filter(v -> v.getId().equals(itemDTO.getVariationId()) && v.isStatus())
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Variation not found with ID: " + itemDTO.getVariationId()));
                orderItem.setVariation(variation);
            } else if (!orderItem.getItem().isVariation() && orderItem.getItem().getPrice() != null) {
                orderItem.setVariation(null);
            } else {
                throw new IllegalArgumentException("Item requires a variation or valid price");
            }

            // Set add-ons
            List<OrderItemAddon> orderItemAddons = new ArrayList<>();
            if (!itemDTO.getAddons().isEmpty() && itemDTO.getAddons() != null) {
                for (OrderRequestDTO.OrderItemAddonDTO addonDTO : itemDTO.getAddons()) {
                    if (addonDTO.getName() == null || addonDTO.getName().isEmpty()) {
                        throw new IllegalArgumentException("Add-on name is required");
                    }
                    if (addonDTO.getPrice() == null || addonDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                        throw new IllegalArgumentException("Add-on price must be greater than zero");
                    }
                    // Validate add-on name exists in Addons
                    addonsRepository.findByNameAndStatusTrue(addonDTO.getName())
                            .orElseThrow(
                                    () -> new ResourceNotFoundException("Add-on not found: " + addonDTO.getName()));
                    OrderItemAddon addon = new OrderItemAddon();
                    addon.setName(addonDTO.getName());
                    addon.setPrice(addonDTO.getPrice());
                    addon.setStatus(addonDTO.isStatus());
                    addon.setOrderItem(orderItem);
                    if (addonDTO.getVariationId() != null) {
                        ItemVariation variation = orderItem.getItem().getItemVariations().stream()
                                .filter(v -> v.getId().equals(addonDTO.getVariationId()) && v.isStatus())
                                .findFirst()
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        "Variation not found with ID: " + addonDTO.getVariationId()));
                        addon.setItemVariation(variation);
                    }
                    if (addonDTO.getId() != null) {
                        ItemAddon addonEntity = new ItemAddon();
                        addonEntity.setId(addonDTO.getId());
                        addon.setItemAddon(addonEntity);
                    }
                    orderItemAddons.add(addon);
                }
            }
            orderItem.setAddons(orderItemAddons);
            // Calculate subtotal (item/variation price + add-ons price) * quantity
            BigDecimal price = orderItem.getVariation() != null ? orderItem.getVariation().getPrice()
                    : orderItem.getItem().getPrice();
            BigDecimal addonTotal = orderItemAddons.stream()
                    .map(OrderItemAddon::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            orderItem.setSubtotal(price.add(addonTotal).multiply(BigDecimal.valueOf(itemDTO.getQuantity())));

            orderItem.setOrder(order);
            orderItems.add(orderItem);

        }

        order.setOrderItems(orderItems);

        // Calculate order subtotal and total quantity
        order.setSubtotal(orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setTotalQuantity(BigDecimal.valueOf(orderItems.stream()
                .mapToLong(OrderItem::getQuantity)
                .sum()));
    }

    private OrderResponseDTO toResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        if (order.getCustomer() != null) {
            OrderResponseDTO.CustomerDTO customerDTO = new OrderResponseDTO.CustomerDTO();
            customerDTO.setId(order.getCustomer().getId());
            customerDTO.setName(order.getCustomer().getName());
            customerDTO.setPhone(order.getCustomer().getPhone());
            customerDTO.setEmail(order.getCustomer().getEmail());
            dto.setCustomer(customerDTO);
        }
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderTime(order.getOrderTime());
        dto.setStatus(order.getStatus());
        dto.setOrderType(order.getOrderType());
        dto.setSubtotal(order.getSubtotal());
        dto.setTotalQuantity(order.getTotalQuantity());
        dto.setDiscountPercent(order.getDiscountPercent());
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setDeliveryCharge(order.getDeliveryCharge());
        dto.setContainerCharge(order.getContainerCharge());
        dto.setTaxPercent(order.getTaxPercent());
        dto.setTaxAmount(order.getTaxAmount());
        dto.setRoundOff(order.getRoundOff());
        dto.setFinalTotal(order.getFinalTotal());
        dto.setTableNumber(order.getTableNumber());
        dto.setPaymentMethod(order.getPaymentMethod());

        // Map order items
        List<OrderResponseDTO.OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                .map(oi -> {
                    OrderResponseDTO.OrderItemDTO itemDTO = new OrderResponseDTO.OrderItemDTO();
                    itemDTO.setId(oi.getId());
                    itemDTO.setItemId(oi.getItem().getId());
                    itemDTO.setItemName(oi.getItem().getName());
                    if (oi.getVariation() != null) {
                        itemDTO.setVariationId(oi.getVariation().getId());
                        itemDTO.setVariationName(oi.getVariation().getName());
                        itemDTO.setPrice(oi.getVariation().getPrice());
                    } else {
                        itemDTO.setPrice(oi.getItem().getPrice());
                    }
                    itemDTO.setQuantity(oi.getQuantity());
                    itemDTO.setSubtotal(oi.getSubtotal());
                    itemDTO.setAddons(oi.getAddons().stream()
                            .map(addon -> new OrderResponseDTO.OrderItemAddonDTO(
                                    addon.getId(),
                                    addon.getName(),
                                    addon.getPrice(),
                                    addon.isStatus(),
                                    addon.getItemVariation() != null ? addon.getItemVariation().getId() : null))
                            .collect(Collectors.toList()));
                    return itemDTO;
                })
                .collect(Collectors.toList());
        dto.setOrderItems(orderItemDTOs);

        // Generate bill
        OrderResponseDTO.BillDTO billDTO = new OrderResponseDTO.BillDTO();
        billDTO.setOrderId(order.getId());
        billDTO.setCustomerName(order.getCustomer() != null ? order.getCustomer().getName() : "Guest");
        billDTO.setItems(orderItemDTOs.stream()
                .map(oi -> new OrderResponseDTO.BillItemDTO(
                        oi.getItemName(),
                        oi.getVariationName() != null ? oi.getVariationName() : "",
                        oi.getQuantity(),
                        oi.getPrice(),
                        oi.getSubtotal(),
                        oi.getAddons()))
                .collect(Collectors.toList()));
        billDTO.setSubtotal(order.getSubtotal());
        billDTO.setTotalQuantity(order.getTotalQuantity());
        billDTO.setDiscountPercent(order.getDiscountPercent());
        billDTO.setDiscountAmount(order.getDiscountAmount());
        billDTO.setDeliveryCharge(order.getDeliveryCharge());
        billDTO.setContainerCharge(order.getContainerCharge());
        billDTO.setTaxPercent(order.getTaxPercent());
        billDTO.setTaxAmount(order.getTaxAmount());
        billDTO.setRoundOff(order.getRoundOff());
        billDTO.setFinalTotal(order.getFinalTotal());
        billDTO.setTableNumber(order.getTableNumber());
        billDTO.setPaymentMethod(order.getPaymentMethod());
        dto.setBill(billDTO);

        // Generate KOT
        OrderResponseDTO.KotDTO kotDTO = new OrderResponseDTO.KotDTO();
        kotDTO.setOrderId(order.getId());
        kotDTO.setItems(orderItemDTOs.stream()
                .map(oi -> new OrderResponseDTO.KotItemDTO(
                        oi.getItemName(),
                        oi.getVariationName() != null ? oi.getVariationName() : "",
                        oi.getQuantity(),
                        oi.getAddons()))
                .collect(Collectors.toList()));
        kotDTO.setTableNumber(order.getTableNumber());
        kotDTO.setOrderType(order.getOrderType());
        dto.setKot(kotDTO);

        return dto;
    }

    private OrderDTO toOrderDTo(Order x) {
        OrderDTO dto = new OrderDTO();

        dto.setId(x.getId());
        dto.setOrderDate(x.getOrderDate());
        dto.setOrderTime(x.getOrderTime());
        if (x.getCustomer() != null) {
            dto.setCustomer(x.getCustomer().getName());
        } else {
            dto.setCustomer("GUEST");
        }
        dto.setStatus(x.getStatus());
        dto.setOrderType(x.getOrderType());
        dto.setSubtotal(x.getSubtotal());
        dto.setTotalQuantity(x.getTotalQuantity());
        dto.setDiscountPercent(x.getDiscountPercent());
        dto.setDiscountAmount(x.getDiscountAmount());
        dto.setDeliveryCharge(x.getDeliveryCharge());
        dto.setContainerCharge(x.getContainerCharge());
        dto.setTaxPercent(x.getTaxPercent());
        dto.setTaxAmount(x.getTaxAmount());
        dto.setRoundOff(x.getRoundOff());
        dto.setFinalTotal(x.getFinalTotal());
        dto.setTableNumber(x.getTableNumber());
        dto.setPaymentMethod(x.getPaymentMethod());
        dto.setCreatedBy(x.getCreatedBy());
        dto.setLastModifiedBy(x.getLastModifiedBy());

        return dto;
    }

}
