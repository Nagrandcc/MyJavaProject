package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    RouteService routeService = new RouteServiceImpl();
    FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受参数
        String currentPagestr = request.getParameter("currentPage");
        String pageSizestr = request.getParameter("pageSize");
        String cidstr = request.getParameter("cid");

        //接受rname 线路名称
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");


        int currentPage = 0;
        int pageSize = 0;
        int cid = 0;
        //2.处理参数
        if (cidstr != null && cidstr.length() > 0 && !"null".equals(cidstr)) {
            cid = Integer.parseInt(cidstr);//类别id
        }
        if (currentPagestr != null && currentPagestr.length() > 0) {
            currentPage = Integer.parseInt(currentPagestr);
        } else {
            currentPage = 1;//当前页码
        }
        if (pageSizestr != null && pageSizestr.length() > 0) {
            pageSize = Integer.parseInt(pageSizestr);
        } else {
            pageSize = 5;//每页显示条数
        }

        //3.调用service查询PageBean对象
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);
        //4.序列化
        writeValue(routePageBean, response);

    }

    /**
     * 查找route对象
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid_str = request.getParameter("rid");
        int rid = Integer.parseInt(rid_str);
        Route route = routeService.findOne(rid);
        writeValue(route, response);
    }

    /**
     * 查询收藏状态
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String ridstr = request.getParameter("rid");
        int rid = 0;
        int uid = 0;
        if (ridstr != null && ridstr.length() > 0 && !"null".equals(ridstr)) {
            rid = Integer.parseInt(ridstr);//类别id
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            uid = user.getUid();
        }
        Boolean findfavorite = favoriteService.findfavorite(rid, uid);
        ResultInfo info = new ResultInfo();
        if (findfavorite) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            if (user == null) {
                info.setErrorMsg("未登录");
            } else {
                info.setErrorMsg("未收藏");
            }
        }
        writeValue(info, response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            return;
        }else {
            uid = user.getUid();
        }
        favoriteService.add(rid,uid);
    }
}
