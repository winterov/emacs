INSERT INTO roles (title, description, createdat, is_catalog, modifyby, status, path)
VALUES ('Служебные', 'Служебные роли', current_timestamp, true, 1, 'ACTIVE', '1'),
       ('Администраторы', 'Роли администраторов', current_timestamp, true, 1, 'ACTIVE', '2'),
       ('Регистратура', 'Роли модуля регистратуры', current_timestamp, true, 1, 'ACTIVE', '3'),
       ('Управление персоналом', 'Роли сотрудников кадровой службы', current_timestamp, true, 1, 'ACTIVE', '4'),
       ('Новый сотрудник', 'Роль по умолчанию назначаемая новым сотрудникам при создании аккаунта', current_timestamp,
        false, 1, 'ACTIVE', '1.5'),
       ('СуперПользователь', 'Имеет доступ ко всем модулям программы с неограниченными правами', current_timestamp,
        false, 1, 'ACTIVE', '2.6'),
       ('Администратор', 'Пользователь имеющий доступ на чтение и запись настроек программы, данных о пользователях',
        current_timestamp, false, 1, 'ACTIVE', '2.7'),
       ('Работник регистратуры', 'Работник имеющий доступ на чтение и запись к модулю расписания', current_timestamp,
        false, 1, 'ACTIVE', '3.8'),
       ('Менеджер по персоналу', 'Менеджер по персоналу', current_timestamp, false, 1, 'ACTIVE', '4.9')

