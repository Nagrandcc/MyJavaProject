package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.从redis中查询
        Jedis jedis = JedisUtil.getJedis();
        //Set<String> categroys = jedis.zrange("categroy", 0, -1);
        //查询sortedset中的分数和值
        Set<Tuple> categroys = jedis.zrangeWithScores("categroy",0,-1);

        List<Category> cs = null;
        //2.判断
        if (categroys == null || categroys.size() == 0) {
            System.out.println("从数据库查询...");
            //3.如果为空,从数据库查询,并存入redis
            cs = categoryDao.findAll();
            for (Category c : cs) {
                jedis.zadd("categroy", c.getCid(), c.getCname());
            }
        } else {
            System.out.println("从redis查询...");
            //4.如果不为空,将set存入list
            cs = new ArrayList<Category>();
            for (Tuple tuple : categroys) {
                Category categroy = new Category();
                categroy.setCname(tuple.getElement());
                categroy.setCid((int) tuple.getScore());
                cs.add(categroy);
            }
        }
        return cs;
    }
}
