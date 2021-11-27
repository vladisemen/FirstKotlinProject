# План 1

## Cтруктура обьекта

## R1. Создать объект User*
- создать поля id, login, pass, salt

## R2. Создать объект RoleResource*
- создать поля idUser, resource, role

## R3. Создать объект Parser*
- создать поля login, pass, res, inputRole, ds, de, vol

## R4. Создать перечесление ExitCodeEnum*
- создать поля SUCCESS(0), HELP_OUTPUT(1), INVALID_LOGIN_FORMAT(2), INVALID_LOGIN(3), INVALID_PASSWORD(4), UNKNOWN_ROLE(5), NO_ACCESS(6), INCORRECT_ACTIVITY(7)

## R5. Создать перечесление Roles*
- создать поля READ, WRITE, EXECUTE

## R6. Создать объект Authentication*
- создать поле-ссылка parser

## R7. Создать объект Authorization*
- создать поле-ссылка parser

## R8. Создать объект Parser*
- создать поле text

## R9. Создать объект AAAEloquent*
- создать поля login, resource, role

## R10. Создать объект DateBase*

## Методы объектов

### R11. Объект DateBase
#### R11.1 Метод getRolesResources: возвращает ресурсы пользователей
#### R11.1 Метод getUsers: возвращает существующих пользователей

### R12. Объект Parser
#### R12.1 Метод parser: Разбивате строку на параметры и возаращает как обьект User

### R13. Объект Authentication
#### R13.1 Метод isLoginValid: проверяет правильность формата логина
#### R13.2 Метод isAuthentication: проверяет существования такого пользователя
#### R13.3 Метод getPassHashAndSalt: возращает хешированный пароль с добавление соли
#### R13.4 Метод getHash: возращает хеш строки
#### R13.5 Метод authentication: проводит аунтентификацию

### R14. Объект Authorization
#### R14.1 Метод authorization: проводит авторизацию
#### R14.2 Метод isDateAndValueValid: проверяет формат даты

### R15. Объект AAAEloquent
#### R15.1 Метод hasLogin: проверяет на существование логина
#### R15.1 Метод findUserByLogin: проверяет на существование пользователя
#### R15.1 Метод isCheckResourceAccess: проверяет на существование доступа пользователя к данному ресрсу