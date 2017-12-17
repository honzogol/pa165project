import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Router } from '@angular/router';
import {ApplicationConfig, CONFIG_TOKEN} from "../app-config";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  logged: boolean = false;

  constructor(private http: HttpClient,
              private router: Router,
              @Inject(CONFIG_TOKEN) private config: ApplicationConfig) { }

  ngOnInit() {

  }

  login(email, password) {
    this.http.post(this.config.apiEndpoint + '/pa165/rest/auth?email=' + email + '&password=' + password, null, { withCredentials: true }).subscribe(
      data=> {
        console.log('Data: ' + data);
        this.router.navigate(['']);
      },
      error => {
        console.log('Error: ' + error);
        alert('Error: ' + error.message);
      }
    )
  }
}
