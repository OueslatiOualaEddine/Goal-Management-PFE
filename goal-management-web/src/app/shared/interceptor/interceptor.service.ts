import { Injectable } from "@angular/core";
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpResponse,
  HTTP_INTERCEPTORS,
} from "@angular/common/http";
import { BehaviorSubject, Observable, throwError } from "rxjs";
import {
  catchError,
  filter,
  finalize,
  switchMap,
  take,
  tap,
} from "rxjs/operators";
import { Router } from "@angular/router";
import { AuthService } from "app/service/auth/auth.service";

@Injectable()
export class InterceptorService implements HttpInterceptor {
  isRefreshingToken: boolean = false;
  tokenSubject: BehaviorSubject<string> = new BehaviorSubject<string>("");

  constructor(private router: Router, private authService: AuthService) {}
  // intercept request and add token
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // tslint:disable-next-line:no-debugger
    // modify request
    if (request.headers.get("authExempt") === "true") {
      return next.handle(request);
    }

    if (request.url.indexOf("assets") == -1) {
      request = this.addTokenToRequest(
        request,
        localStorage.getItem("token") || ""
      );
    }
    return next.handle(request).pipe(
      catchError((err) => {
        if (err.status === 401 || err.status === 403) {
          return this.handle401Error(request, next);
        }
        const error = err.error.message || err.statusText;
        return throwError(error);
      })
    );
  }

  private addTokenToRequest(
    request: HttpRequest<any>,
    token: string
  ): HttpRequest<any> {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  private handle401Error(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<any> {
    if (!this.isRefreshingToken) {
      this.isRefreshingToken = true;

      this.tokenSubject.next("");

      return this.authService.refreshToken().pipe(
        switchMap((response: any) => {
          this.tokenSubject.next(response.token);
          request = this.addTokenToRequest(request, response.token);
          localStorage.setItem("token", response.token);
          return next.handle(request).pipe(
            catchError((err) => {
              return throwError(err.error.message || err.statusText);
            })
          );
        }),
        catchError((err) => {
          return <any>this.logout();
        }),
        finalize(() => {
          this.isRefreshingToken = false;
        })
      );
    } else {
      this.isRefreshingToken = false;

      return this.tokenSubject.pipe(
        filter((token) => token != null),
        take(1),
        switchMap((token) => {
          return next.handle(this.addTokenToRequest(request, token));
        })
      );
    }
  }

  logout() {
    localStorage.clear();
    this.router.navigateByUrl("/login");
  }
}
