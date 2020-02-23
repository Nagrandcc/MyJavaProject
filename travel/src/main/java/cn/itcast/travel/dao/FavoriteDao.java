package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 查找是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findFavorite(int rid,int uid);

    int findCountByRid(int rid);

    void add(int i, int uid);
}
