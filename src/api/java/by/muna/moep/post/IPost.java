package by.muna.moep.post;

import by.muna.moep.post.data.IPostType;
import by.muna.moep.post.data.IPostTypeFull;

import java.util.List;
import java.util.Map;

/**
 * Ключевой интерфейс приложения, собирающий всю его логику.
 *
 * <p>Реализация данного интерфейса принимает
 * {@link by.muna.moep.post.database.IPostDatabase} и
 * {@link by.muna.moep.post.formula.parser.IFormulaParser}.</p>
 *
 * @see by.muna.moep.post.database.IPostDatabase
 * @see by.muna.moep.post.formula.parser.IFormulaParser
 */
public interface IPost {
    /**
     * @return Список кратких описаний типов отправлений (id'шник и человеческое имя)
     * @throws PostException Если база данных бросает исключение — перебрасываем его
     */
    List<IPostType> getPostTypes() throws PostException;

    /**
     * @return Полное описание типа (человеческое имя, список параметров)
     * @param id id'шник типа отправления
     * @throws PostException Если база данных бросает исключение — перебрасываем его
     */
    IPostTypeFull getPostType(int id) throws PostException;

    /**
     * Вычисляет стоимость почтового отправления.
     * <p>Логика такова:</p>
     * <ul>
     *     <li>От базы данных получаем формулу ответственную за тип</li>
     *     <li>Передаём полученную формулу парсеру формул, он говорит нам,
     *     какие в формуле используются формулы</li>
     *     <li>Рекурсивно получаем все формулы от базы данных</li>
     *     <li>Разрешаем зависимости, подставляя формулы в формулы,
     *     получаем итоговую формулу</li>
     *     <li>Вычисляем формулу, получаем стоимость</li>
     * </ul>
     * @param postTypeId id типа отправления
     * @param arguments значения параметров, в будущем строковые значения будут deprecated
     * @return Цена отправления в белорусских рублях
     * @throws PostException Если база данных бросает исключение,
     * какая-нибудь формула не парсится, или что-то ещё пошло не так —
     * перебрасываем исключение
     */
    long calculate(int postTypeId, Map<String, String> arguments) throws PostException;
}
