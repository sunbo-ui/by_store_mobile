package com.mysb.core.service;

import com.mysb.core.dao.good.GoodsDao;
import com.mysb.core.dao.item.ItemDao;
import com.mysb.core.dao.log.PayLogDao;
import com.mysb.core.dao.order.OrderDao;
import com.mysb.core.dao.order.OrderItemDao;
import com.mysb.core.interfac.OrderFeginClient;
import com.mysb.core.pojo.entry.BuyCartEntity;
import com.mysb.core.pojo.entry.BuyOrderEntity;
import com.mysb.core.pojo.good.Goods;
import com.mysb.core.pojo.item.Item;
import com.mysb.core.pojo.item.ItemQuery;
import com.mysb.core.pojo.log.PayLog;
import com.mysb.core.pojo.order.Order;
import com.mysb.core.pojo.order.OrderItem;
import com.mysb.core.utils.Constants;
import com.mysb.core.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Transactional
public class OrderServiceImpl implements OrderFeginClient {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private PayLogDao payLogDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void orderSave(Order order) {
        //取出购物车
        List<BuyCartEntity> list = (List<BuyCartEntity>) redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).get(order.getUserId());
        ArrayList<String> orderList = new ArrayList<>();//订单id列表
        double total_money = 0;//总金额
        if (list != null) {
            IdWorker idWorker = new IdWorker();
            List<BuyOrderEntity> buyOrderEntity = createBuyOrderEntity(list);
            if (buyOrderEntity != null) {
                for (BuyOrderEntity entity : buyOrderEntity) {
                    Order newOrder = new Order();
                    newOrder.setOrderId(new IdWorker().nextId());
                    newOrder.setPaymentType(order.getPaymentType());
                    newOrder.setStatus("1");//未付款
                    newOrder.setCreateTime(new Date());
                    newOrder.setUpdateTime(new Date());
                    newOrder.setUserId(order.getUserId());
                    newOrder.setBuyerMessage(order.getBuyerMessage());
                    newOrder.setReceiverAreaName(order.getReceiverAreaName());
                    newOrder.setReceiver(order.getReceiver());
                    newOrder.setReceiverMobile(order.getReceiverMobile());
                    newOrder.setSourceType(order.getSourceType());//app端
                    newOrder.setSellerId(entity.getSellerId());
                    //循环购物车明细
                    double money = 0;
                    if (entity.getOrderItems() != null) {
                        for (BuyCartEntity cartEntity : entity.getOrderItems()) {
                            //保存订单详情
                            ItemQuery itemQuery = new ItemQuery();
                            ItemQuery.Criteria criteria = itemQuery.createCriteria();
                            criteria.andGoodsIdEqualTo(cartEntity.getId());
                            List<Item> items = itemDao.selectByExample(itemQuery);
                            OrderItem orderItem = new OrderItem();
                            orderItem.setId(idWorker.nextId());
                            orderItem.setOrderId(newOrder.getOrderId());//订单id
                            orderItem.setSellerId(entity.getSellerId());
                            orderItem.setItemId(items.get(0).getId());
                            orderItem.setGoodsId(cartEntity.getId());
                            orderItem.setTitle(items.get(0).getTitle());
                            orderItem.setPrice(new BigDecimal(cartEntity.getPrice()));
                            orderItem.setNum(cartEntity.getNum());
                            orderItem.setPicPath(cartEntity.getSmallImage());
                            money += (Double.parseDouble(cartEntity.getPrice()) * cartEntity.getNum());//金额累加
                            orderItem.setTotalFee(new BigDecimal(money));
                            orderItemDao.insertSelective(orderItem);
                        }
                    }
                    //订单总额
                    total_money += money;
                    newOrder.setPayment(new BigDecimal(total_money));
                    orderDao.insertSelective(newOrder);
                    orderList.add(newOrder.getOrderId() + "");//添加到订单列表
                    if (!"2".equals(order.getPaymentType())) {
                        PayLog payLog = new PayLog();
                        String outTradeNo = idWorker.nextId() + "";//支付订单号
                        payLog.setOutTradeNo(outTradeNo);
                        payLog.setCreateTime(new Date());
                        //订单列表,逗号分离
                        String ids = orderList.toString().replace("[", "")
                                .replace("]", "").replace(" ", "");
                        payLog.setOrderList(ids);
                        payLog.setPayType(order.getPaymentType());//支付类型
                        payLog.setTotalFee((long) (total_money * 100));//总金额(分)
                        payLog.setTradeState("0");//支付类型 未付款
                        payLog.setUserId(order.getUserId());
                        payLog.setCreateTime(new Date());
                        payLogDao.insertSelective(payLog);
                        redisTemplate.boundHashOps("payLog").put(order.getUserId(), payLog);//用户名做key
                    }
                }
                //删除redis购物车
                redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).delete(order.getUserId());
            }
        }
    }



    public List<BuyOrderEntity> createBuyOrderEntity(List<BuyCartEntity> list) {
        List<BuyOrderEntity> buyOrderEntities = new ArrayList<>();
        List<String> sellers = new ArrayList<>();//暂存seller

        for (BuyCartEntity buyCartEntity : list) {
            Goods goods = goodsDao.selectByPrimaryKey(buyCartEntity.getId());
            //存在商家
            if (sellers.size() > 0) {
                int flages = 0;
                int j=0;
                for (int i = 0; i < buyOrderEntities.size(); i++) {
                    if (buyOrderEntities.get(i).getSellerId().equals(goods.getSellerId())) {
                        flages = 0;
                        j=i;
                        break;
                    } else {
                        flages = 1;

                    }
                }
                if (flages == 1) {
                    BuyOrderEntity entity = new BuyOrderEntity();
                    entity.setSellerId(goods.getSellerId());
                    List<BuyCartEntity> orderItems = entity.getOrderItems();
                    if (orderItems == null) {
                        orderItems = new ArrayList<BuyCartEntity>();
                        orderItems.add(buyCartEntity);
                    } else {
                        orderItems.add(buyCartEntity);
                    }
                    //设置 List<BuyCartEntity>
                    entity.setOrderItems(orderItems);
                    buyOrderEntities.add(entity);
                } else {
                    List<BuyCartEntity> orderItems = buyOrderEntities.get(j).getOrderItems();
                    orderItems.add(buyCartEntity);
                    buyOrderEntities.get(j).setOrderItems(orderItems);
                }
                int flag = 0;
                for (int i = 0; i < sellers.size(); i++) {
                    if (goods.getSellerId().equals(sellers.get(i))) {
                        i = 0;
                    } else {
                        i = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    sellers.add(goods.getSellerId());
                }
            } else {
                BuyOrderEntity entity = new BuyOrderEntity();
                entity.setSellerId(goods.getSellerId());
                List<BuyCartEntity> orderItems = entity.getOrderItems();
                if (orderItems == null) {
                    orderItems = new ArrayList<BuyCartEntity>();
                    orderItems.add(buyCartEntity);
                } else {
                    orderItems.add(buyCartEntity);
                }
                //设置 List<BuyCartEntity>
                entity.setOrderItems(orderItems);
                buyOrderEntities.add(entity);
                sellers.add(goods.getSellerId());
            }

            System.out.println(buyOrderEntities);
        }
        return buyOrderEntities;
    }

}
