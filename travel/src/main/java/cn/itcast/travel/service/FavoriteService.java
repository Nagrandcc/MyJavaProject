package cn.itcast.travel.service;

public interface FavoriteService {
    public Boolean findfavorite(int rid, int uid);

    void add(String rid, int uid);
}
