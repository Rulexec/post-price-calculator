package by.muna.moep.post.database;

import by.muna.moep.post.database.models.IDBPostFormula;
import by.muna.moep.post.database.models.IDBPostType;

import java.util.List;

/**
 * Интерфейс базы данных приложения, предоставляет откуда-нибудь данные о типах и формулах.
 */
public interface IPostDatabase {
    /**
     * Получает список всех типов, по умолчанию лениво.
     * <p>Т.е. получает лишь идентификаторы и имена типов,
     * а по запросу формулы/параметров — обращается к базе данных и берёт их оттуда.</p>
     * @return Список всех типов отправлений
     * @throws PostDatabaseException Если получить данные по каким-то причинам не удалось,
     * бросить это исключение
     */
    List<IDBPostType> getAllTypes() throws PostDatabaseException;

    /**
     * Получает конкретный тип по идентификатору.
     * @param id Идентификатор типа
     * @param deep Если true, то получить и формулу и параметры, иначе лениво
     * @return Данные с типом отправления, null если такого нет
     * @throws PostDatabaseException Если получить нельзя — бросить исключение
     */
    IDBPostType getPostType(int id, boolean deep) throws PostDatabaseException;

    /**
     * @param slug Псевдоним формулы
     * @return Формулу или null, если такой нет
     * @throws PostDatabaseException Если получить нельзя — бросить исключение
     */
    IDBPostFormula getFormula(String slug) throws PostDatabaseException;

    default IDBPostType getPostType(int id) throws PostDatabaseException {
        return this.getPostType(id, true);
    }
}
