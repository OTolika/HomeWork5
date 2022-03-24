#language: ru

@Smoke
Функционал: Рик и Морти

  @test1
  Сценарий: Персонаж Морти
    Дано загружен персонаж Морти
    Тогда персонаж Морти существует

  @test1
  Сценарий: Последний эпизод персонажа Морти
    Дано загружен персонаж Морти
    * находим последний эпизод персонажа Морти
    Тогда последний эпизод персонажа Морти существует

  @test1
  Сценарий: Последний персонаж последнего эпизода персонажа Морти
    Дано загружен персонаж Морти
    * находим последний эпизод персонажа Морти
    * загружаем последнего персонажа последнего эпизода
    Тогда последний персонаж последнего эпизода существует

  @test1
  Сценарий: Свойства персонажей
    Дано загружен персонаж Морти
    * находим последний эпизод персонажа Морти
    * загружаем последнего персонажа последнего эпизода
    Тогда раса персонажей совпадает
    * местоположение персонажей не совпадает
