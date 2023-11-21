package tasks;

import common.Person;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 {

  private long count;
  private static final int SKIP_PERSON=1;

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  //Можно не удалять персону, а пропустить ее в стриме, не будет затрачиваться время на удаление
  //Плюс возможно будет передан неизменяемый лист
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
            .skip(SKIP_PERSON)
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  // неправильное использование стрима
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  //Судя из подсказки на лекции можно вот так использовать стрим, предыдущий вариант занимает много места
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(Person::getId, this::convertPersonToString, (name1, name2) -> name1));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> differentPersons = new HashSet<>((persons2));
    return persons1.stream()
            .anyMatch(differentPersons::contains);
  }

  // Первый раз вижу, чтобы передавался стрим, так делают?
  public long countEven(Collection<Integer> numbers) {
    return numbers.stream()
            .filter(num -> num % 2 == 0)
            .count();
  }
}
