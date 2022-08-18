package com.ling.roecketmq.subscribe;

/**
 * 抽象观察目标
 *
 * @author zhangling
 * @date 2022/7/4 9:04 PM
 */
public interface Subject {
    /**
     * 添加观察者
     *
     * @author zhangling
     * @date 2022/7/4 9:04 PM
     */
    void addObserver(Observer obj);

    /**
     * 移除观察者
     *
     * @author zhangling
     * @date 2022/7/4 9:05 PM
     */
    void deleteObserver(Observer obj);

    /**
     * 当有新通知时通知所有的观察者
     *
     * @author zhangling
     * @date 2022/7/4 9:05 PM
     */
    void notifyObserver();

}