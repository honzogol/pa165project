import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.scss']
})
export class ManagerComponent implements OnInit {

  constructor(private http: HttpClient, private cookieService: CookieService, private router: Router) { }

  cookieIsAdmin: boolean = false;

  ngOnInit() {
    this.getCookie();
    console.log(this.cookieIsAdmin);
  }

  getCookie(){
    if (this.cookieService.get('creatures-is_admin') == "true"){
      this.cookieIsAdmin = true;
      return;
    }
    this.cookieIsAdmin = false;
  }

  logout() {
    this.http.delete('http://localhost:8080/pa165/rest/auth', {withCredentials: true}).subscribe(
      data => console.log('Data: ' + data),
      error => console.log('Error: ' + error)
    )
    this.router.navigate(['/login']);
  }
}
