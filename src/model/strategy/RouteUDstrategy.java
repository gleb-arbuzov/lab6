package model.strategy;

import dataClasses.Route;

import java.util.Map;

/**
 * Интерфейс для стратегий загрузки/выгрузки объектов {@link Route}
 */
public interface RouteUDstrategy
{
    /**
     * Загружает объекты из переданной коллекции
     */
    String download(Map<Integer,Route> collection);

    /**
     * Выгружает объекты из переданной коллекции
     */
    void upload(Map<Integer,Route> collection);
}
