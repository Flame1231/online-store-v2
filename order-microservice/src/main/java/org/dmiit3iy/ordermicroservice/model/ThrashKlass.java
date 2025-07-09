//package org.dmiit3iy.ordermicroservice.model;
//
//public class ThrashKlass {
//}
//// Стартовая реализация микросервисов. Пока без авторизации.
//
//// =====================
//// 1. ORDER SERVICE
//// =====================
//
//// OrderController.java
//@RestController
//@RequestMapping("/api/order")
//@RequiredArgsConstructor
//public class OrderController {
//
//    private final InventoryGrpcClient inventoryClient;
//    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
//
//    @PostMapping
//    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
//        ProductResponse product = inventoryClient.checkAvailability(orderRequest.getProductId());
//
//        if (product.getQuantity() < orderRequest.getQuantity()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough stock");
//        }
//
//        OrderEvent orderEvent = new OrderEvent(UUID.randomUUID().toString(), orderRequest, product);
//        kafkaTemplate.send("orders", orderEvent);
//
//        return ResponseEntity.ok("Order placed successfully");
//    }
//}
//
//// InventoryGrpcClient.java
//@Component
//@RequiredArgsConstructor
//public class InventoryGrpcClient {
//
//    private final InventoryServiceGrpc.InventoryServiceBlockingStub stub;
//
//    public ProductResponse checkAvailability(Long productId) {
//        ProductRequest request = ProductRequest.newBuilder()
//                .setProductId(productId)
//                .build();
//        return stub.checkAvailability(request);
//    }
//}
//
//// OrderEvent.java (Kafka message)
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class OrderEvent {
//    private String orderId;
//    private OrderRequest orderRequest;
//    private ProductResponse productResponse;
//}
//
//// OrderRequest.java
//@Data
//public class OrderRequest {
//    private Long productId;
//    private int quantity;
//    private Long userId;
//}
//
//// =====================
//// 2. INVENTORY SERVICE
//// =====================
//
//// InventoryServiceImpl.java
//@Service
//@RequiredArgsConstructor
//public class InventoryServiceImpl extends InventoryServiceGrpc.InventoryServiceImplBase {
//
//    private final ProductRepository productRepository;
//
//    @Override
//    public void checkAvailability(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
//        Product product = productRepository.findById(request.getProductId())
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        ProductResponse response = ProductResponse.newBuilder()
//                .setProductId(product.getId())
//                .setName(product.getName())
//                .setPrice(product.getPrice())
//                .setSale(product.getSale())
//                .setQuantity(product.getQuantity())
//                .build();
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//}
//
//// Product.java
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Product {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    private int quantity;
//    private double price;
//    private double sale;
//}
//
//// ProductRepository.java
//public interface ProductRepository extends JpaRepository<Product, Long> {
//}
//
//// =====================
//// 3. NOTIFICATION SERVICE
//// =====================
//
//// KafkaOrderConsumer.java
//@Component
//@RequiredArgsConstructor
//public class KafkaOrderConsumer {
//
//    private final OrderRepository orderRepository;
//
//    @KafkaListener(topics = "orders", groupId = "notification")
//    public void consume(OrderEvent event) {
//        OrderEntity order = new OrderEntity();
//        order.setOrderId(event.getOrderId());
//        order.setProductId(event.getOrderRequest().getProductId());
//        order.setUserId(event.getOrderRequest().getUserId());
//        order.setQuantity(event.getOrderRequest().getQuantity());
//        order.setPrice(event.getProductResponse().getPrice());
//        order.setSale(event.getProductResponse().getSale());
//        order.setTotalPrice(
//                (event.getProductResponse().getPrice() - event.getProductResponse().getSale()) * event.getOrderRequest().getQuantity()
//        );
//        orderRepository.save(order);
//    }
//}
//
//// OrderEntity.java
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "orders")
//public class OrderEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String orderId;
//    private Long productId;
//    private int quantity;
//    private double price;
//    private double sale;
//    private double totalPrice;
//    private Long userId;
//}
//
//// OrderRepository.java
//public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
//    List<OrderEntity> findByOrderId(String orderId);
//    List<OrderEntity> findByUserId(Long userId);
//}
//
//// OrderReadController.java
//@RestController
//@RequestMapping("/api/orders")
//@RequiredArgsConstructor
//public class OrderReadController {
//
//    private final OrderRepository orderRepository;
//
//    @GetMapping("/all")
//    public List<OrderEntity> getAll() {
//        return orderRepository.findAll();
//    }
//
//    @GetMapping("/{orderId}")
//    public List<OrderEntity> getByOrderId(@PathVariable String orderId) {
//        return orderRepository.findByOrderId(orderId);
//    }
//
//    @GetMapping("/user/{userId}")
//    public List<OrderEntity> getByUserId(@PathVariable Long userId) {
//        return orderRepository.findByUserId(userId);
//    }
//}
//
