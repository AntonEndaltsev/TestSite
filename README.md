<b>Это CRUD приложение</b><br/>
- На удаленный VDS по адресу 80.87.107.222 осуществлен deploy через реализацию двух docker контейнеров. В одном крутится сам сервис, во втором развернута PostgreSQL<br/>
- Внедрен Swagger - http://80.87.107.222:8086/swagger-ui/index.html<br/>
- Внедрен Spring Security<br/>
- Сделана версионизация двух типов: по endpoint'ам, по HTTP-header<br/>
- Сделаны unit и интеграционные тесты (в том числе с использованием testcontainers)<br/>
- Через Github Actions внедрен CI/CD. При пуше или пуллреквесте прогоняются тесты.<br/>
- Внутри сервиса - Spring Data JPA, Hibernate, Spring Boot
