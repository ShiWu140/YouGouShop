package com.training.aigouapi.controller;


import com.training.aigouapi.entity.OrderProduct;
import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.service.OrderProductService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 订单商品控制器
 *
 * @author ethan
 */
@CrossOrigin
@RestController
@RequestMapping("/orderProduct")
public class OrderProductController { 

    @Resource
    private OrderProductService orderProductService;

    /**
     * 分页方法
     *
     * @param page 当前页码
     * @param size  每页大小
     * @return 包含订单商品数据的分页对象
     */
    @GetMapping
    public ResponseEntity<PageEntity<OrderProduct>> findPage(@RequestParam int page, @RequestParam int size) {
        PageEntity<OrderProduct> orderProducts = orderProductService.findPage(page, size);
        return ResponseEntity.ok(orderProducts);
    }

    //获取url参数创建对象，参数不完整返回空对象
//    public OrderProduct getOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String id = req.getParameter("id");
//        String order_id = req.getParameter("order_id");
//        String product_id = req.getParameter("product_id");
//        String product_num = req.getParameter("product_num");
//        if (id.isEmpty() || order_id.isEmpty() || product_id.isEmpty() || product_num.isEmpty()) {
//            ResponseEntity.failed(resp, "信息不完整");
//            return null;
//        }
//        try {
//            return new OrderProduct(id, order_id, product_id, Integer.parseInt(product_num));
//        } catch (Exception e) {
//            ResponseEntity.failed(resp, "商品数量需要填写数字");
//            return null;
//        }
//
//    }


    /**
     * 添加订单商品
     *
     * @param orderProduct 订单商品对象
     * @return 成功或失败信息
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderProduct orderProduct) {
        if (orderProduct == null || orderProduct.getId() == null || orderProduct.getOrderId() == null || orderProduct.getProductId() == null || orderProduct.getProductNum() == null) {
            return ResponseEntity.badRequest().body("订单商品信息不完整");
        }
        if (orderProductService.findId(orderProduct.getId()) != null) {
            return ResponseEntity.badRequest().body("存在有相同订单商品 ID");
        }
        boolean rs = orderProductService.save(orderProduct);
        if (rs) {
            return ResponseEntity.ok(orderProduct);
        } else {
            return ResponseEntity.badRequest().body("保存失败");
        }
    }

    /**
     * 更新订单商品信息
     *
     * @param id    订单商品ID
     * @param orderProduct 订单商品对象
     * @return 成功或失败信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody OrderProduct orderProduct) {
        if (orderProduct == null || id == null || orderProduct.getOrderId() == null || orderProduct.getProductId() == null || orderProduct.getProductNum() == null) {
            return ResponseEntity.badRequest().body("订单商品商品信息不完整");
        }
        boolean rs = orderProductService.update(orderProduct);
        if (rs) {
            return ResponseEntity.ok(orderProduct);
        } else {
            return ResponseEntity.badRequest().body("更新订单商品商品失败");
        }
    }

    /**
     * 删除订单商品
     *
     * @param id 订单商品ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable String id) {
        boolean rs = orderProductService.remove(id);
        if (rs) {
            return ResponseEntity.ok("订单商品删除成功 " + id);
        } else {
            return ResponseEntity.badRequest().body("删除订单商品失败");
        }
    }

    /**
     * 按ID查询订单商品信息
     *
     * @param id 订单商品ID
     * @return 订单商品对象或错误信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        OrderProduct orderProduct = orderProductService.findId(id);
        if (orderProduct != null) {
            return ResponseEntity.ok(orderProduct);
        } else {
            return ResponseEntity.badRequest().body("没有找到");
        }
    }

    /**
     * 查询所有订单商品
     *
     * @return 所有订单商品的列表
     */
    @GetMapping("/all")
    public ResponseEntity<List<OrderProduct>> findAll() {
        List<OrderProduct> orderProducts = orderProductService.findAll();
        return ResponseEntity.ok(orderProducts);
    }
}
