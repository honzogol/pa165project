import {Injectable} from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import {ErrorDialogComponent} from "./error-dialog/error-dialog.component";
import {MatDialog} from "@angular/material";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private dialog: MatDialog,) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .catch((err: HttpErrorResponse) => {
        let errors = [];
        errors.push(err.error.errors);
        if (errors.length == 0) {
          errors.push(err.statusText);
        }

        if (err.status == 0) {
          errors.push("Server is not accessible.");
        }

        let dialogRef = this.dialog.open(ErrorDialogComponent, {
          width: '600px',
          data: errors,
        });

        dialogRef.afterClosed().subscribe(result => {
          console.log("Dialog closed");
        });
        return Observable.throw(err);
      });
  }
}
