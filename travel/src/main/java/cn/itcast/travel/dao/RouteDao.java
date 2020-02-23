package cn.itcast.travel.dao;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.awt.*;
import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    public int findTotalCount(int cid,String ranme);

    /**
     * 根据cid,start,pagesize查询list集合
     * @param cid
     * @param start
     * @param pagesize
     * @return
     */
    public List<Route> findByPage(int cid,int start,int pagesize,String ranme);

    /**
     * 根据rid查询RouteImg集合
     * @param rid
     * @return
     */
    public List<RouteImg> findByImg(int rid);

    /**
     * 根据rid查询Seller
     * @param rid
     * @return
     */
    public Seller findBySeller(int rid);

    /**
     * 查询route对象
     * @param rid
     * @return
     */
    public Route findRoute(int rid);
}
