package cn.cindy.proxy.base;

public interface AbstractBookService {
    public void create();
    public void query();
    public void query(int id);
    public void update();
    public void delete();
}