import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import {catchError, throwError} from 'rxjs';

export const exampleInterceptor: HttpInterceptorFn = (req, next) => {
  req = req.clone({
    setHeaders: {
      Authorization: 'Bearer some-token'
    }})
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage: string;
      // Получаем сообщение из ответа сервера

      if (error.status == 0 ){
        errorMessage = 'Отсутствует подключение к серверу';
        alert(`${errorMessage}`);
      }
      else if (error.status == 400) {
        errorMessage = error.error?.message
        alert(`${errorMessage}`);
      }
      else if (error.status == 404) {
        errorMessage = error.error?.message
        alert(`${errorMessage}`);
      }
      else if (error.status == 409) {
        errorMessage = error.error?.message
        alert(`${errorMessage}`);
      }
      else if (error.status == 500) {
        errorMessage = error.error?.message
        alert(`${errorMessage}`);
      }


      // Выводим статус и сообщение


      // Пробрасываем ошибку дальше
      return throwError(() => error);
    })
  );
};
