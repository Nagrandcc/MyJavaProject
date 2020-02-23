package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService{
    FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public Boolean findfavorite(int rid,int uid) {
        Favorite favorite =  favoriteDao.findFavorite(rid, uid);
        if (favorite!=null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void add(String rid, int uid) {
        try {
            favoriteDao.add(Integer.parseInt(rid),uid);
        } catch (NumberFormatException e) {

        }
    }
}
