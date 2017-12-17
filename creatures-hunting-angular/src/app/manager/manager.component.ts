import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {ApplicationConfig, CONFIG_TOKEN} from "../app-config";

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.scss']
})
export class ManagerComponent implements OnInit {

  constructor(private http: HttpClient,
              private cookieService: CookieService,
              private router: Router,
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig) { }

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
    this.http.delete(this.config.apiEndpoint + '/pa165/rest/auth', {withCredentials: true}).subscribe(
      data => console.log('Data: ' + data),
      error => console.log('Error: ' + error)
    )
    this.router.navigate(['/login']);
  }
}
