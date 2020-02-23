package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid查询总记录数
     *
     * @param cid
     * @return
     */
    @Override
    public int findTotalCount(int cid, String ranme) {
        //1.定义sql模板
        String sql = "SELECT COUNT(*) FROM tab_route WHERE 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);//添加?对应的值
        }

        if (ranme != null && ranme.length() > 0) {
            sb.append(" and rname like ? ");
            params.add("%" + ranme + "%");
        }

        sql = sb.toString();

        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    /**
     * 根据cid,start,pagesize查询list集合
     *
     * @param cid
     * @param start
     * @param pagesize
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pagesize, String ranme) {
        //1.定义sql模板
        String sql = "SELECT * FROM tab_route where 1 = 1 ";


        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);//添加?对应的值
        }

        if (ranme != null && ranme.length() > 0 && !"null".equals(ranme)) {
            sb.append(" and rname like ? ");
            params.add("%" + ranme + "%");
        }
        sb.append(" limit ? , ? ");
        sql = sb.toString();

        params.add(start);
        params.add(pagesize);

        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    @Override
    public List<RouteImg> findByImg(int rid) {
        String sql = "SELECT * FROM tab_route_img WHERE rid = ?";
        return template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }

    @Override
    public Seller findBySeller(int rid) {
        Route route = findRoute(rid);
        String sql = "SELECT * FROM tab_seller WHERE sid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), route.getSid());
    }

    @Override
    public Route findRoute(int rid) {
        String sql = "SELECT * FROM tab_route WHERE rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
