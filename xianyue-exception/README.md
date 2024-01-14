# RestControllerAdvice 注解与全局异常处理

## @RestControllerAdvice 注解

应用与Controller层的切面注解，范围是单个项目中所有使用了RequestMapping的类。只定义了范围，需配合`@ExceptionHandler`、`@InitBinder`、`@ModelAttribute`
使用，单独使用无意义

- `@ExceptionHandler`：处理全局异常
- `@InitBinder`：将前端传递的参数进行分别绑定
- `@ModelAttribute`：获取InitBinder绑定的参数，进行属性绑定
