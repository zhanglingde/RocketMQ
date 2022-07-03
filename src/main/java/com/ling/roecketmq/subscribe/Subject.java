package com.ling.roecketmq.subscribe;

/**
 * 抽象观察目标
 * @author shisan
 * @Description:
 * @date 2019-08-06 10:27:43
 */
public interface Subject {
    /**
     * 添加观察者
     * @param  obj
     * */
    void addObserver(Observer obj);

    /**
     * 移除观察者
     * @param  obj
     * */
    void deleteObserver(Observer obj);

    /**
     * 当有新通知通知所有的观察者
     * */
    void notifyObserver();

}